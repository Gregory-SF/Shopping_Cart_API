


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

	public void update(Stock stock, Stock newStock) {
//		if(stockIsValid(newStock)) {
//			try {
//				Stock dbStock = findByAttributes(stock);
//				dbStock.setName(newStock.getName());
//				dbStock.setType(newStock.getType());
//				dbStock.setUnitValue(newStock.getUnitValue());
//				dao.update(dbStock);
//			} catch (NoResultException e){
//				throw new NoStockException(e.getMessage());
//			}
//		}	
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