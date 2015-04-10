package com.olivetag.interfaces;

import java.util.ArrayList;
import com.olivetag.bin.ProductLikedCustomerItem;

public interface ProductLikedCustomerInterface {

	public void onStartedProductLikedCustomer();

	public void onCompletedProductLikedCustomer(String errorcode, String message,
			ArrayList<ProductLikedCustomerItem> listofproductlikedcustomer);

}
