package com.myretail.app.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.app.dao.IMyRetailAppDao;
import com.myretail.app.model.CurrentPrice;
import com.myretail.app.model.PriceAndCurrency;
import com.myretail.app.model.ProductDetails;
import com.myretail.app.util.ConstantsUtil;
import com.myretail.app.util.CurrencyMap;

/**
 * @author Aju
 *
 */
public class MyRetailAppServiceImpl implements IMyRetailAppService {
	
	protected final Logger log= LogManager.getLogger(getClass());
	
	@Autowired
	IMyRetailAppDao myRetailAppDao;
	
	RestTemplate restTemplate = new RestTemplate();
	
	//get product details from external api and MongoDB, combine it and return the result
	public ProductDetails getProductDetails(Long productId) {
		// TODO Auto-generated method stub		
		//do http get call
        ResponseEntity<String> productDetails = doRestTemplateCall(ConstantsUtil.BASE_URL+productId+ConstantsUtil.URL_ENDPOINT);
        ProductDetails pdtDetailsResponse = new ProductDetails();
        //To handle redirect from http to https
        HttpHeaders httpHeaders = productDetails.getHeaders();
        HttpStatus statusCode = productDetails.getStatusCode();
        if (statusCode.equals(HttpStatus.MOVED_PERMANENTLY) || statusCode.equals(HttpStatus.FOUND) || statusCode.equals(HttpStatus.SEE_OTHER)) {
            if (httpHeaders.getLocation() != null) {
            	productDetails = doRestTemplateCall(httpHeaders.getLocation().toString());
            } else {
                throw new RuntimeException();
            }
        }
        if(null!= productDetails) { //to check if productdetail response is received 
        log.info("product details found:"+productDetails.getBody());
       
        JsonNode node;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
			node = objectMapper.readValue(productDetails.getBody(), JsonNode.class);
			if(!myRetailAppDao.isProductDetailsPresent(productId))	  //check if product data is present in MongoDB
				myRetailAppDao.saveToMongoDB(productDetails.getBody());     // if not, save product data to MongoDB
			else
				log.info("Data for prductId "+productId+" is already present in DB, skipping insert");
			JsonNode title = null;
			//get product title from the nested json
			title = node.get(ConstantsUtil.PRODUCT).get(ConstantsUtil.ITEM).get(ConstantsUtil.PDT_DESC).get(ConstantsUtil.TITLE);
			log.info("title:"+title.asText());
			//set the product details response
			pdtDetailsResponse.setName(title.asText());
			pdtDetailsResponse.setId(productId);
			CurrentPrice currentPrice = new CurrentPrice();
			PriceAndCurrency priceAndCurr = myRetailAppDao.getPriceAndCurrency(productId); //get price and currency details
			currentPrice.setCurrency_code(CurrencyMap.getCurrencyCode(priceAndCurr.getCurrency()));
			currentPrice.setValue(priceAndCurr.getPrice()); 
			pdtDetailsResponse.setCurrentPrice(currentPrice);
			
			log.info("pdtDetailsResponse:"+pdtDetailsResponse.toString());
		} catch (IOException e) {
			log.error("Error while calling external api for title",e);
		}
        }
       
        	return pdtDetailsResponse;
	}
	
	private ResponseEntity<String> doRestTemplateCall(String url) {
		ResponseEntity<String> responseEntity = null;
        try {
            responseEntity =  restTemplate.getForEntity(url.trim(), String.class);
        } catch (Exception ex) {
           log.error("Unable to retrieve the product details");
        }
        return responseEntity;
    }
	//update the product price
	public String updateProductPrice(PriceAndCurrency priceAndCurrency) {
		// TODO Auto-generated method stub
		String response = myRetailAppDao.updateProductPrice(priceAndCurrency);
		return response;
	}
	
	

}
