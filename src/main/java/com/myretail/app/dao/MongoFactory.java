package com.myretail.app.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.myretail.app.util.ConstantsUtil;

/**
 * @author Aju
 *
 */
public class MongoFactory {

	private static MongoClient mongo;

	public static MongoClient getMongoConnection() {
		final Logger log = LogManager.getLogger(MongoFactory.class);
		int port = ConstantsUtil.MONGO_PORT;
		String hostName = ConstantsUtil.MONGO_HOSTNAME;
		
		if (null == mongo) {
			try {
				mongo = new MongoClient(hostName, port);
			} catch (MongoException e) {
				log.error("Unable to get mongo db connection");
				e.printStackTrace();
			}
		}
		return mongo;

	}
	
	public static DB getMongoDB(String dbName) {
		return getMongoConnection().getDB(ConstantsUtil.MONGO_DBNAME);
		
	}
	public static DBCollection getMongoDBCollection(String dbName,String collectionName) {
		return getMongoDB(ConstantsUtil.MONGO_DBNAME).getCollection(ConstantsUtil.MONGO_COLLECTION);
		
	}

}
