package com.bjss.main.PriceBasket;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	String POUND = "\u00A3";

	//Variables
	BigDecimal priceBd;
	BigDecimal discountBd;
	BigDecimal totalDiscountBd;
	BigDecimal subtotal;
	BigDecimal total;
	ProductBean[] storeItems;

	//Constructor
	public CalculateTotal(ProductBean[] storeItems){
		this.priceBd = BigDecimal.ZERO;
		this.discountBd = BigDecimal.ZERO;
		this.totalDiscountBd = BigDecimal.ZERO;
		this.subtotal = BigDecimal.ZERO;
		this.total = BigDecimal.ZERO;
		this.storeItems = storeItems;
	}


	public BigDecimal calculateTotal(HashMap<String, Integer> itemsMap){

		hasOffer(storeItems, itemsMap);

		for(Map.Entry<String, Integer> product : itemsMap.entrySet()) {
			String itemName = product.getKey();
			BigDecimal quantity = new BigDecimal(String.valueOf(product.getValue()));

			for (int i=0; i<storeItems.length; i++){
				if (storeItems[i].getItem().equalsIgnoreCase(itemName)){
					discountBd = BigDecimal.ZERO;

					priceBd = new BigDecimal(String.valueOf(storeItems[i].getPrice()));
					priceBd = priceBd.multiply(quantity);

					//Check whether the item has a discount
					discountBd = hasDiscount(storeItems[i]);
					discountBd = discountBd.divide(oneHundred).multiply(quantity); 
					totalDiscountBd = totalDiscountBd.add(discountBd);

					subtotal = subtotal.add(priceBd);
				}
			}
		}

		if (totalDiscountBd.compareTo(BigDecimal.ZERO) == 0){
			System.out.println("(No offers availabe)");
		}

		System.out.println("Subtotal: " + POUND + subtotal.setScale(2, RoundingMode.CEILING));
		total = subtotal.subtract(totalDiscountBd);
		System.out.println("Total: " + POUND + total.setScale(2, RoundingMode.CEILING));

		return total;
	}
	
	
	/**
	 * Calculates discount and sums it up
	 * @param discount
	 * @param quantity
	 * @return
	 */
	public BigDecimal calculateTotalDiscount(BigDecimal discount, BigDecimal quantity){
		discount = discount.divide(oneHundred).multiply(quantity);
		return totalDiscountBd.add(discount);
	}


	/**
	 * Determines whether the input item has an active offer and applies it accordingly
	 * @param purchasedItems
	 * @param storeItems
	 */
	public void hasOffer(ProductBean[] storeItems, HashMap<String, Integer> basketMap){

		for (ProductBean items : storeItems) {
			if (items.getActiveOffer()) { //if there is an active offer for an item
				if (basketMap.get(items.getItem()) >= items.getOfferQuantity()){ //check whether we have the amount of items required in our basket to get the discount
					for (int i=0; i<storeItems.length; i++){
						if (storeItems[i].getItem().equals(items.getOfferDiscountedProduct())){
							storeItems[i].setDiscount(items.getOfferDiscount()); //set the discount for that product accordingly
							System.out.println("Available offer. Buy " + items.getOfferQuantity() + " " + items.getItem() +
									" and get a " + items.getOfferDiscount() + "% discount on " + items.getOfferDiscountedProduct());
							System.out.println("--------------");
						}
					}
				}
			}
		}
	}


	/**
	 * If input item has a discount, applies such discount to the price
	 * and increments the discount, returning the addition of all available discounts
	 * @param basketItem
	 * @return discountBd
	 */
	public BigDecimal hasDiscount(ProductBean basketItem){
		BigDecimal percentage = BigDecimal.ZERO;
		BigDecimal priceBd = new BigDecimal(String.valueOf(basketItem.getPrice()));

		//Get discount value from Json. If discount is 0, no change will take place
		percentage = new BigDecimal(String.valueOf(basketItem.getDiscount()));
		discountBd = discountBd.add((percentage).multiply(priceBd));
		if (discountBd.compareTo(BigDecimal.ZERO) != 0){
			System.out.println(basketItem.getItem() + " " + basketItem.getDiscount() + "% off: -" + 
					discountBd.toBigInteger().toString() + "p");
		}
		return discountBd;
	}


}
