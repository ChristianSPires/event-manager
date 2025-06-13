package com.example.eventmanager.service;

import com.example.eventmanager.model.User;
import com.example.eventmanager.repository.UserRepository;
import com.example.eventmanager.service.oauth2.OAuth2UserInfo;
import com.example.eventmanager.service.oauth2.OAuth2UserInfoStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuth2UserInfoStrategyFactory userInfoFactory;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository, OAuth2UserInfoStrategyFactory userInfoFactory) {
        this.userRepository = userRepository;
        this.userInfoFactory = userInfoFactory;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = userInfoFactory.extract(registrationId, oauth2User.getAttributes());

        if ("github".equals(registrationId) && userInfo.email() == null) {
            String token = userRequest.getAccessToken().getTokenValue();
            String email = fetchPrimaryGithubEmail(token);

            if (email != null) {
                userInfo = new OAuth2UserInfo(email, userInfo.name(), userInfo.picture());
            }
        }

        if (userInfo.email() == null || userInfo.name() == null) {
            throw new OAuth2AuthenticationException("Dados do usuário incompletos");
        }

        Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
        attributes.put("email", userInfo.email());

        OAuth2UserInfo finalUserInfo = userInfo;
        User user = userRepository.findByEmail(userInfo.email())
                .orElseGet(() -> registerNewUser(finalUserInfo));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email"
        );
    }

    private User registerNewUser(OAuth2UserInfo userInfo) {
        User user = new User();
        user.setEmail(userInfo.email());
        user.setName(userInfo.name());
        user.setProfilePicture(userInfo.picture());
        return userRepository.save(user);
    }

    private String fetchPrimaryGithubEmail(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    "https://api.github.com/user/emails",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().stream()
                        .filter(emailMap -> Boolean.TRUE.equals(emailMap.get("primary")))
                        .map(emailMap -> (String) emailMap.get("email"))
                        .findFirst()
                        .orElse(null);
            }
        } catch (Exception ignored) {
        }

        return null;
    }
}
