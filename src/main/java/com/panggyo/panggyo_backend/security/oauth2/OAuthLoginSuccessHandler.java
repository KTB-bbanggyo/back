package com.panggyo.panggyo_backend.security.oauth2;

import com.panggyo.panggyo_backend.security.token.JwtUtil;
import com.panggyo.panggyo_backend.security.token.RefreshToken;
import com.panggyo.panggyo_backend.security.token.RefreshTokenRepository;
import com.panggyo.panggyo_backend.entity.SocialProvider;
import com.panggyo.panggyo_backend.entity.WebtyUser;
import com.panggyo.panggyo_backend.repository.SocialProviderRepository;
import com.panggyo.panggyo_backend.repository.UserRepository;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler{

    private final JwtUtil jwtUtil;
    private final SocialProviderRepository socialProviderRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void handleLoginSuccess(String provider, String providerId) {
        SocialProvider socialProvider = socialProviderRepository.findByProviderId(providerId);
        WebtyUser user;

        if (socialProvider == null) {
            socialProvider = new SocialProvider(provider, providerId, null);
            socialProviderRepository.save(socialProvider);

            user = new WebtyUser("nickname", socialProvider);
            userRepository.save(user);
        } else {
            user = socialProvider.getUser();
            refreshTokenRepository.deleteByUserId(user.getUserId());
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId(), 3600000);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId(), 86400000);
        refreshTokenRepository.save(new RefreshToken(user.getUserId(), refreshToken));

        log.info("User authenticated: " + user.getNickname());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.sendRedirect("/"); // 로그인 성공 후 리디렉트할 URL
    }
}
