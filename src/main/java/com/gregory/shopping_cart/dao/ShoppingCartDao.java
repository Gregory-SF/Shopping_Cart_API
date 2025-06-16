package com.gregory.shopping_cart.dao;

import com.gregory.shopping_cart.model.entities.ShoppingCart;

public class ShoppingCartDao implements AbstractDao<ShoppingCart>{
	
	public ShoppingCart findByProductId(Long id) {
		return em.createQuery("FROM ShoppingCart WHERE product_id = :productId", ShoppingCart.class).setParameter("productId", id).getSingleResult();
	}
	
	public Integer findQuantityStockByProductId(Long id) {
		return em.createQuery("Select quantity FROM Stock WHERE product_id = :productId", Integer.class).setParameter("productId", id).getSingleResult();
	}
}
