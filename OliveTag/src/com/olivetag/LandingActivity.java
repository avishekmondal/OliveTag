package com.olivetag;

import java.util.Vector;
import com.facebook.AppEventsLogger;
import com.olivetag.adapter.MediaPagerAdapter;
import com.olivetag.asynctask.SignupAsynctask;
import com.olivetag.database.SharedPreferenceClass;
import com.olivetag.interfaces.SignupInterface;
import com.olivetag.networkutil.GPSTracker;
import com.olivetag.networkutil.Utils;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.Permission.Type;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.viewpagerindicator.CirclePageIndicator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LandingActivity extends Activity implements SignupInterface {

	ViewPager pagercontainer;
	CirclePageIndicator indicator;
	LayoutInflater _layoutInflater;

	ImageView fbLogin;
	TextView tv_skip;

	private ProgressDialog pDialog;
	Dialog alert_dialog;

	GPSTracker gpsTracker;
	double current_lat, current_long;
	String current_latitude = "", current_longitude = "";

	String emailid = "", profileid = "", firstname = "", lastname = "",
			dob = "", gender = "", deviceid = "";

	SharedPreferenceClass sharedpreference;

	public SimpleFacebook simpleFB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.landing_layout);

		initialize();

		current_lat = gpsTracker.getLatitude();
		current_long = gpsTracker.getLongitude();

		current_latitude = String.valueOf(current_lat);
		current_longitude = String.valueOf(current_long);

		onclick();

	}

	private void initialize() {
		// TODO Auto-generated method stub

		sharedpreference = new SharedPreferenceClass(LandingActivity.this);
		gpsTracker = new GPSTracker(LandingActivity.this);

		pagercontainer = (ViewPager) findViewById(R.id.pagercontainer);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		fbLogin = (ImageView) findViewById(R.id.btnFacebookLogin);
		tv_skip = (TextView) findViewById(R.id.tv_skip);

		Vector<View> pages = new Vector<View>();
		_layoutInflater = getLayoutInflater();

		View page1 = _layoutInflater.inflate(R.layout.page_one, null);
		View page2 = _layoutInflater.inflate(R.layout.page_two, null);
		View page3 = _layoutInflater.inflate(R.layout.page_three, null);
		View page4 = _layoutInflater.inflate(R.layout.page_four, null);

		pages.add(page1);
		pages.add(page2);
		pages.add(page3);
		pages.add(page4);

		MediaPagerAdapter adapter = new MediaPagerAdapter(this, pages);
		pagercontainer.setAdapter(adapter);

		indicator.setViewPager(pagercontainer);
		indicator.setClickable(true);
		indicator.setSnap(true);

		pagercontainer.setPageTransformer(false,
				new ViewPager.PageTransformer() {
					@SuppressLint("NewApi")
					@Override
					public void transformPage(View page, float position) {
						// do transformation here

						final float normalizedposition = Math.abs(Math
								.abs(position) - 1);
						page.setAlpha(normalizedposition);
						// page.setScaleX(normalizedposition / 2 + 0.5f);
						// page.setScaleY(normalizedposition / 2 + 0.5f);

					}
				});

	}

	private void onclick() {
		// TODO Auto-generated method stub

		fbLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				simpleFB.login(onLoginListener);

			}
		});

		tv_skip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sharedpreference.saveCategory("207");

				startActivity(new Intent(LandingActivity.this,
						HomeMainActivity.class));

			}
		});

	}

	@Override
	protected void onResume() {
		AppEventsLogger.activateApp(this);
		simpleFB = SimpleFacebook.getInstance(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		simpleFB.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);

	}

	OnLoginListener onLoginListener = new OnLoginListener() {

		@Override
		public void onFail(String reason) {
			// TODO Auto-generated method stub

			Toast.makeText(LandingActivity.this, "Facebook Error",
					Toast.LENGTH_LONG).show();

		}

		@Override
		public void onException(Throwable throwable) {
			// TODO Auto-generated method stub

			Toast.makeText(LandingActivity.this, "Facebook Error",
					Toast.LENGTH_LONG).show();

		}

		@Override
		public void onThinking() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNotAcceptingPermissions(Type type) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLogin() {
			// TODO Auto-generated method stub
			simpleFB.getProfile(new OnProfileListener() {

				public void onFail(String reason) {

					Toast.makeText(LandingActivity.this, "Facebook Error",
							Toast.LENGTH_LONG).show();

				}

				public void onException(Throwable throwable) {

				}

				public void onThinking() {

				}

				public void onComplete(
						com.sromku.simple.fb.entities.Profile response) {

					try {

						emailid = response.getEmail();
						profileid = response.getId();
						firstname = response.getFirstName();
						lastname = response.getLastName();
						gender = response.getGender();

						// http://graph.facebook.com/752876431457749/picture?type=large

						if (Utils.checkConnectivity(LandingActivity.this)) {

							SignupAsynctask get_signup = new SignupAsynctask(
									LandingActivity.this);
							get_signup.signupintf = LandingActivity.this;
							get_signup.execute(emailid, profileid, firstname,
									lastname, dob, gender, current_latitude,
									current_longitude, deviceid);

						}

						else {

							showNetworkDialog("internet");
						}

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				}
			});
		}
	};

	@Override
	public void onStartedSignup() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(LandingActivity.this);
		pDialog.setMessage("Please Wait...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onCompletedSignup(String errorcode, String message,
			String email, String access_token, String name, String gender) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		if (errorcode.equals("0")) {

			sharedpreference.saveLoginflag("1");
			sharedpreference.saveEmailId(email);
			sharedpreference.saveAccessToken(access_token);
			sharedpreference.saveName(name);
			sharedpreference.saveGender(gender);

			if (gender.equalsIgnoreCase("male")) {

				sharedpreference.saveCategory("244");
			}

			else {

				sharedpreference.saveCategory("207");
			}

			startActivity(new Intent(LandingActivity.this,
					HomeMainActivity.class));
			finish();

		}

		else {

			alert_dialog = new Dialog(LandingActivity.this);
			alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alert_dialog.setContentView(R.layout.dialog_alert);
			alert_dialog.show();

			TextView alert_msg = (TextView) alert_dialog
					.findViewById(R.id.alert_msg);
			Button alert_ok = (Button) alert_dialog.findViewById(R.id.alert_ok);

			alert_msg.setText(message);

			alert_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					alert_dialog.dismiss();

				}
			});
		}

	}

	@SuppressWarnings("deprecation")
	public void showNetworkDialog(final String message) {
		// final exit of application
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LandingActivity.this);
		builder.setTitle(getResources().getString(R.string.app_name));
		if (message.equals("gps")) {
			builder.setMessage(getResources().getString(R.string.gps_message));
		} else if (message.equals("internet")) {
			builder.setMessage(getResources().getString(R.string.net_message));
		}
		AlertDialog alert = builder.create();
		alert.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (message.equals("gps")) {
					Intent i = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(i, 1);
				} else if (message.equals("internet")) {
					Intent i = new Intent(
							android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					startActivityForResult(i, 2);
					Intent i1 = new Intent(
							android.provider.Settings.ACTION_WIFI_SETTINGS);
					startActivityForResult(i1, 3);
				} else {
					// do nothing
				}
			}
		});
		// Showing Alert Message
		alert.show();
	}

}
