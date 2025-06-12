package com.gregory.shopping_cart.controller;

import java.util.List;

import com.gregory.shopping_cart.model.entities.Product;
import com.gregory.shopping_cart.service.ProductService;

public class ProductController {

	ProductService service = new ProductService();
	
	public void create(Product produto) {
		service.create(produto);
	}
	
	public List<Product> getAll() {
		return service.getAll();
	}
}
