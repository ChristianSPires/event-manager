package com.example.eventmanager.service.oauth2;

import com.example.eventmanager.service.oauth2.strategy.OAuth2UserInfoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OAuth2UserInfoStrategyFactory {

    private final Map<String, OAuth2UserInfoStrategy> strategyMap;

    @Autowired
    public OAuth2UserInfoStrategyFactory(List<OAuth2UserInfoStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(java.util.stream.Collectors.toMap(
                        OAuth2UserInfoStrategy::getProviderId,
                        strategy -> strategy
                ));
    }

    public OAuth2UserInfo extract(String provider, Map<String, Object> attributes) {
        OAuth2UserInfoStrategy strategy = strategyMap.get(provider);
        if (strategy == null) {
            throw new IllegalArgumentException("Provedor OAuth2 não suportado: " + provider);
        }
        return strategy.extractUserInfo(attributes);
    }
}
