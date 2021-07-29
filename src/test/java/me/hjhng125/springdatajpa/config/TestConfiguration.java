package me.hjhng125.springdatajpa.config;

import me.hjhng125.springdatajpa.article.AuditAware;
import me.hjhng125.springdatajpa.study.StudyEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public StudyEventListener studyEventListener() {
        return new StudyEventListener();
    }

    @Bean
    public AuditAware auditAware() {
        return new AuditAware();
    }
}
