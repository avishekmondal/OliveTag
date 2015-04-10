package com.olivetag.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

	private static final String USER_PREFS = "OliveTagApplication";
	private SharedPreferences sharedprefs;
	private SharedPreferences.Editor prefsEditor;

	public SharedPreferenceClass(Context context) {
		this.sharedprefs = context.getSharedPreferences(USER_PREFS,
				Activity.MODE_PRIVATE);
		this.prefsEditor = sharedprefs.edit();
	}

	public void saveLoginflag(String login_flag) {
		prefsEditor.putString("login_flag", login_flag);
		prefsEditor.commit();
	}

	public String getLoginFlag() {
		return sharedprefs.getString("login_flag", "");
	}

	public void saveEmailId(String user_email) {
		prefsEditor.putString("user_email", user_email);
		prefsEditor.commit();
	}

	public String getEmailId() {
		return sharedprefs.getString("user_email", "");
	}

	public void saveAccessToken(String access_token) {
		prefsEditor.putString("access_token", access_token);
		prefsEditor.commit();
	}

	public String getAccessToken() {
		return sharedprefs.getString("access_token", "");
	}

	public void saveName(String name) {
		prefsEditor.putString("name", name);
		prefsEditor.commit();
	}

	public String getName() {
		return sharedprefs.getString("name", "");
	}

	public void saveFirstName(String firstname) {
		prefsEditor.putString("firstname", firstname);
		prefsEditor.commit();
	}

	public int getTotalItem() {
		return sharedprefs.getInt("total_item", 0);
	}

	public void saveTotalItem(int total_item) {
		prefsEditor.putInt("total_item", total_item);
		prefsEditor.commit();
	}

	public int getTotalPrice() {
		return sharedprefs.getInt("total_price", 0);
	}

	public void saveTotalPrice(int total_price) {
		prefsEditor.putInt("total_price", total_price);
		prefsEditor.commit();
	}

	public String getFirstName() {
		return sharedprefs.getString("firstname", "");
	}

	public void saveLastName(String lastname) {
		prefsEditor.putString("lastname", lastname);
		prefsEditor.commit();
	}

	public String getLastName() {
		return sharedprefs.getString("lastname", "");
	}

	public void saveGender(String gender) {
		prefsEditor.putString("gender", gender);
		prefsEditor.commit();
	}

	public String getGender() {
		return sharedprefs.getString("gender", "");
	}

	public void saveCategory(String category) {
		prefsEditor.putString("category", category);
		prefsEditor.commit();
	}

	public String getCategory() {
		return sharedprefs.getString("category", "");
	}

	public void savePhone(String phone) {
		prefsEditor.putString("phone", phone);
		prefsEditor.commit();
	}

	public String getPhone() {
		return sharedprefs.getString("phone", "");
	}

	public void saveDOB(String dob) {
		prefsEditor.putString("dob", dob);
		prefsEditor.commit();
	}

	public String getDOB() {
		return sharedprefs.getString("dob", "");
	}

	public void saveMaritalStatus(String marital_status) {
		prefsEditor.putString("marital_status", marital_status);
		prefsEditor.commit();
	}

	public String getMaritalStatus() {
		return sharedprefs.getString("marital_status", "");
	}

	public void saveState(String state) {
		prefsEditor.putString("state", state);
		prefsEditor.commit();
	}

	public String getState() {
		return sharedprefs.getString("state", "");
	}

	public void saveCity(String city) {
		prefsEditor.putString("city", city);
		prefsEditor.commit();
	}

	public String getCity() {
		return sharedprefs.getString("city", "");
	}

	public void saveLocation(String location) {
		prefsEditor.putString("location", location);
		prefsEditor.commit();
	}

	public String getLocation() {
		return sharedprefs.getString("location", "");
	}

	public void savelandmark(String landmark) {
		prefsEditor.putString("landmark", landmark);
		prefsEditor.commit();
	}

	public String getLandmark() {
		return sharedprefs.getString("landmark", "");
	}

	public void saveStreet(String street) {
		prefsEditor.putString("street", street);
		prefsEditor.commit();
	}

	public String getStreet() {
		return sharedprefs.getString("street", "");
	}

	public void saveFlatNo(String flat_no) {
		prefsEditor.putString("flat_no", flat_no);
		prefsEditor.commit();
	}

	public String getFlatNo() {
		return sharedprefs.getString("flat_no", "");
	}

	public void saveAddress(String address) {
		prefsEditor.putString("address", address);
		prefsEditor.commit();
	}

	public String getAddress() {
		return sharedprefs.getString("address", "");
	}

	public void savePincode(String pincode) {
		prefsEditor.putString("pincode", pincode);
		prefsEditor.commit();
	}

	public String getPincode() {
		return sharedprefs.getString("pincode", "");
	}

	public void saveTotalServicePrice(String total_service_price) {
		prefsEditor.putString("total_service_price", total_service_price);
		prefsEditor.commit();
	}

	public String getTotalServicePrice() {
		return sharedprefs.getString("total_service_price", "");
	}

}
