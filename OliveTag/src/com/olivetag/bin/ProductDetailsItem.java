package com.olivetag.bin;

import java.util.ArrayList;

public class ProductDetailsItem {

	private String product_code = "";
	private String product_name = "";
	private String brand = "";
	private String availability = "";
	private String actual_price = "";
	private String offer_price = "";
	private String description = "";
	private String care = "";
	private String material = "";
	private String delivery_time = "";
	private String number_likes = "";
	private String number_comments = "";
	private String product_liked = "";
	private ArrayList<String> imagelist;

	public String getProductCode() {
		return product_code;
	}

	public void setProductCode(String product_code) {
		this.product_code = product_code;
	}

	public String getProductName() {
		return product_name;
	}

	public void setProductName(String product_name) {
		this.product_name = product_name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getActualPrice() {
		return actual_price;
	}

	public void setActualPrice(String actual_price) {
		this.actual_price = actual_price;
	}

	public String getOfferPrice() {
		return offer_price;
	}

	public void setOfferPrice(String offer_price) {
		this.offer_price = offer_price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCare() {
		return care;
	}

	public void setCare(String care) {
		this.care = care;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDeliveryTime() {
		return delivery_time;
	}

	public void setDeliveryTime(String delivery_time) {
		this.delivery_time = delivery_time;
	}

	public String getNumberOfLikes() {
		return number_likes;
	}

	public void setNumberOfLikes(String number_likes) {
		this.number_likes = number_likes;
	}

	public String getNumberOfComments() {
		return number_comments;
	}

	public void setNumberOfComments(String number_comments) {
		this.number_comments = number_comments;
	}

	public String getProductLiked() {
		return product_liked;
	}

	public void setProductLiked(String product_liked) {
		this.product_liked = product_liked;
	}

	public ArrayList<String> getImagelist() {
		return imagelist;
	}

	public void setImagelist(ArrayList<String> imagelist) {
		this.imagelist = imagelist;
	}

}
