package com.gregory.shopping_cart.dao;

import com.gregory.shopping_cart.model.entities.Stock;

public class StockDao implements AbstractDao<Stock>{
	
	public Stock findByProductId(Long id) {
		return em.createQuery("FROM Stock WHERE product_id = :productId", Stock.class).setParameter("productId", id).getSingleResult();
	}
	
	// Daria pra adicionar um find by categoria
}
