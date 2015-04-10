package com.olivetag.interfaces;

import java.util.ArrayList;
import com.olivetag.bin.ProductDetailsItem;

public interface ProductDetailsInterface {

	public void onStartedProductDetails();

	public void onCompletedProductDetails(String errorcode, String message,
			ArrayList<ProductDetailsItem> listofproducts);

}
