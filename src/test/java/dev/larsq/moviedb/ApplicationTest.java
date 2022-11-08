package dev.larsq.moviedb;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;

@Testcontainers
class ApplicationTest {
    String host;

    @Container
    GenericContainer<?> backend = new GenericContainer<>(DockerImageName.parse("larsq/moviedb:1.0.0"))
            .withExposedPorts(8080);

    @BeforeEach
    void setupUrl() {
        host = "http://%s:%s".formatted(backend.getHost(), backend.getMappedPort(8080));
    }

    @DisplayName("can query database")
    @Test
    void can_query_database() {
        given().body("{ \"part\":\"movie\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("%s/".formatted(host))
                .then()
                .statusCode(200);

    }
}
