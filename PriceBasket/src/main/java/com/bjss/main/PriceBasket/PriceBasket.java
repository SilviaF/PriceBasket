package com.bjss.main.PriceBasket;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class PriceBasket {

	//Constants
	public static final String POUND = "\u00A3";

	//Variables	
	BigDecimal total;
	String[] purchasedItems;
	HashMap<String, Integer> itemsMap = new HashMap<String, Integer>();

	//Constructor
	public PriceBasket(String[] purchasedItems) {
		this.purchasedItems = purchasedItems;
		this.total = BigDecimal.ZERO;

		//Store the repetition of the purchased items in a HashMap for possible offers
		for (int i = 0; i < purchasedItems.length; ++i) {
			String item = purchasedItems[i];

			if (itemsMap.containsKey(item))
				itemsMap.put(item, itemsMap.get(item) + 1);
			else
				itemsMap.put(item, 1);
		}
	}


	public static void main ( String[] args ) {
		String[] purchasedItems = {"Soup", "Milk", "Apples"}; //TODO: delete this, get it from input

		try{
			ProductBean[] storeItems = deserializeItemDetails();
			PriceBasket priceBasket = new PriceBasket(purchasedItems);
			CalculateTotal calculateTotal = new CalculateTotal(storeItems);
			calculateTotal.calculateTotal(priceBasket.itemsMap);

		}catch (IOException e){
			System.err.println(e.getMessage());
		}
	}


	/**
	 * Deserializes the Json file including the items, prices and discounts
	 * @return ItemDetails[] with the deserialized Json
	 * @throws IOException
	 */
	private static ProductBean[] deserializeItemDetails() throws IOException {

		JsonReader reader = new JsonReader(new FileReader("products.json"));
		ProductBean[] items = new Gson().fromJson(reader, ProductBean[].class);
		reader.close();

		return items;
	}

}
