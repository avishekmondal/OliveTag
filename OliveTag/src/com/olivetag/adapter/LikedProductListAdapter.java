package com.olivetag.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olivetag.R;
import com.olivetag.bin.ProductDetailsItem;

public class LikedProductListAdapter extends ArrayAdapter<ProductDetailsItem> {

	private LayoutInflater inflater;
	private Context mContext;
	ImageLoader imageLoader;

	public LikedProductListAdapter(Context context,
			ArrayList<ProductDetailsItem> listoflikedproducts) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.like_row, R.id.tvProducrName,
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

		convertView = inflater.inflate(R.layout.like_row, null);

		holder.tvProductName = (TextView) convertView
				.findViewById(R.id.tvProductName);
		holder.tvBrand = (TextView) convertView.findViewById(R.id.tvBrand);
		holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
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

		holder.tvProductName.setText(surveyList.getProductName());

		if (!surveyList.getBrand().equals(("null"))
				&& !surveyList.getBrand().equals(("false"))) {

			holder.tvBrand.setText(surveyList.getBrand());
		}

		if (!surveyList.getOfferPrice().equals(("null"))) {

			holder.tvPrice.setText("Rs. "
					+ surveyList.getOfferPrice().replace(".0000", ""));

		}

		else {

			holder.tvPrice.setText("Rs. "
					+ surveyList.getActualPrice().replace(".0000", ""));
		}

		imageLoader.displayImage(imageList.get(0).toString(),
				holder.imgViewProduct, options);

		return convertView;
	}

	public class ViewHolder {

		private TextView tvProductName;
		private TextView tvBrand;
		private TextView tvPrice;
		private ImageView imgViewProduct;

	}

}
