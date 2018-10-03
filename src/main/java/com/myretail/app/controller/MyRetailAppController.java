package com.myretail.app.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.app.model.PriceAndCurrency;
import com.myretail.app.model.ProductDetails;
import com.myretail.app.service.IMyRetailAppService;

/**
 * @author Aju
 *
 */
@RestController
public class MyRetailAppController {
	
	@Autowired
	IMyRetailAppService myRetailAppService;
	
	protected final Logger logger = LogManager.getLogger(getClass());
	//To retrieve product data
	@RequestMapping(value="/retrieve_product_data/{productId}",  method = RequestMethod.GET, produces="application/json")
	@ResponseBody	
	public ResponseEntity<?> submit(@PathVariable("productId") final Long productId) throws Exception{
		logger.info("retrieve_product_data start..");
		ProductDetails response  =myRetailAppService.getProductDetails(productId);

		return new ResponseEntity<ProductDetails>(response,HttpStatus.OK);
		
	}
	//to update product price
	@RequestMapping(value="/update_product_price",  method = RequestMethod.PUT)
	@ResponseBody	
	public ResponseEntity<?> put(@RequestBody PriceAndCurrency priceAndCurrency) throws Exception{
		logger.info("update product price start..");
		String response  =myRetailAppService.updateProductPrice(priceAndCurrency);

		return new ResponseEntity<String>(response,HttpStatus.OK);
		
	}
	
}
