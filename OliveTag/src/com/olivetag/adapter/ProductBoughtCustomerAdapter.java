package com.olivetag.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.olivetag.R;
import com.olivetag.bin.ProductBoughtCustomerItem;

public class ProductBoughtCustomerAdapter extends
		ArrayAdapter<ProductBoughtCustomerItem> {

	private LayoutInflater inflater;
	private Context mContext;

	public ProductBoughtCustomerAdapter(Context context,
			ArrayList<ProductBoughtCustomerItem> listofproductboughtcustomer) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.bought_customer_row, R.id.tvCustomerName,
				listofproductboughtcustomer);
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ProductBoughtCustomerItem surveyList = (ProductBoughtCustomerItem) this
				.getItem(position);

		ViewHolder holder;
		holder = new ViewHolder();

		convertView = inflater.inflate(R.layout.bought_customer_row, null);

		holder.tvCustomerName = (TextView) convertView
				.findViewById(R.id.tvCustomerName);
		holder.tvViewCustomerProfile = (TextView) convertView
				.findViewById(R.id.tvViewCustomerProfile);
		holder.tvBoughtBefore = (TextView) convertView
				.findViewById(R.id.tvBoughtBefore);
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
		holder.tvBoughtBefore.setText(surveyList.getBoughtBefore() + " ago");

		return convertView;
	}

	public class ViewHolder {

		TextView tvCustomerName;
		TextView tvViewCustomerProfile;
		TextView tvBoughtBefore;
		ImageView imgViewCustomer;
		ImageView imgViewSendGift;

	}
}
