package com.example.eventmanager.service.oauth2.strategy;

import com.example.eventmanager.service.oauth2.OAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GoogleUserInfoStrategy implements OAuth2UserInfoStrategy {

    @Override
    public String getProviderId() {
        return "google";
    }

    @Override
    public OAuth2UserInfo extractUserInfo(Map<String, Object> attributes) {
        return new OAuth2UserInfo(
                (String) attributes.get("email"),
                (String) attributes.get("name"),
                (String) attributes.get("picture")
        );
    }
}
