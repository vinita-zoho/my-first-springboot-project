package com.myproject.banking.exceptions;

public class AccountNotFoundException extends RuntimeException
{
	public AccountNotFoundException(String message)
	{
		super(message);
	}
}
