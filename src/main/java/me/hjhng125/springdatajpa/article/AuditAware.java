package me.hjhng125.springdatajpa.article;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

@Service
public class AuditAware implements AuditorAware<Article> {

    @Override
    public Optional<Article> getCurrentAuditor() {
        System.out.println("looking for current user!");
        return Optional.empty();
    }
}
