package com.olivetag.fragment;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olivetag.HomeMainActivity;
import com.olivetag.R;
import com.olivetag.asynctask.ProductActionAsynctask;
import com.olivetag.asynctask.ProductDetailsAsynctask;
import com.olivetag.bin.ProductActionItem;
import com.olivetag.bin.ProductDetailsItem;
import com.olivetag.classes.Constant;
import com.olivetag.database.SharedPreferenceClass;
import com.olivetag.interfaces.ProductActionInterface;
import com.olivetag.interfaces.ProductDetailsInterface;

public class HomeFragment extends Fragment implements ProductDetailsInterface,
		ProductActionInterface {

	View rootView;

	RelativeLayout parentView;
	int windowwidth, windowheight;
	int screenCenter;
	int x_cord, y_cord, x, y;
	float alphaValue = 0;
	int Likes = 0;
	int count = 0;

	LinearLayout llOption;
	ImageView imgViewDislike, imgViewPeople, imgViewInfo, imgViewComment,
			imgViewLike;

	View popupViewInfo, popupViewLike, popupViewComment;
	PopupWindow popupWindowInfo, popupWindowLike, popupWindowComment;

	String email_id = "", access_token = "", categoty_id = "",
			subcategory_id = "";
	int total_product = 0, current_product = 0;

	private ProgressDialog pDialog;
	Dialog alert_dialog, product_info_dialog;

	ArrayList<ProductDetailsItem> listofproductitems;

	SharedPreferenceClass sharedpreference;
	ImageLoader imageLoader;

	ArrayList<ProductActionItem> productActionList;
	ProductActionItem actionItem;

	private Constant _constant;

	@SuppressWarnings("static-access")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		initialize();

		onclick();

		email_id = sharedpreference.getEmailId();
		access_token = sharedpreference.getAccessToken();
		categoty_id = sharedpreference.getCategory();
		Log.v("categoty_id", categoty_id);

		if (_constant.productActionList.size() > 0) {

			saveProductActionFirst();

		}

		else {

			ProductDetailsAsynctask get_product_details = new ProductDetailsAsynctask(
					getActivity());
			get_product_details.productdetailsintf = HomeFragment.this;
			get_product_details.execute(email_id, access_token, categoty_id,
					subcategory_id);

		}

		return rootView;

	}

	private void initialize() {
		// TODO Auto-generated method stub

		sharedpreference = new SharedPreferenceClass(getActivity());
		_constant = new Constant();
		imageLoader = ImageLoader.getInstance();

		listofproductitems = new ArrayList<ProductDetailsItem>();
		productActionList = new ArrayList<ProductActionItem>();

		llOption = (LinearLayout) rootView.findViewById(R.id.llOption);
		imgViewDislike = (ImageView) rootView.findViewById(R.id.imgViewDislike);
		imgViewPeople = (ImageView) rootView.findViewById(R.id.imgViewPeople);
		imgViewInfo = (ImageView) rootView.findViewById(R.id.imgViewInfo);
		imgViewComment = (ImageView) rootView.findViewById(R.id.imgViewComment);
		imgViewLike = (ImageView) rootView.findViewById(R.id.imgViewLike);

		parentView = (RelativeLayout) rootView.findViewById(R.id.layoutView);
		windowwidth = getActivity().getWindowManager().getDefaultDisplay()
				.getWidth();
		windowheight = getActivity().getWindowManager().getDefaultDisplay()
				.getHeight();
		screenCenter = windowwidth / 2;

		// --------------------- Popup View --------------------------

		LayoutInflater layoutinflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		popupViewInfo = layoutinflater.inflate(R.layout.popup_product_info,
				null);
		popupWindowInfo = new PopupWindow(popupViewInfo,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindowInfo.setBackgroundDrawable(new BitmapDrawable(
				getResources(), ""));
		popupWindowInfo.setOutsideTouchable(true);
		popupWindowInfo.setFocusable(true);

		popupViewLike = layoutinflater.inflate(R.layout.popup_like, null);
		popupWindowLike = new PopupWindow(popupViewLike,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindowLike.setBackgroundDrawable(new BitmapDrawable(
				getResources(), ""));
		popupWindowLike.setOutsideTouchable(true);
		popupWindowLike.setFocusable(true);

		popupViewComment = layoutinflater.inflate(R.layout.popup_comment, null);
		popupWindowComment = new PopupWindow(popupViewComment,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindowComment.setBackgroundDrawable(new BitmapDrawable(
				getResources(), ""));
		popupWindowComment.setOutsideTouchable(true);
		popupWindowComment.setFocusable(true);

	}

	private void onclick() {
		// TODO Auto-generated method stub

		imgViewInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (current_product < total_product) {

					if (popupWindowInfo.isShowing()) {
						popupWindowInfo.dismiss();
					}

					else {

						popupWindowInfo
								.setAnimationStyle(R.style.PopupWindowAnimation);
						popupWindowInfo.showAsDropDown(llOption);

					}

					TextView tvProductName = (TextView) popupViewInfo
							.findViewById(R.id.tvProductName);
					TextView tvActualPrice = (TextView) popupViewInfo
							.findViewById(R.id.tvActualPrice);
					TextView tvOfferPrice = (TextView) popupViewInfo
							.findViewById(R.id.tvOfferPrice);
					LinearLayout llActualPrice = (LinearLayout) popupViewInfo
							.findViewById(R.id.llActualPrice);
					TextView tvSeeMore = (TextView) popupViewInfo
							.findViewById(R.id.tvSeeMore);

					tvProductName.setText(listofproductitems.get(
							current_product).getProductName());

					if (!listofproductitems.get(current_product)
							.getOfferPrice().equals("null")) {

						tvActualPrice.setText("Rs. "
								+ String.valueOf(listofproductitems
										.get(current_product).getActualPrice()
										.replace(".0000", "")));
						tvOfferPrice.setText("Rs. "
								+ listofproductitems.get(current_product)
										.getOfferPrice().replace(".0000", ""));

						llActualPrice.setVisibility(View.VISIBLE);
						tvOfferPrice.setVisibility(View.VISIBLE);

					}

					else {

						tvActualPrice.setText("Rs. "
								+ String.valueOf(listofproductitems
										.get(current_product).getActualPrice()
										.replace(".0000", "")));
					}

					tvSeeMore.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							popupWindowInfo.dismiss();

							((HomeMainActivity) getActivity())
									.loadProductDetailsFragment(
											listofproductitems, current_product);

						}
					});

				}

				else {

					alert_dialog = new Dialog(getActivity());
					alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					alert_dialog.setContentView(R.layout.dialog_alert);
					alert_dialog.show();

					TextView alert_msg = (TextView) alert_dialog
							.findViewById(R.id.alert_msg);
					Button alert_ok = (Button) alert_dialog
							.findViewById(R.id.alert_ok);

					alert_msg.setText("Oops!! No items to see details");

					alert_ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) { // TODO Auto-generated
														// method stub

							alert_dialog.dismiss();

						}
					});
				}

			}

		});

		imgViewPeople.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (current_product < total_product) {

					if (popupWindowLike.isShowing()) {
						popupWindowLike.dismiss();
					}

					else {

						popupWindowLike.showAsDropDown(imgViewPeople);

					}

					TextView tvNumberOfLikes = (TextView) popupViewLike
							.findViewById(R.id.tvNumberOfLikes);

					tvNumberOfLikes.setText(listofproductitems.get(
							current_product).getNumberOfLikes()
							+ " people like this");

				}

				else {

					alert_dialog = new Dialog(getActivity());
					alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					alert_dialog.setContentView(R.layout.dialog_alert);
					alert_dialog.show();

					TextView alert_msg = (TextView) alert_dialog
							.findViewById(R.id.alert_msg);
					Button alert_ok = (Button) alert_dialog
							.findViewById(R.id.alert_ok);

					alert_msg.setText("Oops!! No items to see details");

					alert_ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) { // TODO Auto-generated
														// method stub

							alert_dialog.dismiss();

						}
					});
				}

			}
		});

		imgViewComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (current_product < total_product) {

					if (popupWindowComment.isShowing()) {
						popupWindowComment.dismiss();
					}

					else {

						popupWindowComment.showAsDropDown(imgViewComment);

					}

					TextView tvNumberOfComments = (TextView) popupViewComment
							.findViewById(R.id.tvNumberOfComments);

					tvNumberOfComments.setText(listofproductitems.get(
							current_product).getNumberOfComments()
							+ " people comment this");

				}

				else {

					alert_dialog = new Dialog(getActivity());
					alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					alert_dialog.setContentView(R.layout.dialog_alert);
					alert_dialog.show();

					TextView alert_msg = (TextView) alert_dialog
							.findViewById(R.id.alert_msg);
					Button alert_ok = (Button) alert_dialog
							.findViewById(R.id.alert_ok);

					alert_msg.setText("Oops!! No items to see details");

					alert_ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) { // TODO Auto-generated
														// method stub

							alert_dialog.dismiss();

						}
					});
				}

			}
		});

	}

	@Override
	public void onStartedProductDetails() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onCompletedProductDetails(String errorcode, String message,
			ArrayList<ProductDetailsItem> listofproducts) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		listofproductitems = listofproducts;
		total_product = listofproductitems.size();

		ArrayList<String> imageList = new ArrayList<String>();

		for (int i = listofproductitems.size() - 1; i >= 0; i--) {

			imageList = listofproductitems.get(i).getImagelist();

			LayoutInflater inflate = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final View m_view = inflate.inflate(R.layout.custom_layout, null);
			ImageView m_image = (ImageView) m_view.findViewById(R.id.sp_image);
			LinearLayout m_topLayout = (LinearLayout) m_view
					.findViewById(R.id.sp_color);

			// width = (3 * windowwidth) / 4;
			// height = windowheight / 2;
			m_view.setX(0);
			m_view.setY(0);

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.showImageOnLoading(R.drawable.defaultimage)
					.showImageForEmptyUri(R.drawable.defaultimage)
					.showImageOnFail(R.drawable.defaultimage).build();

			imageLoader.displayImage(imageList.get(0).toString(), m_image,
					options);

			// ADD dynamically like button on image.
			final Button imageLike = new Button(getActivity());
			imageLike.setLayoutParams(new LayoutParams(150, 100));
			imageLike.setBackgroundDrawable(getActivity().getResources()
					.getDrawable(R.drawable.like_btn));
			imageLike.setX(50);
			imageLike.setY(-(windowheight / 2));
			imageLike.setAlpha(alphaValue);
			m_topLayout.addView(imageLike);

			// ADD dynamically dislike button on image.
			final Button imageDislike = new Button(getActivity());
			imageDislike.setLayoutParams(new LayoutParams(150, 100));
			imageDislike.setBackgroundDrawable(getActivity().getResources()
					.getDrawable(R.drawable.dislike_btn));

			imageDislike.setX(((getActivity().getWindowManager()
					.getDefaultDisplay().getWidth()) / 2) + 50);
			imageDislike.setY(-(windowheight / 2) - 100);
			imageDislike.setAlpha(alphaValue);
			m_topLayout.addView(imageDislike);

			m_topLayout.setOnTouchListener(new OnTouchListener() {

				@SuppressWarnings("static-access")
				@SuppressLint({ "ClickableViewAccessibility", "ShowToast" })
				@Override
				public boolean onTouch(View v, MotionEvent event) {

					if (popupWindowInfo.isShowing()) {
						popupWindowInfo.dismiss();
					}

					x_cord = (int) event.getRawX();
					y_cord = (int) event.getRawY();

					m_view.setX(0);
					m_view.setY(0);

					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						x = (int) event.getX();
						y = (int) event.getY();

						m_view.setX(0);
						m_view.setY(0);
						break;

					case MotionEvent.ACTION_MOVE:
						x_cord = (int) event.getRawX(); // Updated for more
														// smoother animation.
						y_cord = (int) event.getRawY();
						m_view.setX(x_cord - x);
						m_view.setY(y_cord - y);
						if (x_cord >= screenCenter) {
							m_view.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
							if (x_cord > (screenCenter + (screenCenter / 2))) {
								imageLike.setAlpha(1);
								if (x_cord > (windowwidth - (screenCenter / 4))) {
									Likes = 2;
								} else {
									Likes = 0;
								}
							} else {
								Likes = 0;
								imageLike.setAlpha(0);
							}
							imageDislike.setAlpha(0);
						} else {
							// rotate
							m_view.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
							if (x_cord < (screenCenter / 2)) {
								imageDislike.setAlpha(1);
								if (x_cord < screenCenter / 4) {
									Likes = 1;
								} else {
									Likes = 0;
								}
							} else {
								Likes = 0;
								imageDislike.setAlpha(0);
							}
							imageLike.setAlpha(0);
						}

						break;

					case MotionEvent.ACTION_UP:
						x_cord = (int) event.getRawX();
						y_cord = (int) event.getRawY();

						imageDislike.setAlpha(0);
						imageLike.setAlpha(0);

						if (Likes == 0) {

							m_view.setX(0);
							m_view.setY(0);
							m_view.setRotation(0);

							// ((HomeMainActivity) getActivity())
							// .loadProductDetailsFragment(
							// listofproductitems, current_product);

						} else if (Likes == 1) {

							parentView.removeView(m_view);

							actionItem = new ProductActionItem();
							actionItem.setProductId(listofproductitems.get(
									current_product).getProductCode());
							actionItem.setProductLiked("0");
							productActionList.add(actionItem);

							_constant.productActionList = productActionList;
							// _constant.productActionList.addAll(productActionList);

							current_product++;

						} else if (Likes == 2) {

							parentView.removeView(m_view);

							actionItem = new ProductActionItem();
							actionItem.setProductId(listofproductitems.get(
									current_product).getProductCode());
							actionItem.setProductLiked("1");
							productActionList.add(actionItem);

							_constant.productActionList = productActionList;
							// _constant.productActionList.addAll(productActionList);

							current_product++;

						}
						break;

					default:
						break;
					}
					return true;
				}
			});

			parentView.addView(m_view);

		}

	}

	@SuppressWarnings("static-access")
	private void saveProductActionFirst() {
		// TODO Auto-generated method stub

		try {

			JSONArray productList = new JSONArray();

			for (int i = 0; i < _constant.productActionList.size(); i++) {

				JSONObject productjsonObject = new JSONObject();

				productjsonObject.put("productCode",
						_constant.productActionList.get(i).getProductId());

				productjsonObject.put("productLiked",
						_constant.productActionList.get(i).getProductLiked());

				productList.put(productjsonObject);

			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("productList", productList);
			jsonObject.put("emailId", sharedpreference.getEmailId());
			jsonObject.put("accessToken", sharedpreference.getAccessToken());

			JSONObject saveLike = new JSONObject();
			saveLike.put("saveLike", jsonObject);

			ProductActionAsynctask product_action = new ProductActionAsynctask(
					getActivity());
			product_action.productactionintf = HomeFragment.this;
			product_action.execute(saveLike.toString());

			_constant.productActionList.clear();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onStartedProductAction() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onCompletedProductAction(String errorcode, String message) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		ProductDetailsAsynctask get_product_details = new ProductDetailsAsynctask(
				getActivity());
		get_product_details.productdetailsintf = HomeFragment.this;
		get_product_details.execute(email_id, access_token, categoty_id,
				subcategory_id);

	}

}
