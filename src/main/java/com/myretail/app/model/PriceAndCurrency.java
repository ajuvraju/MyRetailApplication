package com.myretail.app.model;

import java.io.Serializable;

public class PriceAndCurrency implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long productId;
	
	private String currency;
	
	private Double price;
	

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
