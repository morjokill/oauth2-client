package com.morj.oauth2.code.flow.oauth2_api;

import com.github.scribejava.apis.openid.OpenIdJsonTokenExtractor;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class KeycloakOAuthApi extends DefaultApi20 {
    private static final ConcurrentMap<String, KeycloakOAuthApi> INSTANCES = new ConcurrentHashMap<>();
    private final String baseUrlWithRealm;

    private KeycloakOAuthApi(String baseUrlWithRealm) {
        this.baseUrlWithRealm = baseUrlWithRealm;
    }

    public static KeycloakOAuthApi instance(String baseUrl, String realm) {
        final String defaultBaseUrlWithRealm = composeBaseUrlWithRealm(baseUrl, realm);

        //java8: switch to ConcurrentMap::computeIfAbsent
        KeycloakOAuthApi api = INSTANCES.get(defaultBaseUrlWithRealm);
        if (api == null) {
            api = new KeycloakOAuthApi(defaultBaseUrlWithRealm);
            final KeycloakOAuthApi alreadyCreatedApi = INSTANCES.putIfAbsent(defaultBaseUrlWithRealm, api);
            if (alreadyCreatedApi != null) {
                return alreadyCreatedApi;
            }
        }
        return api;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return baseUrlWithRealm + "/protocol/openid-connect/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return baseUrlWithRealm + "/protocol/openid-connect/auth";
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return OpenIdJsonTokenExtractor.instance();
    }

    private static String composeBaseUrlWithRealm(String baseUrl, String realm) {
        return baseUrl + (baseUrl.endsWith("/") ? "" : "/") + "realms/" + realm;
    }
}
