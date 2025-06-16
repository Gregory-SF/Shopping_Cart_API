package com.gregory.shopping_cart.controller;

import java.util.List;

import com.gregory.shopping_cart.model.entities.Product;
import com.gregory.shopping_cart.service.ProductService;

public class ProductController {

	ProductService service = new ProductService();
	
	public void create(Product product) {
		service.create(product);
	}

	public void update(Product product, Product newProduct) {
		service.update(product, newProduct);
	}
	
	public void delete(Product product) {
		service.delete(product);
	}
	
	public List<Product> findAll() {
		return service.getAll();
	}
	
	public Product findById(Long id) {
		return service.findById(id);
	}
}
