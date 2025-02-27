@Service
public class KakaoOAuthService {
    
    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId + 
               "&redirect_uri=" + redirectUri + "&response_type=code";
    }

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SocialProviderRepository socialProviderRepository;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public ResponseEntity<?> processKakaoLogin(String code) {
        // 1. 인가 코드를 이용해 액세스 토큰 요청
        String accessToken = getAccessToken(code);
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "카카오 인증 실패", "code", 401));
        }

        // 2. 액세스 토큰을 이용해 유저 정보 요청
        Map<String, Object> userInfo = getUserInfo(accessToken);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "카카오 사용자 정보 조회 실패", "code", 401));
        }

        // 3. 사용자 정보 저장 및 JWT 토큰 발급
        WebtyUser user = registerOrUpdateUser(userInfo);
        String jwtToken = jwtUtil.generateAccessToken(user.getUserId(), 3600000);

        return ResponseEntity.ok(Map.of("user_id", user.getUserId(), "jwt_token", jwtToken));
    }

    private String getAccessToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

        return response.getBody() != null ? (String) response.getBody().get("access_token") : null;
    }

    private Map<String, Object> getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    private WebtyUser registerOrUpdateUser(Map<String, Object> userInfo) {
        String providerId = String.valueOf(userInfo.get("id"));
        SocialProvider socialProvider = socialProviderRepository.findByProviderId(providerId);

        if (socialProvider == null) {
            socialProvider = new SocialProvider("kakao", providerId, null);
            socialProviderRepository.save(socialProvider);

            WebtyUser user = new WebtyUser("nickname", socialProvider);
            userRepository.save(user);
            return user;
        }

        return socialProvider.getUser();
    }
}
