package org.example.prober_oncall;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OnCallWebClient {
    private final WebClient webClient;

    public OnCallWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String login() {
        return webClient.get()
                .uri("/api/v0/users/jdoe")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
