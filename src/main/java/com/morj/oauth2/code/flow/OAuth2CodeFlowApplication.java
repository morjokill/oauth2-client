package com.morj.oauth2.code.flow;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.morj.oauth2.code.flow.oauth2_api.KeycloakOAuthApi;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Scanner;

@SpringBootApplication
public class OAuth2CodeFlowApplication {
    // Replace these with your own api key, secret, callback, base url and realm
    final static String apiKey = "oauth2flow";
    final static String apiSecret = "ULBQ2oZpxxIFARt3ZFRyZsYF6n2XgbpN";
    final static String callback = "http://192.168.0.103:8080/code";
    final static String baseUrl = "http://192.168.0.103:8888";
    final static String realm = "OAuth2Flow";

    final static String protectedResourceUrl = baseUrl + "/realms/" + realm + "/protocol/openid-connect/userinfo";

    final static OAuth20Service service = new ServiceBuilder(apiKey)
            .apiSecret(apiSecret)
            .defaultScope("openid")
            .callback(callback)
            .build(KeycloakOAuthApi.instance(baseUrl, realm));

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OAuth2CodeFlowApplication.class, args);
        login();
    }

    public static String login() throws Exception {
        final Scanner in = new Scanner(System.in);

        System.out.println("=== Keyloack's OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl();
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        return authorizationUrl;
    }

    @SneakyThrows
    public static String getAccessToken(String code) {
        System.out.println("Trading the Authorization Code for an Access Token...");
        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");

        // Now let's go and ask for a protected resource!
/*        System.out.println("Now we're going to access a protected resource...");
        final OAuthRequest request = new OAuthRequest(Verb.GET, protectedResourceUrl);
        service.signRequest(accessToken, request);
        try (Response response = service.execute(request)) {
            System.out.println("Got it! Lets see what we found...");
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());
        }
        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");*/

        return accessToken.getRawResponse();
    }
}
