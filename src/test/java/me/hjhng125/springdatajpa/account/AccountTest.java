package me.hjhng125.springdatajpa.account;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void account_java_bean_spec_test() {
        Account account = new Account();
        account.setEmail("hjhng125@nate.com");
        account.setName("hjhng");
        account.setPassword("pass2");

        assertThat(account).isNotNull();
    }
}