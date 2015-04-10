package com.olivetag;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.olivetag.adapter.NavDrawerListAdapter;
import com.olivetag.bin.CartItem;
import com.olivetag.bin.NavDrawerItem;
import com.olivetag.bin.ProductDetailsItem;
import com.olivetag.classes.BadgeView;
import com.olivetag.database.DBAdapter;
import com.olivetag.database.SharedPreferenceClass;
import com.olivetag.fragment.CartFragment;
import com.olivetag.fragment.CheckoutFragment;
import com.olivetag.fragment.HomeFragment;
import com.olivetag.fragment.LikeListFragment;
import com.olivetag.fragment.ProductDetailsFragment;
import com.olivetag.fragment.ProductZoomFragment;
import com.olivetag.fragment.ProfileFragment;
import com.olivetag.fragment.SettingsFragment;

public class HomeMainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	ImageView imgViewSearch, imgViewHome, imgViewSettings;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	Menu badge_menu;
	MenuItem badge_item_notificaction, badge_item_cart;
	int badge_item_id_notificaction, badge_item_id_cart;
	View target_notification, target_cart;
	BadgeView badge_notification, badge_cart;
	int count_notification = 0, count_cart = 0;

	View popupView1;
	PopupWindow popupWindow1;

	DBAdapter db;
	long id;
	boolean remove;

	ArrayList<CartItem> CartList;

	SharedPreferenceClass sharedpreference;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedpreference = new SharedPreferenceClass(HomeMainActivity.this);

		LayoutInflater layoutinflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		imgViewSearch = (ImageView) findViewById(R.id.imgViewSearch);
		imgViewHome = (ImageView) findViewById(R.id.imgViewHome);
		imgViewSettings = (ImageView) findViewById(R.id.imgViewSettings);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array

		navDrawerItems.add(new NavDrawerItem());

		navDrawerItems.add(new NavDrawerItem(R.drawable.women_back));

		navDrawerItems.add(new NavDrawerItem(R.drawable.men_back));

		navDrawerItems.add(new NavDrawerItem());

		navDrawerItems.add(new NavDrawerItem());

		navDrawerItems.add(new NavDrawerItem(R.drawable.help_back));

		navDrawerItems.add(new NavDrawerItem(R.drawable.wallet_back));

		navDrawerItems.add(new NavDrawerItem());

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#aaa8a9")));
		getActionBar().setTitle("");

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.menu_icon, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				// invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				// invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item

			loadHomeFragment();
		}

		// ///////////////////////////////////////////////////////////////////////////

		db = new DBAdapter(HomeMainActivity.this);
		db.open();

		CartList = db.getRecords();
		count_cart = CartList.size();

		db.close();

		// ///////////////////////////////////////////////////////////////////////////

		popupView1 = layoutinflater.inflate(R.layout.fragment_notification,
				null);
		popupWindow1 = new PopupWindow(popupView1,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow1.setBackgroundDrawable(new BitmapDrawable(getResources(),
				""));
		popupWindow1.setOutsideTouchable(true);
		popupWindow1.setFocusable(true);

		// ///////////////////////////////////////////////////////////////////////////

		imgViewSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				loadHomeFragment();

			}
		});

		imgViewHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				loadHomeFragment();

			}
		});

		imgViewSettings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				loadSettingsFragment();

			}
		});

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		badge_menu = menu;

		badge_item_notificaction = menu.findItem(R.id.action_notification);
		badge_item_id_notificaction = badge_item_notificaction.getItemId();

		badge_item_cart = menu.findItem(R.id.action_cart);
		badge_item_id_cart = badge_item_cart.getItemId();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				target_notification = findViewById(badge_item_id_notificaction);
				badge_notification = new BadgeView(HomeMainActivity.this,
						target_notification);
				badge_notification.setText("2");
				badge_notification.setTextColor(Color.parseColor("#ffffff"));
				badge_notification.show();

				target_cart = findViewById(badge_item_id_cart);
				badge_cart = new BadgeView(HomeMainActivity.this, target_cart);
				badge_cart.setText(String.valueOf(count_cart));
				badge_cart.setTextColor(Color.parseColor("#ffffff"));
				badge_cart.show();

			}
		}, 1000);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {

		case R.id.action_like:

			loadLikeListFragment();
			return true;

		case R.id.action_notification:

			View view = findViewById(R.id.action_notification);
			count_notification++;
			badge_notification.setVisibility(View.GONE);

			// --------------------- Popup View --------------------------

			if (popupWindow1.isShowing()) {
				popupWindow1.dismiss();
			}

			else {

				popupWindow1.showAsDropDown(view);

			}
			return true;

		case R.id.action_cart:

			loadCartFragment();
			return true;

		default:

			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		switch (position) {
		case 0:
			loadHomeFragment();
			break;
		case 1:
			sharedpreference.saveCategory("207");
			loadHomeFragment();
			break;
		case 2:
			sharedpreference.saveCategory("244");
			loadHomeFragment();
			break;
		case 3:
			loadProfileFragment();
			break;
		case 4:
			loadHomeFragment();
			break;
		case 5:
			loadHomeFragment();
			break;
		case 6:
			loadHomeFragment();
			break;
		case 7:
			sharedpreference.saveLoginflag("");
			sharedpreference.saveEmailId("");
			sharedpreference.saveAccessToken("");
			sharedpreference.saveName("");
			sharedpreference.saveGender("");

			startActivity(new Intent(HomeMainActivity.this,
					LandingActivity.class));
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static long back_pressed;

	/*
	 * public void onBackPressed() { super.onBackPressed(); if (back_pressed +
	 * 3000 > System.currentTimeMillis()) {
	 * android.os.Process.killProcess(android.os.Process.myPid()); finish();
	 * 
	 * } else { Toast.makeText(getBaseContext(), "Press once again to exit!",
	 * 1000) .show(); back_pressed = System.currentTimeMillis();
	 * 
	 * // loadHomeFragment();
	 * 
	 * } }
	 */

	public void loadHomeFragment() {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new HomeFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadProductDetailsFragment(
			ArrayList<ProductDetailsItem> listofproductitems,
			int current_product) {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new ProductDetailsFragment();
		FragmentManager fragmentManager = getFragmentManager();

		Bundle bundle = new Bundle();
		bundle.putSerializable("listofproductitems", listofproductitems);
		bundle.putInt("current_product", current_product);
		ProductDetailsFragment productDetails = ProductDetailsFragment
				.newInstance(bundle);

		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, productDetails).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadProductZoomFragment(
			ArrayList<ProductDetailsItem> listofproductitems,
			int current_product, int current_view) {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new ProductZoomFragment();
		FragmentManager fragmentManager = getFragmentManager();

		Bundle bundle = new Bundle();
		bundle.putSerializable("listofproductitems", listofproductitems);
		bundle.putInt("current_product", current_product);
		bundle.putInt("current_view", current_view);
		ProductZoomFragment productzoom = ProductZoomFragment
				.newInstance(bundle);

		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, productzoom).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadProfileFragment() {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new ProfileFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	private void loadLikeListFragment() {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new LikeListFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadCartFragment() {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new CartFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadCheckoutFragment() {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new CheckoutFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadSettingsFragment() {
		// TODO Auto-generated method stub

		Fragment fragment = null;
		fragment = new SettingsFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void loadNotification() {
		// TODO Auto-generated method stub

		if (badge_notification.isShown()) {
			badge_notification.increment(1);
		}

	}

	public void incrementCart() {
		// TODO Auto-generated method stub

		if (badge_cart.isShown()) {
			badge_cart.increment(1);
			count_cart++;
		}

	}

	public void decrementCart() {
		// TODO Auto-generated method stub

		if (badge_cart.isShown()) {
			badge_cart.decrement(1);
			count_cart--;
		}

	}

}
