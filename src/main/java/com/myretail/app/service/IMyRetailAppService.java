package com.myretail.app.service;

import com.myretail.app.model.PriceAndCurrency;
import com.myretail.app.model.ProductDetails;

/**
 * @author Aju
 *
 */
public interface IMyRetailAppService {
	
	public ProductDetails getProductDetails(Long productId);
	
	String updateProductPrice(PriceAndCurrency priceAndCurrency);

}
