package dev.bazarski.clashqualifiers;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9000)
public class ClashQualifiersIntegrationTests {
    private static final Logger log = LoggerFactory.getLogger(ClashQualifiersIntegrationTests.class);
    WebTestClient client;

    @BeforeEach
    void setup(ApplicationContext context) {
         client = WebTestClient.bindToApplicationContext(context)
                 .build();
    }

    @Test
    void shouldReturnStatus200() {
        var x = client.get()
                .uri("/api/qualifiers/matchIds")
                .exchange()
                .expectStatus().isOk().expectBody().returnResult();
        System.out.println(x);
    }

    @Test
    void shouldReturn6Entities() {
        var x = client.get()
                .uri("/api/qualifiers/matchIds")
                .exchange()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(6);
    }

    @Test
    void shouldReturnCorrectJsonBody() {
        var x = client.get()
                .uri("/api/qualifiers/matchIds")
                .exchange()
                .expectBody()
                .jsonPath("$.[*].gameName").isNotEmpty()
                .jsonPath("$.[*].points").isNotEmpty();
    }
}
