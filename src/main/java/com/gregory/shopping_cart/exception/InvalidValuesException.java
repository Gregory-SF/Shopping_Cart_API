package com.gregory.shopping_cart.exception;

public class InvalidValuesException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidValuesException(String msg) {
		super(msg);
	}
}
