package com.tobyspringboot;

import com.tobyspringboot.hello.HelloController;
import com.tobyspringboot.hello.SimpleHelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRest {

    @Test
    void hello() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:9090/hello?name={name}", String.class, "Spring");

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
                .startsWith(MediaType.TEXT_PLAIN_VALUE)).isTrue();
        assertThat(res.getBody().trim()).isEqualTo("hello Spring");
    }

    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService();

        String test = helloService.sayHello("test");

        Assertions.assertThat(test).isEqualTo("hello test");
    }

    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("test");

        Assertions.assertThat(ret).isEqualTo("test");
    }


}
