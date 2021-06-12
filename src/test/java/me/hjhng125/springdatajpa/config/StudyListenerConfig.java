package me.hjhng125.springdatajpa.config;

import me.hjhng125.springdatajpa.study.StudyEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudyListenerConfig {

    @Bean
    public StudyEventListener studyEventListener() {
        return new StudyEventListener();
    }
}
