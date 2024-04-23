package com.myproject.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.banking.dto.AccountDto;
import com.myproject.banking.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	private AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	
	// Add Account REST API
	@PostMapping(path = "/create")
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto)
	{
		return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
	}
	
	// Get Account REST API
	@GetMapping(path = "/get/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id)
	{
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}
	
	// Deposit REST API
	@PutMapping(path = "/deposit/{id}")
	public ResponseEntity<AccountDto> deposit(@PathVariable long id, @RequestBody Map<String, Double> request)
	{
		Double amount = request.get("amount");
		AccountDto accountDto = accountService.deposit(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	// Withdraw REST API
	@PutMapping(path = "/withdraw/{id}")
	public ResponseEntity<AccountDto> withdraw(@PathVariable long id, @RequestBody Map<String, Double> request)
	{
		Double amount = request.get("amount");
		AccountDto accountDto = accountService.withdraw(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	// Get All Accounts REST API
	@GetMapping(path = "/getall")
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	// Delete Account REST API
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteAccount(Long id)
	{
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account is deleted Successfully");
	}

	
	

}
