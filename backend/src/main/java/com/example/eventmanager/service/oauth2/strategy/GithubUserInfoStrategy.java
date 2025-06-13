package com.example.eventmanager.service.oauth2.strategy;

import com.example.eventmanager.service.oauth2.OAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GithubUserInfoStrategy implements OAuth2UserInfoStrategy {

    @Override
    public String getProviderId() {
        return "github";
    }

    @Override
    public OAuth2UserInfo extractUserInfo(Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        if (name == null) {
            name = (String) attributes.get("login");
        }

        return new OAuth2UserInfo(
                (String) attributes.get("email"),
                name,
                (String) attributes.get("avatar_url")
        );
    }
}
