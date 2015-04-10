package com.olivetag.interfaces;

import java.util.ArrayList;
import com.olivetag.bin.ProductDetailsItem;

public interface LikedProductDetailsInterface {

	public void onStartedLikedProductDetails();

	public void onCompletedLikedProductDetails(String errorcode,
			String message,
			ArrayList<ProductDetailsItem> listoflikedproducts);

}
