package me.hjhng125.springdatajpa;


import static org.assertj.core.api.Java6Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class test {

    @Test
    public void parse() {

        String localDateTime = "2021-05-31 00:00:00" ;

        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        assertThat(dateTime).isEqualTo(LocalDateTime.of(2021,5,31,0,0,0));
    }
}
