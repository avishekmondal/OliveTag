package com.olivetag.bin;

public class ProductBoughtCustomerItem {

	private String customer_id = "";
	private String customer_name = "";
	private String customer_img = "";
	private String bought_before = "";

	public String getCustomerId() {
		return customer_id;
	}

	public void setCustomerId(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomerName() {
		return customer_name;
	}

	public void setCustomerName(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomerImg() {
		return customer_img;
	}

	public void setgetCustomerImg(String customer_img) {
		this.customer_img = customer_img;
	}

	public String getBoughtBefore() {
		return bought_before;
	}

	public void setBoughtBefore(String bought_before) {
		this.bought_before = bought_before;
	}

}
