package com.myproject.banking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.myproject.banking.controller.AccountController;
import com.myproject.banking.dto.AccountDto;
import com.myproject.banking.exceptions.AccountNotFoundException;
import com.myproject.banking.exceptions.LessThanBalanceException;
import com.myproject.banking.exceptions.MinimumBalanceException;
import com.myproject.banking.repository.AccountRepository;
import com.myproject.banking.service.impl.AccountServiceImpl;

@SpringBootTest
class AccountControllerTests {
	
	private AccountController accountController;
	
	private AccountRepository accountRepository;
	
	@Autowired
	public AccountControllerTests(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@BeforeEach
	public void setUp() {
		this.accountController = new AccountController(new AccountServiceImpl(this.accountRepository));
	}
	
	// getAccountById
	@Test
	void getAccountByValidId() {
		assertThat(accountController.getAccountById(2L).getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void getAccountByInValidId() {
		assertThrows(AccountNotFoundException.class, () -> accountController.getAccountById(200L));
	}
	
	// addAccount
	@Test
	void addAccountWithValidBalance()
	{
		AccountDto testAccount = new AccountDto("Test", 100);
		assertThat(accountController.addAccount(testAccount).getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	void addAccountWithLessThanMinimumBalance()
	{
		AccountDto testAccount = new AccountDto("Test", 0);
		assertThrows(MinimumBalanceException.class, () -> accountController.addAccount(testAccount));
	}
	
	// deposit
	@Test
	void depositWithValidId() {
		Map<String, Double> inputMap = new HashMap<String, Double>();
		inputMap.put("amount" , 300.0);
		assertThat(accountController.deposit(2L, inputMap).getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void depositWithInValidId() {
		Map<String, Double> inputMap = new HashMap<String, Double>();
		inputMap.put("amount" , 300.0);
		assertThrows(AccountNotFoundException.class, () -> accountController.deposit(-10, inputMap));
	}
	
	//withdraw
	@Test
	void withdrawWithValidId() {
		Map<String, Double> inputMap = new HashMap<String, Double>();
		inputMap.put("amount" , 300.0);
		assertThat(accountController.withdraw(2L, inputMap).getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void withdrawWithInValidId() {
		Map<String, Double> inputMap = new HashMap<String, Double>();
		inputMap.put("amount" , 300.0);
		assertThrows(AccountNotFoundException.class, () -> accountController.withdraw(-10, inputMap));
	}
	
	@Test
	void withdrawWithoutMinimumBalance() {
		Map<String, Double> inputMap = new HashMap<String, Double>();
		inputMap.put("amount" , 300000.0);
		assertThrows(LessThanBalanceException.class, () -> accountController.withdraw(2, inputMap));
	}
	
	// getAllAccounts
	@Test
	void getAllAccountsOK() {
		assertThat(accountController.getAllAccounts().getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	// Delete Account By ID
	@Test
	void deleteAccountWithValidId() {
		assertThat(accountController.deleteAccount(3L).getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void deleteAccountWithInValidId() {
		assertThrows(AccountNotFoundException.class, () -> accountController.deleteAccount(200L));
	}
}
