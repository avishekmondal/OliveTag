package com.olivetag.interfaces;

public interface SignupInterface {

	public void onStartedSignup();

	public void onCompletedSignup(String errorcode, String message, String email,
			String access_token, String name, String gender);

}
