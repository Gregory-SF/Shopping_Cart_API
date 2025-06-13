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
		return dao.findAll("Product", Product.class);
	}

	public void update(Product product, Product newProduct) {
		if(productIsValid(newProduct)) {
			try {
				Product dbProduct = findByAttributes(product);
				dbProduct.setName(newProduct.getName());
				dbProduct.setType(newProduct.getType());
				dbProduct.setUnitValue(newProduct.getUnitValue());
				dao.update(dbProduct);
			} catch (NoResultException e){
				throw new NoProductException(e.getMessage());
			}
		}	
	}

	public Product findById(Long id) {
		return dao.findById(Product.class, id);
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
			return dao.findByAttributes(product);
		} catch (NoResultException e) {
			throw new NoResultException("Product doesn't exist! Operation canceled");
		}
	}

	private boolean productIsValid(Product product) {
		if(product.getUnitValue()<= 0 || product.getName()== "" || product.getType() == "") return false;
		return true;
	}
}