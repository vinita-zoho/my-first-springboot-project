package com.myproject.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
