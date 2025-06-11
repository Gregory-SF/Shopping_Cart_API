package com.gregory.shopping_cart.service;

import com.gregory.shopping_cart.dao.AbstractDao;
import com.gregory.shopping_cart.dao.ProductDao;
import com.gregory.shopping_cart.model.entities.Product;

public class ProductService {

	AbstractDao<Product> dao = new ProductDao();
	
	public void create(Product product) {
		dao.create(product);
	}
}
