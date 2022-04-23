package com.morj.oauth2.code.flow.config;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.morj.oauth2.code.flow.oauth2_api.KeycloakOAuthApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public static OAuth20Service oAuth20Service(
            @Value("${client.id}") String clientId,
            @Value("${client.secret}") String clientSecret,
            @Value("${base.url}") String baseUrl,
            @Value("${realm}") String realm,
            @Value("${callback}") String callback) {
        return new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("openid")
                .callback(callback)
                .build(KeycloakOAuthApi.instance(baseUrl, realm));
    }
}
