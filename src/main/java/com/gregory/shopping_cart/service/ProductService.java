package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import com.gregory.shopping_cart.dao.ProductDao;
import com.gregory.shopping_cart.dao.StockDao;
import com.gregory.shopping_cart.exception.DbException;
import com.gregory.shopping_cart.exception.InvalidValuesException;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.Product;

public class ProductService {

	ProductDao dao = new ProductDao();
	StockDao stockDao= new StockDao();

	public void create(Product product) {
		try {
			productIsValid(product);
			findByAttributes(product);
			System.out.println("Product already registered");
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
		} catch (NoResultException e) {
			throw new NoProductException(e.getMessage());
		}
	}

	public Product findById(Long id) {
		Product foundProduct = dao.findById(Product.class, id);
		if (foundProduct != null) {
			return foundProduct;
		} else {
			throw new NoProductException("Product not found!");
		}
	}

	public void delete(Product product) {
		try {
			dao.delete(findByAttributes(product));
		} catch (NoProductException e) {
			throw new NoProductException(e.getMessage());
		} catch (RollbackException e) {
			throw new DbException("This product is on stock, for safety measures, delete it from stock first!");
		}
	}

	public Product findByAttributes(Product product) throws NoResultException {
		try {
			return dao.findByAttributes(product);
		} catch (NoResultException e) {
			throw new NoProductException("Product doesn't exist! Operation canceled");
		}
	}

	private void productIsValid(Product product) {
		if (product.getUnitValue() <= 0 || product.getName() == "" || product.getType() == "") {
			throw new InvalidValuesException("Invalid values!");
		}
	}
}