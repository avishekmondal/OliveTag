package com.olivetag.adapter;

import java.util.ArrayList;
import com.olivetag.R;
import com.olivetag.bin.ProductLikedCustomerItem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductLikedCustomerAdapter extends
		ArrayAdapter<ProductLikedCustomerItem> {

	private LayoutInflater inflater;
	private Context mContext;

	public ProductLikedCustomerAdapter(Context context,
			ArrayList<ProductLikedCustomerItem> listofproductlikedcustomer) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.liked_customer_row, R.id.tvCustomerName,
				listofproductlikedcustomer);
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ProductLikedCustomerItem surveyList = (ProductLikedCustomerItem) this
				.getItem(position);

		ViewHolder holder;
		holder = new ViewHolder();

		convertView = inflater.inflate(R.layout.liked_customer_row, null);

		holder.tvCustomerName = (TextView) convertView
				.findViewById(R.id.tvCustomerName);
		holder.tvViewCustomerProfile = (TextView) convertView
				.findViewById(R.id.tvViewCustomerProfile);
		holder.imgViewCustomer = (ImageView) convertView
				.findViewById(R.id.imgViewCustomer);
		holder.imgViewSendGift = (ImageView) convertView
				.findViewById(R.id.imgViewSendGift);

		holder.tvViewCustomerProfile
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Log.v("id", surveyList.getCustomerId());

					}
				});

		convertView.setTag(holder);
		holder = (ViewHolder) convertView.getTag();

		holder.tvCustomerName.setText(surveyList.getCustomerName());

		return convertView;
	}

	public class ViewHolder {

		TextView tvCustomerName;
		TextView tvViewCustomerProfile;
		ImageView imgViewCustomer;
		ImageView imgViewSendGift;

	}
}
