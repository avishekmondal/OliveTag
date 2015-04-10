package com.olivetag.interfaces;

public interface ProductActionInterface {

	public void onStartedProductAction();

	public void onCompletedProductAction(String errorcode, String message);

}
