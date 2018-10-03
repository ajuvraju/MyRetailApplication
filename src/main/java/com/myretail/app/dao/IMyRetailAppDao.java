package com.myretail.app.dao;

import com.myretail.app.model.PriceAndCurrency;

public interface IMyRetailAppDao {
	
	void saveToMongoDB(String jsonString);
	
	boolean isProductDetailsPresent(Long productId);
	
	 PriceAndCurrency getPriceAndCurrency(Long productId);
	
	 public String updateProductPrice(PriceAndCurrency priceAndCurrency);
}
