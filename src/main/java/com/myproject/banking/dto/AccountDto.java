package com.myproject.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
	
	public AccountDto(String accountHolderName, double balance)
	{
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}
	
	private Long id;
	
	private String accountHolderName;
	
	private double balance;

}
