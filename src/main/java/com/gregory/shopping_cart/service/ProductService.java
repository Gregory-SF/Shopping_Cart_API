package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.dao.ProductDao;
import com.gregory.shopping_cart.model.entities.Product;

public class ProductService {

	ProductDao dao = new ProductDao();
	
	public void create(Product product) {
		dao.create(product);
	}
	
	public List<Product> getAll() {
		return dao.getAll("Product", Product.class);
	}
}