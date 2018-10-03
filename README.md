**Objective**</br>
Business objective of this project is to create the below 2 new APIs:
a)	To retrieve product data 
b)	To update product price


**Overview**</br>
The application is written in java using Spring MVC framework. Logging is implemented using log4j and for storage MongoDB NoSQL data store is used.
```
a)Retrieve product data 
Endpoint : /app/retrieve_product_data/{productid}
Type : GET
ResponseBody:
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "currentPrice": {
        "value": 25.63,
        "currency_code": "GBP"
    }
}
```
The purpose of this API is to return the product details and pricing as a response. The data will be collected from an external API. This information will be stored in MongoDB and later the formattedprice details will be fetched from the Data Store based on the product id and is send as a response of this API call.
This api will retrieve data from external API and store it in the Dat aStore only if the details for the corresponding productid(tcin) is not present in the MongoDB, if data is present it will skip the DB insert and use the existing data from the Data Store.
```
b)Update Product price
EndPoint:/update_product_price
Type: POST
Request body:
{
	"productId":"13860428",
	"price":20.00,
	"currency":"USD"
}
```
The purpose of this API is to update the product price in the DataStore. User can pass the product id, price and currencycode in the request body and this api will update the formattedprice and price values in the listPrice nested tag. The currency code will be converted into currency symbol for saving, and the symbol is converted to the code while retrieving to make the api more user friendly.

**Data Source Details**
All data will be persisted in  MongoDB in json format and json manipulation is done to retrieve and update the records.
DataStore details:
```
dbName : MyRetail
Collection : MyRetailJsonData
Port: localhost
Hostname: 27017
```
Data Store setup instructions:
```
>mongo
>use MyRetail;
>db.createCollection(‘MyRetailJsonData’);
```
Tools used:
```
Robo 3T 1.2 – to view the Datastore details and entries.
Git Bash : for scripting.
```
