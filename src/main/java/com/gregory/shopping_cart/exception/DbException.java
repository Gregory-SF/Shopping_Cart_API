package com.gregory.shopping_cart.exception;

public class DbException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DbException(String msg) {
		super(msg);
	}
}
