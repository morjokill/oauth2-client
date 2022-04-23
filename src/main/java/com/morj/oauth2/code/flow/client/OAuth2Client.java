package com.morj.oauth2.code.flow.client;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2Client {
    private final OAuth20Service oAuth20Service;

    public String login() {
        System.out.println("=== Keyloack's OAuth Workflow ===");
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = oAuth20Service.getAuthorizationUrl();
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        return authorizationUrl;
    }

    @SneakyThrows
    public String getAccessToken(String code) {
        System.out.println("Trading the Authorization Code for an Access Token...");
        final OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");
        return accessToken.getRawResponse();
    }
}
