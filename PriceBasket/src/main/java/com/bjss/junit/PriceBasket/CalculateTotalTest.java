package com.bjss.junit.PriceBasket;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import org.junit.Test;

import com.bjss.main.PriceBasket.CalculateTotal;
import com.bjss.main.PriceBasket.ProductBean;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class CalculateTotalTest {

	ProductBean[] storeItems;
	CalculateTotal calculateTest;

	public CalculateTotalTest() {
		try{
			ProductBean[] storeItems = deserializeItemDetails();	
			calculateTest = new CalculateTotal(storeItems);
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}


	/**
	 * Test item with no discount
	 */
	@Test
	public void noDiscountTest(){
		try{
			//Soup Discount
			assertEquals(0, calculateTest.hasDiscount(storeItems[0]).intValue());
		} catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Test item with discount
	 */
	@Test
	public void discountTest(){
		try{
			//Apples Discount
			assertEquals(10, calculateTest.hasDiscount(storeItems[3]).intValue());			
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Test item with offer
	 */
	@Test
	public void hasOfferTest(){
		try{
			HashMap basketMap = new HashMap();
			basketMap.put(storeItems[0], 2); //Soup has discount on bread (items[1]) if >2
			
			calculateTest.hasOffer(storeItems, basketMap);				
			assertEquals(50, storeItems[1].getDiscount());
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Test item with no offer
	 */
	@Test
	public void hasNoOfferTest(){
		try{
			HashMap basketMap = new HashMap();
			basketMap.put(storeItems[2], 2); //Milk has no discount on bread (items[1])
			
			calculateTest.hasOffer(storeItems, basketMap);				
			assertEquals(0, storeItems[1].getDiscount());
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}


	@Test
	public void calculateDiscountTest(){
		//Example of 10% discount for two items
		BigDecimal discount = BigDecimal.TEN;
		BigDecimal quantity = new BigDecimal(2);
		BigDecimal totalDiscount = BigDecimal.ZERO;
		CalculateTotal calculateTest = new CalculateTotal(storeItems);

		totalDiscount = calculateTest.calculateTotalDiscount(discount, quantity);

		assertEquals(new BigDecimal(0.20).setScale(2, RoundingMode.FLOOR), 
				totalDiscount.setScale(2, RoundingMode.CEILING));

	}


	/**
	 * Method from PriceBasket class that deserializes the input Json file
	 * @return Product[] items
	 * @throws IOException
	 */
	private static ProductBean[] deserializeItemDetails() throws IOException {
		JsonReader reader = new JsonReader(new FileReader("products.json"));
		ProductBean[] items = new Gson().fromJson(reader, ProductBean[].class);
		reader.close();

		return items;
	}

}
