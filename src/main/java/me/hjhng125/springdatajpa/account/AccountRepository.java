package me.hjhng125.springdatajpa.account;

import me.hjhng125.springdatajpa.common.SimpleRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AccountRepository extends SimpleRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

}
