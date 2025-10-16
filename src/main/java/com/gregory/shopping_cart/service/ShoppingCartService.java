
package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.dao.ShoppingCartDao;
import com.gregory.shopping_cart.dao.StockDao;
import com.gregory.shopping_cart.exception.InvalidValuesException;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.ShoppingCart;

public class ShoppingCartService {

	ShoppingCartDao dao = new ShoppingCartDao();
	StockService stockSer= new StockService();
	ProductService prodSer = new ProductService(); 

	public void create(ShoppingCart shoppingCart) {
		try {
			validateShoppingCart(shoppingCart);
			dao.findByProductId(shoppingCart.getProduct().getId());
			System.out.println("This product is already on shoppingCart");
		} catch (NoProductException e) {
			System.out.println(e.getMessage());
		} catch (NoResultException e) {	
			dao.create(shoppingCart);
			stockSer.sell(shoppingCart.getProduct().getId(), shoppingCart.getQuantity());
		}
	}

	public List<ShoppingCart> findAll() {
		return dao.findAll("ShoppingCart", ShoppingCart.class);
	}
	

	public void update(ShoppingCart shoppingCart) {
		try {
			validateShoppingCart(shoppingCart);
			ShoppingCart dbShoppingCart = findById(shoppingCart.getId());
			dbShoppingCart.setQuantity(shoppingCart.getQuantity());
			dao.update(dbShoppingCart);
		} catch (NoProductException e) {
			System.out.println(e.getMessage());
		} catch (NoResultException e){
			throw new NoResultException(e.getMessage());
		}
	}
	
	public void update(Long productId, Integer quantity) {
		if(quantity >=0) {
			try {
				ShoppingCart dbShoppingCart = dao.findByProductId(productId);
				dbShoppingCart.setQuantity(quantity);
				dao.update(dbShoppingCart);
			} catch (NoResultException e) {
				throw new NoProductException("This product is not on shoppingCart!");
			}
		} else throw new InvalidValuesException("Invalid values!");
	}
	
	public void delete(Long id) {
		try {
			dao.delete(findById(id));
		} catch (NoResultException e){
			throw new NoProductException(e.getMessage());
		}
	}

	public ShoppingCart findById(Long id) throws NoResultException {
		ShoppingCart dbShoppingCart = dao.findById(ShoppingCart.class, id);
		if (dbShoppingCart != null) {
			return dbShoppingCart;
		} else {
			throw new NoResultException("ShoppingCart doesn't exist! Operation canceled");
		}
	}

	private void validateShoppingCart(ShoppingCart shoppingCart){
		if(shoppingCart.getQuantity()>=0) {
			try {
				if(shoppingCart.getProduct().getId() != null) {
					shoppingCart.setProduct(prodSer.findById(shoppingCart.getProduct().getId()));
				} else shoppingCart.setProduct(prodSer.findByAttributes(shoppingCart.getProduct()));
				if(dao.findQuantityStockByProductId(shoppingCart.getProduct().getId()) < shoppingCart.getQuantity()) {
					throw new InvalidValuesException("There's not enough itens!");
				}
			} catch (NoProductException e) {
				throw new NoProductException(e.getMessage());
			}
		} else throw new InvalidValuesException("Invalid values!");
	}
	
	public Double getTotalValue() {
		Double value = 0.0;
		List<ShoppingCart> cart = findAll();
		for (ShoppingCart shoppingCart : cart) {
			value = value += shoppingCart.getProduct().getUnitValue() * shoppingCart.getQuantity();
		}
		return value;
	}
}