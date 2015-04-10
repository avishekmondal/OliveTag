package com.olivetag.adapter;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olivetag.R;
import com.olivetag.bin.ProductDetailsItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LikedProductGridAdapter extends
		ArrayAdapter<ProductDetailsItem> {

	private LayoutInflater inflater;
	private Context mContext;
	ImageLoader imageLoader;

	public LikedProductGridAdapter(Context context,
			ArrayList<ProductDetailsItem> listoflikedproducts) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.like_row_grid, R.id.tvProducrName,
				listoflikedproducts);
		this.mContext = context;
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ProductDetailsItem surveyList = (ProductDetailsItem) this
				.getItem(position);

		ViewHolder holder;
		holder = new ViewHolder();

		convertView = inflater.inflate(R.layout.like_row_grid, null);

		holder.tvProducrName = (TextView) convertView
				.findViewById(R.id.tvProducrName);
		holder.imgViewProduct = (ImageView) convertView
				.findViewById(R.id.imgViewProduct);

		convertView.setTag(holder);
		holder = (ViewHolder) convertView.getTag();

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.showImageOnLoading(R.drawable.defaultimage)
				.showImageForEmptyUri(R.drawable.defaultimage)
				.showImageOnFail(R.drawable.defaultimage).build();

		ArrayList<String> imageList = new ArrayList<String>();
		imageList = surveyList.getImagelist();

		holder.tvProducrName.setText(surveyList.getProductName());
		imageLoader.displayImage(imageList.get(0).toString(),
				holder.imgViewProduct, options);

		return convertView;
	}

	public class ViewHolder {

		private TextView tvProducrName;
		private ImageView imgViewProduct;

	}

}
