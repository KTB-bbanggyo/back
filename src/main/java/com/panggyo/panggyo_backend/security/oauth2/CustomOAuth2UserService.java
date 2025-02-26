package com.panggyo.panggyo_backend.security.oauth2;

import com.panggyo.panggyo_backend.entity.SocialProvider;
import com.panggyo.panggyo_backend.entity.WebtyUser;
import com.panggyo.panggyo_backend.repository.SocialProviderRepository;
import com.panggyo.panggyo_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialProviderRepository socialProviderRepository;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserInfo userInfo = null;

        if ("kakao".equals(provider)) {
            log.info("카카오 로그인 요청 감지");
            userInfo = new KakaoUserInfo(attributes);
        }

        if (userInfo == null) {
            throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider: " + provider);
        }

        String providerId = userInfo.getProviderId();
        SocialProvider socialProvider = socialProviderRepository.findByProviderId(providerId);
        WebtyUser user;

        if (socialProvider == null) {
            log.info("신규 유저 등록 진행");
            socialProvider = SocialProvider.builder()
                    .provider(userInfo.getProvider())
                    .providerId(providerId)
                    .build();
            socialProviderRepository.save(socialProvider);

            user = WebtyUser.builder()
                    .nickname("nickname") // 기본 닉네임 설정, 이후 변경 가능
                    .socialProvider(socialProvider)
                    .build();
            userRepository.save(user);

            socialProvider.setUser(user);
            socialProviderRepository.save(socialProvider);
        } else {
            log.info("기존 유저 로그인");
            user = socialProvider.getUser();
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id"
        );
    }
}
