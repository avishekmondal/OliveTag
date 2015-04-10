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
import com.olivetag.bin.ProductCommentedCustomerItem;

public class ProductCommentedCustomerAdapter extends
		ArrayAdapter<ProductCommentedCustomerItem> {

	private LayoutInflater inflater;
	private Context mContext;

	public ProductCommentedCustomerAdapter(
			Context context,
			ArrayList<ProductCommentedCustomerItem> listofproductcommentedcustomer) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.commented_customer_row, R.id.tvCustomerName,
				listofproductcommentedcustomer);
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ProductCommentedCustomerItem surveyList = (ProductCommentedCustomerItem) this
				.getItem(position);

		ViewHolder holder;
		holder = new ViewHolder();

		convertView = inflater.inflate(R.layout.commented_customer_row, null);

		holder.tvCustomerName = (TextView) convertView
				.findViewById(R.id.tvCustomerName);
		holder.tvViewCustomerProfile = (TextView) convertView
				.findViewById(R.id.tvViewCustomerProfile);
		holder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
		holder.imgViewCustomer = (ImageView) convertView
				.findViewById(R.id.imgViewCustomer);

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
		holder.tvComment.setText(surveyList.getComment());

		return convertView;
	}

	public class ViewHolder {

		TextView tvCustomerName;
		TextView tvViewCustomerProfile;
		TextView tvComment;
		ImageView imgViewCustomer;

	}
}
