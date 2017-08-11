package com.bjss.test.PriceBasket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculates the total price to pay for the items in the basket, 
 * including discounts
 * @param purchasedItems String[] with the items purchased
 * @param storeItems ItemDetails[] with the products in the store, their discounts and offers
 * @return Total to pay by the customer
 */
public class CalculateTotal {

	//Constants
	BigDecimal oneHundred = new BigDecimal("100");
	
	//Variables
	BigDecimal total;
	BigDecimal priceBd;
	BigDecimal discountBd;
	BigDecimal subtotal;
	ItemDetails[] storeItems;

	public CalculateTotal(ItemDetails[] storeItems){
		this.total = BigDecimal.ZERO;
		this.priceBd = BigDecimal.ZERO;
		this.discountBd = BigDecimal.ZERO;
		this.subtotal = BigDecimal.ZERO;
		this.storeItems = storeItems;
	}
	
	
	public BigDecimal calculateTotal(HashMap<String, Integer> itemsMap){
				
		
		for(Map.Entry<String, Integer> product : itemsMap.entrySet()) {
		    String itemName = product.getKey();
		    BigDecimal quantity = new BigDecimal(String.valueOf(product.getValue()));
		    
		    for (int i=0; i<storeItems.length; i++){
				if (storeItems[i].item.equalsIgnoreCase(itemName)){
					System.out.println("Item: " + itemName);
					System.out.println("Price: " + storeItems[i].price);
					System.out.println("Quantity: " + quantity);

					priceBd = new BigDecimal(String.valueOf(storeItems[i].price));
					priceBd = priceBd.multiply(quantity);

					//Check whether the item has a discount
					discountBd = hasDiscount(storeItems[i]);
					discountBd = discountBd.multiply(quantity); 
					
					if (discountBd.intValue()==0){
						System.out.println("No offers, suck it");
					}
					
					System.out.println("Discount: " + discountBd);
					
					hasOffer(storeItems[i], itemsMap);
					System.out.println("------");

					subtotal = subtotal.add(priceBd);
					System.out.println("Subtotal: " + subtotal);
					total = subtotal.subtract(discountBd);
					System.out.println("Total: " + total);
				}
			}
		}
		
//		for (String basketItem : purchasedItems){
//			for (int i=0; i<storeItems.length; i++){
//				if (storeItems[i].item.equalsIgnoreCase(basketItem)){
//					System.out.println("Item: " + storeItems[i].item);
//					System.out.println("Price: " + storeItems[i].price);
//
//					priceBd = new BigDecimal(String.valueOf(storeItems[i].price));
//
//					//Check whether the item has a discount
//					discountBd = hasDiscount(storeItems[i]);
//					hasOffer(storeItems[i], itemsMap);
//					System.out.println("------");
//
//					subtotal = subtotal.add(priceBd);
//					System.out.println("Subtotal: " + subtotal);
//					total = subtotal.subtract(discountBd);
//					System.out.println("Total: " + total);
//				}
//			}
			
//		}
		return total;
	}
	
	
	/**
	 * If input item has a discount, applies such discount to the price
	 * and increments the discount, returning the addition of all available discounts
	 * @param basketItem
	 * @return discountBd
	 */
	private BigDecimal hasDiscount(ItemDetails basketItem){
		BigDecimal percentage = BigDecimal.ZERO;
		BigDecimal priceBd = new BigDecimal(String.valueOf(basketItem.price));
		
		//Get discount value from Json. If discount is 0, no change will take place
		percentage = new BigDecimal(String.valueOf(basketItem.discount));
		discountBd = discountBd.add((percentage.divide(oneHundred)).multiply(priceBd));
		
//
//		if (storeItems.discount!=0){
//			discountBd = new BigDecimal(String.valueOf(storeItems.discount));
//			//Calculate the percentage and apply it to the price
//			priceBd = priceBd.subtract((discountBd.divide(oneHundred)).multiply(priceBd));
//			System.out.println("Price after discount: " + priceBd);
//		} else {
//			System.out.println("Price: " + priceBd);
//		}

		return discountBd;
	}


	/**
	 * Determines whether the input item has an active offer and applies it accordingly
	 * @param purchasedItems
	 * @param storeItems
	 */
	private BigDecimal hasOffer(ItemDetails purchasedItems, HashMap<String, Integer> itemsMap){
		
		BigDecimal percentage = BigDecimal.ZERO;
		
		//If there is an active offer
		if (purchasedItems.activeOffer){
			//Get discounts for as many products with offers we have in the basket
			while (purchasedItems.offerQuantity<=itemsMap.get(purchasedItems.item)){
//				System.out.println("before itemsMap.get(purchasedItems.item): " + itemsMap.get(purchasedItems.item));
				//If we have the discounted product in our basket, apply discount
				if(itemsMap.get(purchasedItems.offerDiscountedProduct)!=null){
					//Cast discount percentage into BigDecimal for better processing of currency
					percentage = new BigDecimal(String.valueOf(purchasedItems.offerDiscount));
					//Find the price of that discounted product and calculate the discount
					for (ItemDetails items : storeItems){
						if (items.item.equalsIgnoreCase(purchasedItems.offerDiscountedProduct)){
							discountBd = discountBd.add((percentage.divide(oneHundred)).multiply(items.price));							
						}
					}
					
				}
				//Substract from the sum of an item the number of items required for a discount
				itemsMap.put(purchasedItems.item, itemsMap.get(purchasedItems.item)-purchasedItems.offerQuantity);
//				System.out.println("after itemsMap.get(purchasedItems.item): " + itemsMap.get(purchasedItems.item));
			}
			
			
			System.out.println("There is an active offer for this product");
			System.out.println("Buy " + purchasedItems.offerQuantity + " " + purchasedItems.item + " and get a " + 
					purchasedItems.offerDiscount + "% discount on " + purchasedItems.offerDiscountedProduct + "!!");

		}
		
		return discountBd;
		
	}

}
