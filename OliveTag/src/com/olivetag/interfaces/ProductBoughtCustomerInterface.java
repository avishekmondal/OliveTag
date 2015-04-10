package com.olivetag.interfaces;

import java.util.ArrayList;
import com.olivetag.bin.ProductBoughtCustomerItem;

public interface ProductBoughtCustomerInterface {

	public void onStartedProductBoughtCustomer();

	public void onCompletedProductBoughtCustomer(String errorcode,
			String message,
			ArrayList<ProductBoughtCustomerItem> listofproductboughtcustomer);

}
