package com.tobyspringboot;

import com.tobyspringboot.hello.HelloRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HellobootTest
public class HelloRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HelloRepository helloRepository;


    @BeforeEach
    void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS HELLO(name varchar(50) primary key, count int)");
    }

    @Test
    void findHelloFailed() {
        Assertions.assertThat(helloRepository.findHello("kim")).isNull();
    }

    @Test
    void increaseCount() {
        Assertions.assertThat(helloRepository.countOf("kim")).isEqualTo(0);

        helloRepository.increaseCount("kim");
        Assertions.assertThat(helloRepository.countOf("kim")).isEqualTo(1);

        helloRepository.increaseCount("kim");
        Assertions.assertThat(helloRepository.countOf("kim")).isEqualTo(2);
    }
}
