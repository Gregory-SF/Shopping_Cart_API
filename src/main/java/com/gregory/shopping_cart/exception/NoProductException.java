package com.gregory.shopping_cart.exception;

import javax.persistence.NoResultException;

public class NoProductException extends NoResultException{

	private static final long serialVersionUID = 1L;

	public NoProductException(String msg) {
		super(msg);
	}

}
