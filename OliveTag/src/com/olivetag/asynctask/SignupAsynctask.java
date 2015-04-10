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
import org.json.JSONException;
import org.json.JSONObject;
import com.olivetag.interfaces.SignupInterface;
import com.olivetag.networkutil.NetworkUtil;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SignupAsynctask extends AsyncTask<String, Void, String> {

	Activity activity;
	public SignupInterface signupintf;

	String emailid = "", profileid = "", firstname = "", lastname = "",
			dob = "", gender = "", current_latitude = "",
			current_longitude = "", deviceid = "";

	String errorcode = "", message = "", user_email = "",
			user_access_token = "", user_name = "", user_gender = "";
	InputStream is = null;
	static String json = "";

	public SignupAsynctask(Activity activity) {
		this.activity = activity;

	}

	@Override
	protected String doInBackground(String... args) {
		// TODO Auto-generated method stub

		String url = NetworkUtil.signupUrl;

		emailid = args[0];
		profileid = args[1];
		firstname = args[2];
		lastname = args[3];
		dob = args[4];
		gender = args[5];
		current_latitude = args[6];
		current_longitude = args[7];
		deviceid = args[8];

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("emailId", emailid);
			jsonObject.put("profileId", profileid);
			jsonObject.put("fName", firstname);
			jsonObject.put("lName", lastname);
			jsonObject.put("dob", dob);
			jsonObject.put("gender", gender);
			jsonObject.put("latValue", current_latitude);
			jsonObject.put("longVal", current_longitude);
			jsonObject.put("deviceId", deviceid);
			jsonObject.put("deviceType", "android");

			JSONObject fbsignUp = new JSONObject();
			fbsignUp.put("fbsignUp", jsonObject);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("data", fbsignUp
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

					JSONObject signupObj = dataObj.getJSONObject("signup");

					user_email = signupObj.getString("emailId");

					user_access_token = signupObj.getString("accessToken");

					user_name = signupObj.getString("userName");
					
					user_gender = signupObj.getString("userGender");

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		signupintf.onCompletedSignup(errorcode, message, user_email,
				user_access_token, user_name, user_gender);

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		signupintf.onStartedSignup();

	}
}
