package com.bjss.test.PriceBasket;

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
		
		System.out.println("ItemsMap: " + itemsMap.toString());
	}


	public static void main ( String[] args ) {
		String[] purchasedItems = {"Apples", "Milk", "Bread", "Apples", "Apples"}; //TODO: delete this, get it from input

		try{
			ItemDetails[] storeItems = deserializeItemDetails();
			PriceBasket priceBasket = new PriceBasket(purchasedItems);
			CalculateTotal calculateTotal = new CalculateTotal(storeItems);
			System.out.println("Subtotal: " + POUND + calculateTotal.calculateTotal(priceBasket.itemsMap));

		}catch (IOException e){
			System.out.println(e.getMessage());
		}

	} 


	/**
	 * Deserializes the Json file including the items, prices and discounts
	 * @return ItemDetails[] with the deserialized Json
	 * @throws IOException
	 */
	private static ItemDetails[] deserializeItemDetails() throws IOException {

		JsonReader reader = new JsonReader(new FileReader("products.json"));
		ItemDetails[] items = new Gson().fromJson(reader, ItemDetails[].class);
		reader.close();

		return items;
	}


	/**
	 * Calculates the total price to pay for the items in the basket, 
	 * including discounts
	 * @param purchasedItems String[] with the items purchased
	 * @param storeItems ItemDetails[] with the products in the store, their discounts and offers
	 * @return Total to pay by the customer
	 */
//	private static BigDecimal calculateTotal(String[] purchasedItems, ItemDetails[] storeItems, HashMap<String, Integer> itemsMap){
//		int discount = 0;
//		BigDecimal total = BigDecimal.ZERO;
//		BigDecimal priceBd = BigDecimal.ZERO;
//
//		for (String basketItem : purchasedItems){
//			for (int i=0; i<storeItems.length; i++){
//				if (storeItems[i].item.equals(basketItem)){
//					System.out.println("Item: " + storeItems[i].item);
//
//					hasOffer(storeItems[i], storeItems);
//					discount = storeItems[i].discount;
//					priceBd = new BigDecimal(String.valueOf(storeItems[i].price));
//
//					//Check whether the item has a discount
//					priceBd = hasDiscount(storeItems[i]);
//
//					total = total.add(priceBd);
//				}
//			}
//		}
//
//		return total;
//	}
//	
//
//	/**
//	 * If the input item has a discount, applies such discount to the price,
//	 * otherwise returns the full price
//	 * @param discount
//	 * @param priceBd
//	 * @return price
//	 */
//	private static BigDecimal hasDiscount(ItemDetails storeItems){
//		BigDecimal discountBd = BigDecimal.ZERO;
//		BigDecimal priceBd = new BigDecimal(String.valueOf(storeItems.price));
//		BigDecimal oneHundred = new BigDecimal("100");		
//
//		if (storeItems.discount!=0){
//			discountBd = new BigDecimal(String.valueOf(storeItems.discount));
//			//Calculate the percentage and apply it to the price
//			priceBd = priceBd.subtract((discountBd.divide(oneHundred)).multiply(priceBd));
//			System.out.println("Price after discount: " + priceBd);
//		} else {
//			System.out.println("Price: " + priceBd);
//		}
//
//		return priceBd;
//	}
//
//
//	/**
//	 * Determines whether the input item has an active offer and applies it accordingly
//	 * @param purchasedItems
//	 * @param storeItems
//	 */
//	private static void hasOffer(ItemDetails purchasedItems, ItemDetails[] storeItems){
//		if (purchasedItems.activeOffer){
//			System.out.println("There is an active offer for this product");
//			System.out.println("Buy " + purchasedItems.offerQuantity + " " + purchasedItems.item + " and get a " + 
//					purchasedItems.offerDiscount + "% discount on " + purchasedItems.offerDiscountedProduct + "!!");
//
//		}
//	}

}
