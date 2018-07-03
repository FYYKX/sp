package com.example.sp;


import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
    @Test
    public void invalidEmail() {
        List<String> email = StringUtils.getEmail("Hello World! @metioned");

        assertThat(email.size()).isEqualTo(0);
    }

    @Test
    public void validEmail() {
        List<String> email = StringUtils.getEmail("Hello World! kate@example.com");

        assertThat(email.size()).isEqualTo(1);
        assertThat(email).contains("kate@example.com");
    }
}