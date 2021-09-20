package com.coding.saga.web.adapter.in;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class OAuth2UserFactory {
    private OAuth2UserFactory() {
        throw new UnsupportedOperationException();
    }

    public static DefaultOAuth2User createOAuth2User(String email, String name) {
        return new DefaultOAuth2User(emptyList(),
                Map.of(
                        "email", email,
                        "name", name
                ),
                "name"
        );
    }
}
