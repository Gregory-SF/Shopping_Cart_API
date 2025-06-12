package com.gregory.shopping_cart.dao;

import com.gregory.shopping_cart.model.entities.Product;

public class ProductDao implements AbstractDao<Product>{

	@Override
	public void update() {
	}
	
	public Product getByAttributes(Product product) {
		return em.createQuery("FROM Product WHERE name = :name AND type = :type", Product.class).setParameter("name", product.getName()).setParameter("type", product.getType()).getSingleResult();
	}
}
