package com.gregory.shopping_cart.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import jakarta.persistence.JoinColumn;

@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private Integer quantity;
	static Double totalValue = 0.0;

	public ShoppingCart() {
	}
	
	public ShoppingCart(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", product=" + product + ", totalValue=" + totalValue + "]";
	}
}
