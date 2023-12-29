package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
import com.developaw.harupuppy.domain.user.dto.response.OAuthTokenResponse;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.utils.JwtTokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OAuthService {
    static final String ACCESS_TOKEN = "Access-Token";
    static final String REFRESH_TOKEN = "Refresh-Token";
    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public String login(String providerName, String code, HttpServletResponse response) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        OAuthTokenResponse oAuthToken = getAccessToken(provider, code);
        Map<String, Object> userAttributes = getUserInfo(provider, oAuthToken);
        String userEmail = (String) userAttributes.get("email");

        if (userRepository.existsByEmail(userEmail)) {
            userEmail = userRepository.findByEmail(userEmail).get().getEmail();
            TokenDto token = jwtTokenUtils.generateToken(userEmail);
            response.setHeader(ACCESS_TOKEN, token.accessToken());
            response.setHeader(REFRESH_TOKEN, token.refreshToken());
        }

        return userEmail;
    }

    private OAuthTokenResponse getAccessToken(ClientRegistration provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(getTokenRequest(provider, code))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> getTokenRequest(ClientRegistration provider, String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        return formData;
    }

    private Map<String, Object> getUserInfo(ClientRegistration provider, OAuthTokenResponse token) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(String.valueOf(token.accessToken())))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }
}
