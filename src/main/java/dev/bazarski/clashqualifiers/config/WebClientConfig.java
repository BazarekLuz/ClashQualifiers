package dev.bazarski.clashqualifiers.config;

import dev.bazarski.clashqualifiers.props.RiotApiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(RiotApiProperties props) {
        return WebClient.builder()
                .baseUrl(props.getGeneralUrl())
                .defaultHeader("X-Riot-Token", props.getToken())
                .build();
    }
}
