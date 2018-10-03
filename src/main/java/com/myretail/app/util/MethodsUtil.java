package com.myretail.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MethodsUtil {
	//convert model object to json string
	public static String pojoToJson(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		if(obj == null)
			return null;
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
