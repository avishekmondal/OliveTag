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

import com.olivetag.bin.ProductDetailsItem;
import com.olivetag.interfaces.LikedProductDetailsInterface;
import com.olivetag.networkutil.NetworkUtil;

public class LikedProductDetailsAsynctask extends
		AsyncTask<String, Void, String> {

	Activity activity;
	public LikedProductDetailsInterface likedproductdetailsintf;
	private ProductDetailsItem likedproductitem;
	private ArrayList<ProductDetailsItem> listoflikedproducts;

	String email_id = "", access_token = "", categoty_id = "",
			subcategory_id = "";

	String errorcode = "", message = "";
	InputStream is = null;
	static String json = "";

	public LikedProductDetailsAsynctask(Activity activity) {

		this.activity = activity;
		listoflikedproducts = new ArrayList<ProductDetailsItem>();

	}

	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub

		String url = NetworkUtil.getlikedproductdetailsUrl;

		email_id = args[0];
		access_token = args[1];

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("emailId", email_id);
			jsonObject.put("accessToken", access_token);

			JSONObject LikedProductDetails = new JSONObject();
			LikedProductDetails.put("LikedProductDetails", jsonObject);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("data",
					LikedProductDetails.toString()));
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

				errorcode = dataObj.getString("errorcode");

				message = dataObj.getString("massage");

				if (errorcode.equals("0")) {

					JSONArray productDetailsArray = dataObj
							.getJSONArray("likedProductDetails");

					for (int i = 0; i < productDetailsArray.length(); i++) {

						likedproductitem = new ProductDetailsItem();

						JSONObject data = productDetailsArray.getJSONObject(i);

						likedproductitem.setProductCode(data
								.getString("productCode"));
						likedproductitem.setProductName(data
								.getString("productName"));
						likedproductitem.setBrand(data.getString("brand"));
						likedproductitem.setAvailability(data
								.getString("availability"));
						likedproductitem.setActualPrice(data
								.getString("actualPrice"));
						likedproductitem.setOfferPrice(data
								.getString("offerPrice"));
						likedproductitem.setDescription(data
								.getString("description"));
						likedproductitem.setCare(data.getString("care"));
						likedproductitem
								.setMaterial(data.getString("material"));
						likedproductitem.setDeliveryTime(data
								.getString("deliveryTime"));
						likedproductitem.setNumberOfLikes(data
								.getString("numberOfLikes"));
						likedproductitem.setNumberOfComments(data
								.getString("numberOfComments"));

						JSONArray productImgListArray = data
								.getJSONArray("productImgList");

						ArrayList<String> imageList = new ArrayList<String>();

						for (int j = 0; j < productImgListArray.length(); j++) {

							String imgPath = productImgListArray.getString(j)
									.replaceAll(" ", "%20");

							imageList.add(imgPath);

						}

						likedproductitem.setImagelist(imageList);

						listoflikedproducts.add(likedproductitem);

					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		Log.v("size", String.valueOf(listoflikedproducts.size()));
		likedproductdetailsintf.onCompletedLikedProductDetails(errorcode,
				message, listoflikedproducts);

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		likedproductdetailsintf.onStartedLikedProductDetails();

	}
}
