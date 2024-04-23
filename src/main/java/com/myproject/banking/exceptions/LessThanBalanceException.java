package com.myproject.banking.exceptions;

public class LessThanBalanceException extends RuntimeException
{
	public LessThanBalanceException(String message)
	{
		super(message);
	}
}
