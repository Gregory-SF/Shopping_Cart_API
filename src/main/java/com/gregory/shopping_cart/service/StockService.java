package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.dao.StockDao;
import com.gregory.shopping_cart.exception.InvalidValuesException;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.Product;
import com.gregory.shopping_cart.model.entities.Stock;

public class StockService {

	StockDao dao = new StockDao();
	ProductService prodSer = new ProductService(); 

	public void create(Stock stock) {
		try {
			validateStock(stock);
			dao.findByProductId(stock.getProduct().getId());
			System.out.println("This product is already on stock");
		} catch (NoProductException e) {
			System.out.println(e.getMessage());
		} catch (NoResultException e) {	
			dao.create(stock);
		}
	}

	public List<Stock> findAll() {
		return dao.findAll("Stock", Stock.class);
	}
	

	public void update(Stock stock) {
		try {
			validateStock(stock);
			Stock dbStock = findById(stock.getId());
			dbStock.setQuantity(stock.getQuantity());
			dao.update(dbStock);
		} catch (NoProductException e) {
			System.out.println(e.getMessage());
		} catch (NoResultException e){
			throw new NoResultException(e.getMessage());
		}
	}
	
	public void update(Long productId, Integer quantity) {
		if(quantity >=0) {
			try {
				Stock dbStock = dao.findByProductId(productId);
				dbStock.setQuantity(quantity);
				dao.update(dbStock);
			} catch (NoResultException e) {
				throw new NoProductException("This product is not on stock!");
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

	public Stock findById(Long id) throws NoResultException {
		Stock dbStock = dao.findById(Stock.class, id);
		if (dbStock != null) {
			return dbStock;
		} else {
			throw new NoResultException("Stock doesn't exist! Operation canceled");
		}
	}

	private void validateStock(Stock stock){
		if(stock.getQuantity()>=0) {
			try {
				if(stock.getProduct().getId() != null) {
					stock.setProduct(prodSer.findById(stock.getProduct().getId()));
				} else stock.setProduct(prodSer.findByAttributes(stock.getProduct()));
			} catch (NoProductException e) {
				throw new NoProductException(e.getMessage());
			}
		} else throw new InvalidValuesException("Invalid values!");
	}
	
	public void sell(Long productId, Integer quantity) {
		Stock dbStock = dao.findByProductId(productId);
		dbStock.sell(quantity);
		dao.update(dbStock);
	}
}