package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.dto.OAuth2UserDetail;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        log.info("--------------------- OAuth2 load user ---------------------");
        final OAuth2User oAuth2User = super.loadUser(request);

        final Map<String, Object> attributes = oAuth2User.getAttributes();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            log.info("OAuthUser key: {}", entry.getKey());
            log.info("OAuthUser value: {}", entry.getValue());
        }

        final String kakaoEmail = (String) attributes.get("email");
        User registedUser = userRepository.findByEmail(kakaoEmail).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        log.info("Successfully pulled user info by kakao OAuth");

        return OAuth2UserDetail.builder()
                .kakaoEmail(kakaoEmail)
                .authorities(List.of(new SimpleGrantedAuthority(registedUser.getUserRole().toString())))
                .attributes(attributes)
                .build();
    }

}
