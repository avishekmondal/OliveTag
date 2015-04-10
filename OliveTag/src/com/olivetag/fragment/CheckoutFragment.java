package com.olivetag.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olivetag.R;
import com.olivetag.database.SharedPreferenceClass;

public class CheckoutFragment extends Fragment {

	View rootView;

	TextView tvTotalItem, tvTotalPrice, tvshippingPrice, tvDiscountPrice,
			tvTaxPrice, tvOrderTotalPrice, tvAmontPayable;

	int total_item = 0, total_price = 0, shipping_price = 0,
			discount_price = 0, tax_price = 0, order_total_price = 0;

	SharedPreferenceClass sharedpreference;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_checkout, container,
				false);

		initialize();

		onclick();

		loadCheckoutDetails();

		return rootView;
	}

	private void initialize() {
		// TODO Auto-generated method stub

		sharedpreference = new SharedPreferenceClass(getActivity());

		tvTotalItem = (TextView) rootView.findViewById(R.id.tvTotalItem);
		tvTotalPrice = (TextView) rootView.findViewById(R.id.tvTotalPrice);
		tvshippingPrice = (TextView) rootView
				.findViewById(R.id.tvshippingPrice);
		tvDiscountPrice = (TextView) rootView
				.findViewById(R.id.tvDiscountPrice);
		tvTaxPrice = (TextView) rootView.findViewById(R.id.tvTaxPrice);
		tvOrderTotalPrice = (TextView) rootView
				.findViewById(R.id.tvOrderTotalPrice);
		tvAmontPayable = (TextView) rootView.findViewById(R.id.tvAmontPayable);

	}

	private void onclick() {
		// TODO Auto-generated method stub

	}

	private void loadCheckoutDetails() {
		// TODO Auto-generated method stub

		total_item = sharedpreference.getTotalItem();

		if (total_item > 0) {

			total_price = sharedpreference.getTotalPrice();
			shipping_price = 70;
			order_total_price = total_price + shipping_price;

			tvTotalItem.setText("Items (" + String.valueOf(total_item) + "):");
			tvTotalPrice.setText(" Rs. " + String.valueOf(total_price) + ".00");
			tvshippingPrice.setText(" Rs. " + String.valueOf(shipping_price)
					+ ".00");
			tvOrderTotalPrice.setText(" Rs. "
					+ String.valueOf(order_total_price) + ".00");
			tvAmontPayable.setText(" Rs. " + String.valueOf(order_total_price)
					+ ".00");

		}

	}
}
