package me.hjhng125.springdatajpa.account;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void crud() {
        Account account = new Account();
        account.setName("hjhng");
        account.setPassword("pass");
        account.setEmail("hjhng125@nate.com");

        boolean contains = accountRepository.contains(account);
        assertThat(contains).isFalse();

        accountRepository.save(account);

        boolean containsAfterSave = accountRepository.contains(account);
        assertThat(containsAfterSave).isTrue();

    }
}