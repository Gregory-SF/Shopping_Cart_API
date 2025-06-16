package com.gregory.shopping_cart.controller;

import java.util.List;

import com.gregory.shopping_cart.model.entities.ShoppingCart;
import com.gregory.shopping_cart.service.ShoppingCartService;

public class ShoppingCartController {
	ShoppingCartService service = new ShoppingCartService();
	
	public void create(ShoppingCart shoppingCart) {
		service.create(shoppingCart);
	}

	public void updateByProductId(Long id, Integer quantity) {
		service.update(id, quantity);
	}

	public void update(ShoppingCart shoppingCart) {
		if(shoppingCart.getId() != null) service.update(shoppingCart);
		else System.out.println("Insert an Id to the shoppingCart!");
	}
	
//	public void delete(Long id) {
//		service.delete(id);
//	}
	
	public List<ShoppingCart> findAll() {
		return service.findAll();
	}
	
//	public ShoppingCart findById(Long id) {
//		return service.findById(id);
//	}

}
