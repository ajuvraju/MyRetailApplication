package com.myretail.app.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CurrencyMap {

	private static final Map<String,String> currencyUtils = getCurrencyMap();

	    private static Map getCurrencyMap() 
	    { 
	    	Map<String,String> currencyUtil = new HashMap<String,String>();
	    	currencyUtil.put("$","USD");   // US Dollar
	    	currencyUtil.put("€","EUR");	// Euro
	    	currencyUtil.put("₡","CRC");	// Costa Rican Colón
	    	currencyUtil.put("£","GBP");	// British Pound Sterling
	    	currencyUtil.put("₪","ILS");	// Israeli New Sheqel
	    	currencyUtil.put("¥","JPY");	// Japanese Yen
	    	currencyUtil.put("₩","KRW");	// South Korean Won
	    	currencyUtil.put("₦","NGN");	// Nigerian Naira
	    	currencyUtil.put("₱","PHP");	// Philippine Peso
	    	currencyUtil.put("₲","PYG");	// Paraguayan Guarani
	    	currencyUtil.put("฿","THB");	// Thai Baht
	    	currencyUtil.put("₴","UAH");	// Ukrainian Hryvnia
	    	currencyUtil.put("₫","VND");	// Vietnamese Dong
	    	currencyUtil.put("zł","PLN");  // Polish Zloty
	    	currencyUtil.put("₹","INR");  // Indian Rupee
	    	return Collections.unmodifiableMap(currencyUtil);
	    }  
	    //Get currency code from symbol 
	    public static String getCurrencyCode(String symbol) {
	    	if(currencyUtils.containsKey(symbol))
	    		 return currencyUtils.get(symbol);
	    	else
	    		return "UNK";  //unknown
	    }
	    //get currency symbol from code
	    public static String getCurrencySymbol(String currCode) {
	    	if(currencyUtils.containsValue(currCode)) {
	    		for (Entry<String, String> entry : currencyUtils.entrySet()) {
	                if (entry.getValue().equalsIgnoreCase(currCode)) {
	                    return entry.getKey();
	                }
	            }
	    		return "?";
	    		
	    	}
	    	else
	    		return "?";
	    }
	    
	    
	
	
	
	

}
