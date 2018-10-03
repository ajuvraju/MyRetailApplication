package com.myretail.app.util;

public class ConstantsUtil {
	//Common Constants
	public static final String PRODUCT = "product";
	public static final String ITEM = "item";
	public static final String PDT_DESC = "product_description";
	public static final String TITLE  = "title";
	public static final String PRICE  = "price";
	public static final String LIST_PRICE  = "listPrice";
	public static final String FORMATTED_PRICE  = "formattedPrice";
	public static final String SET  = "$set";
	public static final String NESTED_FORMATPRICE = "product.price.listPrice.formattedPrice";
	public static final String NESTED_PRICE = "product.price.listPrice.price";
	public static final String TCIN = "product.item.tcin";

	
	//MongoDB details
	public static final int MONGO_PORT=27017;
	public static final String MONGO_HOSTNAME="127.0.0.1";
	public static final String MONGO_DBNAME = "MyRetail";
	public static final String MONGO_COLLECTION = "MyRetailJsonData";
	
	//External Urls ( ideally should be stored in DB properties table)
	public static final String BASE_URL ="http://redsky.target.com/v2/pdp/tcin/";
	public static final String URL_ENDPOINT ="?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
	

}
