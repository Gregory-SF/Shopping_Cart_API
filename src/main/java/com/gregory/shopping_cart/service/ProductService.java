package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.dao.ProductDao;
import com.gregory.shopping_cart.exception.InvalidValuesException;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.Product;

public class ProductService {

	ProductDao dao = new ProductDao();

	public void create(Product product) {
		try {
			productIsValid(product);
			findByAttributes(product);
		} catch (NoProductException e) {
			dao.create(product);				
		}
	}

	public List<Product> getAll() {
		return dao.findAll("Product", Product.class);
	}

	public void update(Product product, Product newProduct) {
		try {
			productIsValid(newProduct);
			Product dbProduct = findByAttributes(product);
			dbProduct.setName(newProduct.getName());
			dbProduct.setType(newProduct.getType());
			dbProduct.setUnitValue(newProduct.getUnitValue());
			dao.update(dbProduct);
		} catch (NoResultException e){
			throw new NoProductException(e.getMessage());
		}	
	}

	public Product findById(Long id) {
		Product foundProduct = dao.findById(Product.class, id);
		if(foundProduct != null) {
			return foundProduct;
		} else {
			throw new NoProductException("Product not found!");
		}

	}

	public void delete (Product product) {
		try {
			dao.delete(findByAttributes(product));
		} catch (NoResultException e){
			throw new NoProductException(e.getMessage());
		}
	}

	public Product findByAttributes(Product product) throws NoResultException{
		try {
			return dao.findByAttributes(product);
		} catch (NoResultException e) {
			throw new NoResultException("Product doesn't exist! Operation canceled");
		}
	}

	public void productIsValid(Product product) {
		if(product.getUnitValue()<= 0 || product.getName()== "" || product.getType() == "") return;
		throw new InvalidValuesException("Invalid values!");
	}
}