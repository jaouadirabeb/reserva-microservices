package com.reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

    @GetMapping(value = "/token")
    public Mono<String> getHome(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return Mono.just(authorizedClient.getAccessToken().getTokenValue());
    }

    @GetMapping("/")
    public Mono<String> index(WebSession session) {
        return Mono.just(session.getId());
    }

}
