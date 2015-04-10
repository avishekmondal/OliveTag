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
import com.olivetag.interfaces.ProductDetailsInterface;
import com.olivetag.networkutil.NetworkUtil;

public class ProductDetailsAsynctask extends AsyncTask<String, Void, String> {

	Activity activity;
	public ProductDetailsInterface productdetailsintf;
	private ProductDetailsItem productitem;
	private ArrayList<ProductDetailsItem> listofproducts;

	String email_id = "", access_token = "", categoty_id = "",
			subcategory_id = "";

	String errorcode = "", message = "";
	InputStream is = null;
	static String json = "";

	public ProductDetailsAsynctask(Activity activity) {

		this.activity = activity;
		listofproducts = new ArrayList<ProductDetailsItem>();

	}

	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub

		String url = NetworkUtil.getproductdetailsUrl;

		email_id = args[0];
		access_token = args[1];
		categoty_id = args[2];
		subcategory_id = args[3];

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("emailId", email_id);
			jsonObject.put("accessToken", access_token);
			jsonObject.put("categoryId", categoty_id);
			jsonObject.put("subCategoryId", subcategory_id);

			JSONObject getProductDetails = new JSONObject();
			getProductDetails.put("getProductDetails", jsonObject);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("data", getProductDetails
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

				errorcode = dataObj.getString("errorcode");

				message = dataObj.getString("massage");

				if (errorcode.equals("0")) {

					JSONArray productDetailsArray = dataObj
							.getJSONArray("productDetails");

					for (int i = 0; i < productDetailsArray.length(); i++) {

						productitem = new ProductDetailsItem();

						JSONObject data = productDetailsArray.getJSONObject(i);

						productitem.setProductCode(data
								.getString("productCode"));
						productitem.setProductName(data
								.getString("productName"));
						productitem.setBrand(data.getString("brand"));
						productitem.setAvailability(data
								.getString("availability"));
						productitem.setActualPrice(data
								.getString("actualPrice"));
						productitem.setOfferPrice(data.getString("offerPrice"));
						productitem.setDescription(data
								.getString("description"));
						productitem.setCare(data.getString("care"));
						productitem.setMaterial(data.getString("material"));
						productitem.setDeliveryTime(data
								.getString("deliveryTime"));
						productitem.setNumberOfLikes(data
								.getString("numberOfLikes"));
						productitem.setNumberOfComments(data
								.getString("numberOfComments"));
						productitem.setProductLiked(data
								.getString("productLiked"));

						JSONArray productImgListArray = data
								.getJSONArray("productImgList");

						ArrayList<String> imageList = new ArrayList<String>();

						for (int j = 0; j < productImgListArray.length(); j++) {

							String imgPath = productImgListArray.getString(j).replaceAll(" ", "%20");

							imageList.add(imgPath);

						}

						productitem.setImagelist(imageList);

						listofproducts.add(productitem);

					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		Log.v("size", String.valueOf(listofproducts.size()));
		productdetailsintf.onCompletedProductDetails(errorcode, message,
				listofproducts);

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		productdetailsintf.onStartedProductDetails();

	}
}