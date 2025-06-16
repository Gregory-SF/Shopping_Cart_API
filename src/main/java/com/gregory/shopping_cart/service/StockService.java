


package com.gregory.shopping_cart.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.dao.StockDao;
import com.gregory.shopping_cart.exception.InvalidValuesException;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.Stock;

public class StockService {

	StockDao dao = new StockDao();
	ProductService prodSer = new ProductService(); 

	public void create(Stock stock) {
		validateStock(stock);
		if(dao.findByProductId(stock.getProduct().getId()) == null) {
			dao.create(stock);
		} else System.out.println("This product is already on stock");
	}

	public List<Stock> findAll() {
		return dao.findAll("Stock", Stock.class);
	}

	public void update(Stock stock) {
		validateStock(stock);
		try {
			Stock dbStock = findById(stock.getId());
			dbStock.setQuantity(stock.getQuantity());
			dao.update(dbStock);
		} catch (NoResultException e){
			throw new NoResultException(e.getMessage());
		}
	}
	
	public void update(Long id, Integer quantity) {
		Stock dbStock = dao.findByProductId(id);
		if(dbStock != null) {
			dbStock.setQuantity(quantity);
			dao.update(dbStock);
		} else throw new NoResultException("This product is not on stock!");
	}


	public Stock findById(Long id) throws NoResultException {
		Stock dbStock = dao.findById(Stock.class, id);
		if (dbStock != null) {
			return dbStock;
		} else {
			throw new NoResultException("Stock doesn't exist! Operation canceled");
		}
	}

	public void delete (Long id) {
		try {
			dao.delete(findById(id));
		} catch (NoResultException e){
			throw new NoProductException(e.getMessage());
		}
	}


	private void validateStock (Stock stock){
		if(stock.getQuantity()>=0) {
			try {
				stock.setProduct(prodSer.findByAttributes(stock.getProduct()));
			} catch (NoProductException e) {
				throw new NoProductException(e.getMessage());
			}
		} else throw new InvalidValuesException("Invalid values!");
	}
}