package com.myretail.app.dao;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.myretail.app.model.PriceAndCurrency;
import com.myretail.app.util.ConstantsUtil;
import com.myretail.app.util.CurrencyMap;

/**
 * @author Aju
 *
 */
public class MyRetailAppDaoImpl implements IMyRetailAppDao {

	protected final Logger log = LogManager.getLogger(getClass());

	BasicDBObject query = new BasicDBObject();
	BasicDBObject field = new BasicDBObject();
	DBCollection collection = MongoFactory.getMongoDBCollection("MyRetail", "MyRetailJsonData");

	public void saveToMongoDB(String jsonString) {
		log.info("Product data not present in MongoDB,inserting....");
		DBObject object = (DBObject) JSON.parse(jsonString);
		collection.insert(object);
		DBCursor cursor = collection.find();
		try {
			log.info(cursor.next());
		} finally {
			cursor.close();
		}

		log.info("Product data inserted");

	}
	//to check if product details are already present in DB
	public boolean isProductDetailsPresent(Long productId) {
		field.put(ConstantsUtil.TCIN, productId.toString());
		DBCursor cursor = collection.find(query, field);
		if (cursor.count() > 0)
			return true;
		return false;
	}
	//to get the product formattedprice from JSON data stored in DB
	public PriceAndCurrency getPriceAndCurrency(Long productId) {
		
		PriceAndCurrency result = new PriceAndCurrency();
		query = new BasicDBObject(ConstantsUtil.TCIN, productId.toString());
		field = new BasicDBObject();
		field.put(ConstantsUtil.NESTED_FORMATPRICE, "");
		DBCursor cursor = collection.find(query, field); 
		JsonNode node;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if(cursor.hasNext()) {
			node = objectMapper.readValue(cursor.next().toString(), JsonNode.class);
			JsonNode formattedPrice = null;
			formattedPrice = node.get(ConstantsUtil.PRODUCT).get(ConstantsUtil.PRICE).get(ConstantsUtil.LIST_PRICE).get(ConstantsUtil.FORMATTED_PRICE);
			String priceval = formattedPrice.asText();
			String currency =  priceval.split("[0-9]")[0];
			String price = priceval.replace(currency, "");
			result.setProductId(productId);
			result.setCurrency(currency);
			result.setPrice(Double.parseDouble(price));
			log.info("currency symbol:" + currency);
			log.info("price:" + result);}
			else
				log.error("No result found");
		} catch (JsonParseException e) {
			log.error("Error parsing JSON data from DB",e);
		} catch (JsonMappingException e) {
			log.error("Error mapping JSON data from DB",e);
		} catch (IOException e) {
			log.error("IOError while parsing JSON data from DB",e);
		} finally {
			cursor.close();
		}
		return result;
	}

	public String updateProductPrice(PriceAndCurrency priceAndCurrency) {
		// TODO Auto-generated method stub
		String currencySymbol = CurrencyMap.getCurrencySymbol(priceAndCurrency.getCurrency());
		if("?".equalsIgnoreCase(currencySymbol))
			return "No such currency found!, Not able to Update";
		StringBuilder priceVal = new  StringBuilder(currencySymbol);
		//String priceVal = priceAndCurrency.getCurrency()+priceAndCurrency.getPrice();
		priceVal.append(priceAndCurrency.getPrice());
		BasicDBObject setUpdateQuery = new BasicDBObject();
		BasicDBObject priceUpdateObj = new BasicDBObject();
		priceUpdateObj.append(ConstantsUtil.NESTED_FORMATPRICE, priceVal.toString());
		priceUpdateObj.append(ConstantsUtil.NESTED_PRICE, priceAndCurrency.getPrice());
		setUpdateQuery.append(ConstantsUtil.SET,priceUpdateObj );
		//newDocument.append("$set", new BasicDBObject().append("product.price.listPrice.price", priceAndCurrency.getPrice()));
		BasicDBObject searchQuery = new BasicDBObject().append(ConstantsUtil.TCIN,priceAndCurrency.getProductId().toString());
		
		WriteResult res = collection.update(searchQuery, setUpdateQuery);
		log.info(res);
		if(res.isUpdateOfExisting())
			return "Successfully updated";
		else
			return "Update failed";
	}
	

}
