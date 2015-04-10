package com.olivetag.interfaces;

import java.util.ArrayList;
import com.olivetag.bin.ProductCommentedCustomerItem;

public interface ProductCommentedCustomerInterface {

	public void onStartedProductCommentedCustomer();

	public void onCompletedProductCommentedCustomer(
			String errorcode,
			String message,
			ArrayList<ProductCommentedCustomerItem> listofproductcommentedcustomer);

}
