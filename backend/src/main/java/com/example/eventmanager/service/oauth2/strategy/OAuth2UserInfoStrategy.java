package com.example.eventmanager.service.oauth2.strategy;

import com.example.eventmanager.service.oauth2.OAuth2UserInfo;

import java.util.Map;

public interface OAuth2UserInfoStrategy {
    String getProviderId();
    OAuth2UserInfo extractUserInfo(Map<String, Object> attributes);
}
