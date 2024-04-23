package com.myproject.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.banking.dto.AccountDto;
import com.myproject.banking.entity.Account;
import com.myproject.banking.exceptions.AccountNotFoundException;
import com.myproject.banking.exceptions.LessThanBalanceException;
import com.myproject.banking.exceptions.MinimumBalanceException;
import com.myproject.banking.mapper.AccountMapper;
import com.myproject.banking.repository.AccountRepository;
import com.myproject.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	private AccountRepository accountRepository;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {

		Account account = AccountMapper.mapToAccount(accountDto);

		if(account.getBalance() <= 0)
		{
			throw new MinimumBalanceException("Minimum Opening Balance Should be Greater than 0");
		}
		Account savedAccount = accountRepository.save(account);

		return AccountMapper.mapToAccountDto(savedAccount);

	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElseThrow( () -> new AccountNotFoundException("Account Does not exist - ID : " + id));
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRepository.findById(id).orElseThrow( () -> new AccountNotFoundException("Account Does not exist - ID : " + id));

		double total = account.getBalance() + amount;

		account.setBalance(total);

		Account savedAccount = accountRepository.save(account);

		return AccountMapper.mapToAccountDto(savedAccount);

	}

	@Override
	public AccountDto withdraw(Long id, double amount) {

		Account account = accountRepository.findById(id).orElseThrow( () -> new AccountNotFoundException("Account Does not exist - ID : " + id));

		if(account.getBalance() < amount)
		{
			throw new LessThanBalanceException("Insufficient Amount -- Account Balance -- "+account.getBalance());
		}

		double total = account.getBalance() - amount;

		if(total <= 0)
		{
			throw new MinimumBalanceException("Minimum Balance Should be Greater than 0");
		}
		account.setBalance(total);

		Account savedAccount = accountRepository.save(account);

		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		List<AccountDto> accountDtoList = accounts.stream().map(account -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
		return accountDtoList;
	}

	@Override
	public void deleteAccount(Long id) {

		accountRepository.findById(id).orElseThrow( () -> new AccountNotFoundException("Account Does not exist - ID : " + id));

		accountRepository.deleteById(id);
	}

}
