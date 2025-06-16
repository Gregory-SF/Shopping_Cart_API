package com.gregory.shopping_cart.controller;

import java.util.List;

import com.gregory.shopping_cart.model.entities.Stock;
import com.gregory.shopping_cart.service.StockService;

public class StockController {
	StockService service = new StockService();
	
	public void create(Stock stock) {
		service.create(stock);
	}

	public void updateByProductId(Long id, Integer quantity) {
		service.update(id, quantity);
	}

	public void update(Stock stock) {
		if(stock.getId() != null) service.update(stock);
		else System.out.println("Insert an Id to the stock!");
	}
	
	public void delete(Long id) {
		service.delete(id);
	}
	
	public List<Stock> findAll() {
		return service.findAll();
	}
	
	public Stock findById(Long id) {
		return service.findById(id);
	}

}
