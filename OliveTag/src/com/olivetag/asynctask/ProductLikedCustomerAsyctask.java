package com.olivetag.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import com.olivetag.bin.ProductLikedCustomerItem;
import com.olivetag.interfaces.ProductLikedCustomerInterface;
import com.olivetag.networkutil.NetworkUtil;

public class ProductLikedCustomerAsyctask extends
		AsyncTask<String, Void, String> {

	Activity activity;
	public ProductLikedCustomerInterface likedcustomerintf;
	private ProductLikedCustomerItem productlikedcustomeritem;
	private ArrayList<ProductLikedCustomerItem> listofproductlikedcustomer;

	String product_code = "";

	String errorcode = "", message = "";
	InputStream is = null;
	static String json = "";

	public ProductLikedCustomerAsyctask(Activity activity) {

		this.activity = activity;
		listofproductlikedcustomer = new ArrayList<ProductLikedCustomerItem>();

	}

	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub

		String url = NetworkUtil.getlikedcustomerUrl;

		product_code = args[0];

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("productCode", product_code);

			JSONObject getLikedPeople = new JSONObject();
			getLikedPeople.put("getLikedPeople", jsonObject);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("data", getLikedPeople
					.toString()));
			Log.d("url", nameValuePairs.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			is.close();
			json = sb.toString();
			Log.d("json Response", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		return json;

	}

	@Override
	protected void onPostExecute(String jsonStr) {
		// TODO Auto-generated method stub
		super.onPostExecute(jsonStr);

		if (jsonStr != null) {

			try {

				JSONObject jsonObj = new JSONObject(jsonStr);

				JSONObject dataObj = jsonObj.getJSONObject("data");

				errorcode = dataObj.getString("errorCode");

				message = dataObj.getString("errorMsg");

				if (errorcode.equals("0")) {

					JSONArray likedpeopleArray = dataObj
							.getJSONArray("likedPeople");

					for (int i = 0; i < likedpeopleArray.length(); i++) {

						productlikedcustomeritem = new ProductLikedCustomerItem();

						JSONObject data = likedpeopleArray.getJSONObject(i);

						productlikedcustomeritem.setCustomerId(data
								.getString("emailId"));
						productlikedcustomeritem.setCustomerName(data
								.getString("customerName"));

						listofproductlikedcustomer
								.add(productlikedcustomeritem);

					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		Log.v("size", String.valueOf(listofproductlikedcustomer.size()));
		likedcustomerintf.onCompletedProductLikedCustomer(errorcode, message,
				listofproductlikedcustomer);

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		likedcustomerintf.onStartedProductLikedCustomer();

	}

}
