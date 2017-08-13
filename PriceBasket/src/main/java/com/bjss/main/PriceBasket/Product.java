package com.bjss.main.PriceBasket;
import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

	private String item;
	private BigDecimal price;
	private int discount;
	private Boolean activeOffer;
	private int offerQuantity;
	private String offerDiscountedProduct;
	private int offerDiscount;

	public Product(){
		this.item = "";
		this.price = BigDecimal.ZERO;
		this.discount = 0;
		this.activeOffer = false;
		this.offerQuantity = 0;
		this.offerDiscountedProduct = "";
		this.offerDiscount = 0;
	}


	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public Boolean getActiveOffer() {
		return activeOffer;
	}

	public void setActiveOffer(Boolean activeOffer) {
		this.activeOffer = activeOffer;
	}

	public int getOfferQuantity() {
		return offerQuantity;
	}

	public void setOfferQuantity(int offerQuantity) {
		this.offerQuantity = offerQuantity;
	}
	public String getOfferDiscountedProduct() {
		return offerDiscountedProduct;
	}

	public void setOfferDiscountedProduct(String offerDiscountedProduct) {
		this.offerDiscountedProduct = offerDiscountedProduct;
	}

	public int getOfferDiscount() {
		return offerDiscount;
	}

	public void setOfferDiscount(int offerDiscount) {
		this.offerDiscount = offerDiscount;
	}

}
