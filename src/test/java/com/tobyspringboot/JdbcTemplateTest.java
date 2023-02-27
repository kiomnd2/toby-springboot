package com.tobyspringboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HellobootTest
public class JdbcTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS HELLO(name varchar(50) primary key, count int)");

    }

    @Test
    void insertAndQuery() {
        jdbcTemplate.update("insert into hello values(?,?) ", "kim", 3);
        jdbcTemplate.update("insert into hello values(?,?) ", "Spring", 3);

        Long count = jdbcTemplate.queryForObject("select count(*) from HELLO", Long.class);
        Assertions.assertThat(count).isEqualTo(2);
    }
}
