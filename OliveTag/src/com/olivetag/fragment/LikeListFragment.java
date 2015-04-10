package com.olivetag.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.olivetag.HomeMainActivity;
import com.olivetag.R;
import com.olivetag.adapter.LikedProductListAdapter;
import com.olivetag.asynctask.LikedProductDetailsAsynctask;
import com.olivetag.asynctask.ProductActionAsynctask;
import com.olivetag.bin.ProductDetailsItem;
import com.olivetag.classes.Constant;
import com.olivetag.database.SharedPreferenceClass;
import com.olivetag.interfaces.LikedProductDetailsInterface;
import com.olivetag.interfaces.ProductActionInterface;

public class LikeListFragment extends Fragment implements
		ProductActionInterface, LikedProductDetailsInterface {

	View rootView;

	ListView listLikedProduct;

	private ProgressDialog pDialog;
	Dialog alert_dialog;

	ArrayList<ProductDetailsItem> listofproductitems;
	LikedProductListAdapter adapter;

	SharedPreferenceClass sharedpreference;
	private Constant _constant;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_like, container, false);

		initialize();

		onclick();

		if (Constant.productActionList.size() > 0) {

			saveProductActionFirst();

		}

		else {

			LikedProductDetailsAsynctask get_liked_product_details = new LikedProductDetailsAsynctask(
					getActivity());
			get_liked_product_details.likedproductdetailsintf = LikeListFragment.this;
			get_liked_product_details.execute(sharedpreference.getEmailId(),
					sharedpreference.getAccessToken());

		}

		return rootView;
	}

	private void initialize() {
		// TODO Auto-generated method stub

		sharedpreference = new SharedPreferenceClass(getActivity());
		_constant = new Constant();

		listofproductitems = new ArrayList<ProductDetailsItem>();

		listLikedProduct = (ListView) rootView
				.findViewById(R.id.listLikedProduct);

	}

	private void onclick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartedLikedProductDetails() {
		// TODO Auto-generated method stub

		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please Wait...");
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();

	}

	@Override
	public void onCompletedLikedProductDetails(String errorcode,
			String message,
			final ArrayList<ProductDetailsItem> listoflikedproducts) {
		// TODO Auto-generated method stub

		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}

		if (listoflikedproducts.size() > 0) {

			adapter = new LikedProductListAdapter(getActivity(),
					listoflikedproducts);
			listLikedProduct.setAdapter(adapter);

			listLikedProduct.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) { // TODO Auto-generated method
													// stub

					((HomeMainActivity) getActivity())
							.loadProductDetailsFragment(listoflikedproducts,
									position);

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
			product_action.productactionintf = LikeListFragment.this;
			product_action.execute(saveLike.toString());

			_constant.productActionList.clear();

			Log.d("productActionList size",
					String.valueOf(_constant.productActionList.size()));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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

		LikedProductDetailsAsynctask get_liked_product_details = new LikedProductDetailsAsynctask(
				getActivity());
		get_liked_product_details.likedproductdetailsintf = LikeListFragment.this;
		get_liked_product_details.execute(sharedpreference.getEmailId(),
				sharedpreference.getAccessToken());

	}

}