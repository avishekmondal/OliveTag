package com.olivetag.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olivetag.HomeMainActivity;
import com.olivetag.R;
import com.olivetag.adapter.MediaPagerAdapter;
import com.olivetag.adapter.ProductBoughtCustomerAdapter;
import com.olivetag.adapter.ProductCommentedCustomerAdapter;
import com.olivetag.adapter.ProductLikedCustomerAdapter;
import com.olivetag.asynctask.ProductBoughtCustomerAsyctask;
import com.olivetag.asynctask.ProductCommentedCustomerAsynctask;
import com.olivetag.asynctask.ProductLikedCustomerAsyctask;
import com.olivetag.bin.ProductBoughtCustomerItem;
import com.olivetag.bin.ProductCommentedCustomerItem;
import com.olivetag.bin.ProductDetailsItem;
import com.olivetag.bin.ProductLikedCustomerItem;
import com.olivetag.database.DBAdapter;
import com.olivetag.database.SharedPreferenceClass;
import com.olivetag.interfaces.ProductBoughtCustomerInterface;
import com.olivetag.interfaces.ProductCommentedCustomerInterface;
import com.olivetag.interfaces.ProductLikedCustomerInterface;
import com.olivetag.networkutil.Utils;
import com.viewpagerindicator.CirclePageIndicator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ProductDetailsFragment extends Fragment implements
		ProductLikedCustomerInterface, ProductCommentedCustomerInterface,
		ProductBoughtCustomerInterface {

	View rootView;

	ViewPager pagercontainer;
	CirclePageIndicator indicator;
	LayoutInflater _layoutInflater;
	Vector<View> pages;
	View page1, page2, page3, page4;

	LinearLayout llProductInfo, llActualPrice, llProductAvailability;
	ImageView imgViewPeople, imgViewComment, imgViewCart, imgViewAddCart;
	TextView tvProductName, tvActualPrice, tvOfferPrice, tvProductDescription,
			tvProductMaterial, tvProductCare, tvPlus, tvQuantity;

	ImageView imgProductView1, imgProductView2, imgProductView3,
			imgProductView4;

	View popupViewLike, popupViewComment, popupViewBought;
	PopupWindow popupWindowLike, popupWindowComment, popupWindowBought;

	LinearLayout llCancel, llOk;
	EditText etPincode;

	private ProgressDialog pDialog;
	Dialog alert_dialog, product_availability_dialog;

	static ArrayList<ProductDetailsItem> listofproductitems;
	static int current_product;
	int current_view = 0;
	ArrayList<String> imageList;

	String product_code = "", product_name = "", product_image = "",
			product_actual_price = "", product_offer_price = "",
			product_price = "", product_brand = "", product_availability = "",
			product_description = "", product_care = "", product_material = "",
			product_delivery_time = "", product_like = "",
			product_comment = "", product_size = "", product_quantity = "1";

	int quantity = 1;

	ProductLikedCustomerAdapter productlikedcustomeradapter;
	ProductCommentedCustomerAdapter productcommentedcustomeradapter;
	ProductBoughtCustomerAdapter productboughtcustomeradapter;

	SharedPreferenceClass sharedpreference;
	ImageLoader imageLoader;

	DBAdapter db;
	long id;
	boolean remove;

	@SuppressWarnings("unchecked")
	public static ProductDetailsFragment newInstance(Bundle bundleToPass) {
		// TODO Auto-generated method stub

		ProductDetailsFragment fragment = new ProductDetailsFragment();
		listofproductitems = (ArrayList<ProductDetailsItem>) bundleToPass
				.getSerializable("listofproductitems");
		fragment.setArguments(bundleToPass);
		current_product = (int) bundleToPass.getInt("current_product");

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		rootView = inflater.inflate(R.layout.fragment_product_details,
				container, false);

		initialize();

		setvalue();

		onclick();

		return rootView;
	}

	/*
	 * @Override public void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); listofproductitems.clear(); }
	 * 
	 * @Override public void onStop() { // TODO Auto-generated method stub
	 * super.onStop(); listofproductitems.clear(); }
	 * 
	 * @Override public void onDestroy() { // TODO Auto-generated method stub
	 * 
	 * super.onDestroy(); listofproductitems.clear(); }
	 */

	private void initialize() {
		// TODO Auto-generated method stub

		sharedpreference = new SharedPreferenceClass(getActivity());
		imageLoader = ImageLoader.getInstance();

		pagercontainer = (ViewPager) rootView.findViewById(R.id.pagercontainer);
		indicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);

		pages = new Vector<View>();
		_layoutInflater = getActivity().getLayoutInflater();

		page1 = _layoutInflater.inflate(R.layout.product_details_one, null);
		page2 = _layoutInflater.inflate(R.layout.product_details_two, null);
		page3 = _layoutInflater.inflate(R.layout.product_details_three, null);
		page4 = _layoutInflater.inflate(R.layout.product_details_four, null);

		llProductInfo = (LinearLayout) rootView
				.findViewById(R.id.llProductInfo);
		llActualPrice = (LinearLayout) rootView
				.findViewById(R.id.llActualPrice);
		llProductAvailability = (LinearLayout) rootView
				.findViewById(R.id.llProductAvailability);
		imgViewPeople = (ImageView) rootView.findViewById(R.id.imgViewPeople);
		imgViewComment = (ImageView) rootView.findViewById(R.id.imgViewComment);
		imgViewCart = (ImageView) rootView.findViewById(R.id.imgViewCart);
		imgViewAddCart = (ImageView) rootView.findViewById(R.id.imgViewAddCart);
		tvProductName = (TextView) rootView.findViewById(R.id.tvProductName);
		tvActualPrice = (TextView) rootView.findViewById(R.id.tvActualPrice);
		tvOfferPrice = (TextView) rootView.findViewById(R.id.tvOfferPrice);
		tvProductDescription = (TextView) rootView
				.findViewById(R.id.tvProductDescription);
		tvProductMaterial = (TextView) rootView
				.findViewById(R.id.tvProductMaterial);
		tvProductCare = (TextView) rootView.findViewById(R.id.tvProductCare);
		tvPlus = (TextView) rootView.findViewById(R.id.tvPlus);
		tvQuantity = (TextView) rootView.findViewById(R.id.tvQuantity);

		imgProductView1 = (ImageView) page1.findViewById(R.id.imgProductView1);
		imgProductView2 = (ImageView) page2.findViewById(R.id.imgProductView2);
		imgProductView3 = (ImageView) page3.findViewById(R.id.imgProductView3);
		imgProductView4 = (ImageView) page4.findViewById(R.id.imgProductView4);

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

		// --------------------- Popup View --------------------------

		LayoutInflater layoutinflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		popupViewLike = layoutinflater.inflate(R.layout.popup_liked_customer,
				null);
		popupWindowLike = new PopupWindow(popupViewLike,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindowLike.setBackgroundDrawable(new BitmapDrawable(
				getResources(), ""));
		popupWindowLike.setOutsideTouchable(true);
		popupWindowLike.setFocusable(true);

		popupViewComment = layoutinflater.inflate(
				R.layout.popup_commented_customer, null);
		popupWindowComment = new PopupWindow(popupViewComment,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindowComment.setBackgroundDrawable(new BitmapDrawable(
				getResources(), ""));
		popupWindowComment.setOutsideTouchable(true);
		popupWindowComment.setFocusable(true);

		popupViewBought = layoutinflater.inflate(
				R.layout.popup_bought_customer, null);
		popupWindowBought = new PopupWindow(popupViewBought,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindowBought.setBackgroundDrawable(new BitmapDrawable(
				getResources(), ""));
		popupWindowBought.setOutsideTouchable(true);
		popupWindowBought.setFocusable(true);

	}

	private void setvalue() {
		// TODO Auto-generated method stub

		product_code = listofproductitems.get(current_product).getProductCode();
		product_name = listofproductitems.get(current_product).getProductName();
		product_actual_price = String.valueOf(listofproductitems.get(
				current_product).getActualPrice());
		product_offer_price = String.valueOf(listofproductitems.get(
				current_product).getOfferPrice());
		product_brand = listofproductitems.get(current_product).getBrand();
		product_availability = listofproductitems.get(current_product)
				.getAvailability();
		product_description = listofproductitems.get(current_product)
				.getDescription();
		product_care = listofproductitems.get(current_product).getCare();
		product_material = listofproductitems.get(current_product)
				.getMaterial();
		product_delivery_time = listofproductitems.get(current_product)
				.getDeliveryTime();
		product_like = listofproductitems.get(current_product)
				.getNumberOfLikes();
		product_comment = listofproductitems.get(current_product)
				.getNumberOfComments();

		ArrayList<String> imageList = new ArrayList<String>();
		imageList = listofproductitems.get(current_product).getImagelist();

		if (imageList.size() == 1) {
			pages.add(page1);
		}

		else if (imageList.size() == 2) {
			pages.add(page1);
			pages.add(page2);
		}

		else if (imageList.size() == 3) {
			pages.add(page1);
			pages.add(page2);
			pages.add(page3);
		}

		else {
			pages.add(page1);
			pages.add(page2);
			pages.add(page3);
			pages.add(page4);

		}

		MediaPagerAdapter adapter = new MediaPagerAdapter(getActivity(), pages);
		pagercontainer.setAdapter(adapter);

		indicator.setViewPager(pagercontainer);
		indicator.setClickable(true);
		indicator.setSnap(true);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.showImageOnLoading(R.drawable.home_icon)
				.showImageForEmptyUri(R.drawable.home_icon)
				.showImageOnFail(R.drawable.home_icon).build();

		product_image = imageList.get(0).toString();

		if (imageList.size() == 1) {
			imageLoader.displayImage(imageList.get(0).toString(),
					imgProductView1, options);
		}

		else if (imageList.size() == 2) {
			imageLoader.displayImage(imageList.get(0).toString(),
					imgProductView1, options);
			imageLoader.displayImage(imageList.get(1).toString(),
					imgProductView2, options);
		}

		else if (imageList.size() == 3) {
			imageLoader.displayImage(imageList.get(0).toString(),
					imgProductView1, options);
			imageLoader.displayImage(imageList.get(1).toString(),
					imgProductView2, options);
			imageLoader.displayImage(imageList.get(2).toString(),
					imgProductView3, options);

		}

		else {
			imageLoader.displayImage(imageList.get(0).toString(),
					imgProductView1, options);
			imageLoader.displayImage(imageList.get(1).toString(),
					imgProductView2, options);
			imageLoader.displayImage(imageList.get(2).toString(),
					imgProductView3, options);
			imageLoader.displayImage(imageList.get(3).toString(),
					imgProductView4, options);

		}

		tvProductName.setText(product_name);
		tvProductDescription.setText(product_description);
		tvProductMaterial.setText(product_material);
		tvProductCare.setText(product_care);

		if (!product_offer_price.equals("null")) {

			tvActualPrice.setText("Rs. "
					+ product_actual_price.replace(".0000", ""));
			tvOfferPrice.setText("Rs. "
					+ product_offer_price.replace(".0000", ""));

			llActualPrice.setVisibility(View.VISIBLE);

			product_price = product_offer_price.replace(".0000", "");

		}

		else {

			tvActualPrice.setText("Rs. "
					+ product_actual_price.replace(".0000", ""));
			tvOfferPrice.setText("No offer price");

			product_price = product_actual_price.replace(".0000", "");
		}

	}

	private void onclick() {
		// TODO Auto-generated method stub

		imgProductView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				current_view = 0;

				((HomeMainActivity) getActivity()).loadProductZoomFragment(
						listofproductitems, current_product, current_view);

			}
		});

		imgProductView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				current_view = 1;

				((HomeMainActivity) getActivity()).loadProductZoomFragment(
						listofproductitems, current_product, current_view);

			}
		});

		imgProductView3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				current_view = 2;

				((HomeMainActivity) getActivity()).loadProductZoomFragment(
						listofproductitems, current_product, current_view);

			}
		});

		imgProductView4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				current_view = 3;

				((HomeMainActivity) getActivity()).loadProductZoomFragment(
						listofproductitems, current_product, current_view);

			}
		});

		imgViewPeople.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utils.checkConnectivity(getActivity())) {

					ProductLikedCustomerAsyctask get_liked_customer = new ProductLikedCustomerAsyctask(
							getActivity());
					get_liked_customer.likedcustomerintf = ProductDetailsFragment.this;
					get_liked_customer.execute(listofproductitems.get(
							current_product).getProductCode());

				}

				else {

					showNetworkDialog("internet");
				}

			}
		});

		imgViewComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utils.checkConnectivity(getActivity())) {

					ProductCommentedCustomerAsynctask get_commented_customer = new ProductCommentedCustomerAsynctask(
							getActivity());
					get_commented_customer.commentedcustomerintf = ProductDetailsFragment.this;
					get_commented_customer.execute(listofproductitems.get(
							current_product).getProductCode());

				}

				else {

					showNetworkDialog("internet");
				}

			}
		});

		imgViewCart.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = sdf.format(new Date());

				if (Utils.checkConnectivity(getActivity())) {

					ProductBoughtCustomerAsyctask get_bought_customer = new ProductBoughtCustomerAsyctask(
							getActivity());
					get_bought_customer.boughtcustomerintf = ProductDetailsFragment.this;
					get_bought_customer.execute(
							listofproductitems.get(current_product)
									.getProductCode(), currentDate);

				}

				else {

					showNetworkDialog("internet");
				}

			}
		});

		tvPlus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				quantity++;
				product_quantity = String.valueOf(quantity);
				tvQuantity.setText(product_quantity);

			}
		});

		imgViewAddCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				db = new DBAdapter(getActivity());
				db.open();

				id = db.insertValue(product_code, product_name, product_image,
						product_price, product_brand, product_availability,
						product_description, product_care, product_material,
						product_delivery_time, product_like, product_comment,
						product_size, product_quantity);

				db.close();

				((HomeMainActivity) getActivity()).incrementCart();
				((HomeMainActivity) getActivity()).loadHomeFragment();

			}
		});

		llProductAvailability.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				product_availability_dialog = new Dialog(getActivity());
				product_availability_dialog
						.requestWindowFeature(Window.FEATURE_NO_TITLE);
				product_availability_dialog.getWindow().setGravity(
						Gravity.CENTER);
				product_availability_dialog
						.setContentView(R.layout.dialog_product_availability);
				product_availability_dialog.show();

				etPincode = (EditText) product_availability_dialog
						.findViewById(R.id.etPincode);
				llCancel = (LinearLayout) product_availability_dialog
						.findViewById(R.id.llCancel);
				llOk = (LinearLayout) product_availability_dialog
						.findViewById(R.id.llOk);

				llCancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						product_availability_dialog.dismiss();

					}
				});

				llOk.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						product_availability_dialog.dismiss();

					}
				});

			}
		});

	}

	@Override
	public void onStartedProductLikedCustomer() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onCompletedProductLikedCustomer(String errorcode,
			String message,
			ArrayList<ProductLikedCustomerItem> listofproductlikedcustomer) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		if (listofproductlikedcustomer.size() > 0) {

			ListView listLikedCustomer = (ListView) popupViewLike
					.findViewById(R.id.listLikedCustomer);

			productlikedcustomeradapter = new ProductLikedCustomerAdapter(
					getActivity(), listofproductlikedcustomer);
			listLikedCustomer.setAdapter(productlikedcustomeradapter);

			if (popupWindowLike.isShowing()) {
				popupWindowLike.dismiss();
			}

			else {

				popupWindowLike.setAnimationStyle(R.style.PopupWindowAnimation);
				popupWindowLike.showAsDropDown(llProductInfo);

			}
		}

		else {

			alert_dialog = new Dialog(getActivity());
			alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alert_dialog.setContentView(R.layout.dialog_alert);
			alert_dialog.show();

			TextView alert_msg = (TextView) alert_dialog
					.findViewById(R.id.alert_msg);
			Button alert_ok = (Button) alert_dialog.findViewById(R.id.alert_ok);

			alert_msg.setText("No People Like this product yet");

			alert_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					alert_dialog.dismiss();

				}
			});
		}

	}

	@Override
	public void onStartedProductBoughtCustomer() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onStartedProductCommentedCustomer() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onCompletedProductCommentedCustomer(
			String errorcode,
			String message,
			ArrayList<ProductCommentedCustomerItem> listofproductcommentedcustomer) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		if (listofproductcommentedcustomer.size() > 0) {

			ListView listCommentedCustomer = (ListView) popupViewComment
					.findViewById(R.id.listCommentedCustomer);

			productcommentedcustomeradapter = new ProductCommentedCustomerAdapter(
					getActivity(), listofproductcommentedcustomer);
			listCommentedCustomer.setAdapter(productcommentedcustomeradapter);

			if (popupWindowComment.isShowing()) {
				popupWindowComment.dismiss();
			}

			else {

				popupWindowComment
						.setAnimationStyle(R.style.PopupWindowAnimation);
				popupWindowComment.showAsDropDown(llProductInfo);

			}
		}

		else {

			alert_dialog = new Dialog(getActivity());
			alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alert_dialog.setContentView(R.layout.dialog_alert);
			alert_dialog.show();

			TextView alert_msg = (TextView) alert_dialog
					.findViewById(R.id.alert_msg);
			Button alert_ok = (Button) alert_dialog.findViewById(R.id.alert_ok);

			alert_msg.setText("No People Commented this product yet");

			alert_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					alert_dialog.dismiss();

				}
			});
		}

	}

	@Override
	public void onCompletedProductBoughtCustomer(String errorcode,
			String message,
			ArrayList<ProductBoughtCustomerItem> listofproductboughtcustomer) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		if (listofproductboughtcustomer.size() > 0) {

			ListView listBoughtCustomer = (ListView) popupViewBought
					.findViewById(R.id.listBoughtCustomer);

			productboughtcustomeradapter = new ProductBoughtCustomerAdapter(
					getActivity(), listofproductboughtcustomer);
			listBoughtCustomer.setAdapter(productboughtcustomeradapter);

			if (popupWindowBought.isShowing()) {
				popupWindowBought.dismiss();
			}

			else {

				popupWindowBought
						.setAnimationStyle(R.style.PopupWindowAnimation);
				popupWindowBought.showAsDropDown(llProductInfo);

			}
		}

		else {

			alert_dialog = new Dialog(getActivity());
			alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alert_dialog.setContentView(R.layout.dialog_alert);
			alert_dialog.show();

			TextView alert_msg = (TextView) alert_dialog
					.findViewById(R.id.alert_msg);
			Button alert_ok = (Button) alert_dialog.findViewById(R.id.alert_ok);

			alert_msg.setText("No People Bought this product yet");

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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
