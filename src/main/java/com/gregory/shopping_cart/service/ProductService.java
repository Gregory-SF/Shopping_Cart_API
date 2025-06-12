package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.dao.ProductDao;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.Product;

public class ProductService {

	ProductDao dao = new ProductDao();
	
	public void create(Product product) {
		dao.create(product);
	}
	
	public List<Product> getAll() {
		return dao.getAll("Product", Product.class);
	}
	
	public void update(Product product) {
	}
	
	public void delete (Product product) {
		try {
			dao.delete(findByAttributes(product));
		} catch (NoResultException e){
			throw new NoProductException(e.getMessage());
		}
	}
	
	private Product findByAttributes(Product product) throws NoResultException{
		try {
			return dao.getByAttributes(product);
		} catch (NoResultException e) {
			throw new NoResultException("Product doesn't exist! Operation canceled");
		}
	}	
}