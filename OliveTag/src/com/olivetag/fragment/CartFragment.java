package com.olivetag.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olivetag.HomeMainActivity;
import com.olivetag.R;
import com.olivetag.bin.CartItem;
import com.olivetag.database.DBAdapter;
import com.olivetag.database.SharedPreferenceClass;

public class CartFragment extends Fragment {

	View rootView;

	TextView tvTotalItem, tvTotalPrice, tvSubTotalPrice, tvGrandTotalPrice;
	ListView listCart;
	Button btnProceed;

	int total_item = 0, total_price = 0;

	DBAdapter db;
	long id;
	boolean remove;

	ArrayList<CartItem> listofCartItems;
	CartAdapter adapter;

	SharedPreferenceClass sharedpreference;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_cart, container, false);

		initialize();

		onclick();

		loadCartDetails();

		return rootView;
	}

	private void initialize() {
		// TODO Auto-generated method stub

		sharedpreference = new SharedPreferenceClass(getActivity());

		listofCartItems = new ArrayList<CartItem>();

		tvTotalItem = (TextView) rootView.findViewById(R.id.tvTotalItem);
		tvTotalPrice = (TextView) rootView.findViewById(R.id.tvTotalPrice);
		tvSubTotalPrice = (TextView) rootView
				.findViewById(R.id.tvSubTotalPrice);
		tvGrandTotalPrice = (TextView) rootView
				.findViewById(R.id.tvGrandTotalPrice);
		listCart = (ListView) rootView.findViewById(R.id.listCart);
		btnProceed = (Button) rootView.findViewById(R.id.btnProceed);

	}

	private void onclick() {
		// TODO Auto-generated method stub

		btnProceed.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((HomeMainActivity) getActivity()).loadCheckoutFragment();

			}
		});

	}

	private void loadCartDetails() {
		// TODO Auto-generated method stub

		total_item = 0;
		total_price = 0;

		db = new DBAdapter(getActivity());
		db.open();

		listofCartItems = db.getRecords();
		Log.v("sizeee", String.valueOf(listofCartItems.size()));

		db.close();

		if (listofCartItems.size() > 0) {

			adapter = new CartAdapter(getActivity(), listofCartItems);
			listCart.setAdapter(adapter);

			for (int i = 0; i < listofCartItems.size(); i++) {

				total_item = total_item
						+ Integer.parseInt(listofCartItems.get(i)
								.getProductQuantity());

				int quantity = Integer.parseInt(listofCartItems.get(i)
						.getProductQuantity());

				int price = Integer.parseInt(listofCartItems.get(i)
						.getProductPrice());

				total_price = total_price + (quantity * price);
			}

			sharedpreference.saveTotalItem(total_item);
			sharedpreference.saveTotalPrice(total_price);

			tvTotalItem.setText("Subtotal (" + String.valueOf(total_item)
					+ " items" + "):");
			tvTotalPrice.setText(" Rs. " + String.valueOf(total_price) + ".00");
			tvSubTotalPrice.setText(" Rs. " + String.valueOf(total_price)
					+ ".00");
			tvGrandTotalPrice.setText(" Rs. " + String.valueOf(total_price)
					+ ".00");

		}

		else {

			listofCartItems.clear();
			adapter = new CartAdapter(getActivity(), listofCartItems);
			listCart.setAdapter(adapter);

			sharedpreference.saveTotalItem(0);
			sharedpreference.saveTotalPrice(0);

			tvTotalItem.setText("Subtotal (0 item):");
			tvTotalPrice.setText(" Rs. 0.00");
			tvSubTotalPrice.setText(" Rs. 0.00");
			tvGrandTotalPrice.setText(" Rs. 0.00");

		}

	}

	public class CartAdapter extends ArrayAdapter<CartItem> {

		private LayoutInflater inflater;
		private Context mContext;
		ImageLoader imageLoader;

		public CartAdapter(Context context, ArrayList<CartItem> listofCartItems) {
			// TODO Auto-generated constructor stub
			super(context, R.layout.like_row, R.id.tvProducrName,
					listofCartItems);
			this.mContext = context;
			inflater = LayoutInflater.from(context);
			imageLoader = ImageLoader.getInstance();

		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final CartItem surveyList = (CartItem) this.getItem(position);

			ViewHolder holder;
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.cart_row, null);

			holder.tvProductName = (TextView) convertView
					.findViewById(R.id.tvProductName);
			holder.tvProductBrand = (TextView) convertView
					.findViewById(R.id.tvProductBrand);
			holder.tvProductPrice = (TextView) convertView
					.findViewById(R.id.tvProductPrice);
			holder.tvProductAvailability = (TextView) convertView
					.findViewById(R.id.tvProductAvailability);
			holder.imgViewProduct = (ImageView) convertView
					.findViewById(R.id.imgViewProduct);
			holder.btnProductQuantity = (Button) convertView
					.findViewById(R.id.btnProductQuantity);
			holder.btnRemove = (Button) convertView
					.findViewById(R.id.btnRemove);

			holder.btnRemove.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					db = new DBAdapter(getActivity());
					db.open();

					remove = db.deleteRecord(surveyList.getCartId());

					db.close();

					((HomeMainActivity) getActivity()).decrementCart();
					loadCartDetails();

				}
			});

			convertView.setTag(holder);
			holder = (ViewHolder) convertView.getTag();

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.showImageOnLoading(R.drawable.defaultimage)
					.showImageForEmptyUri(R.drawable.defaultimage)
					.showImageOnFail(R.drawable.defaultimage).build();

			holder.tvProductName.setText(surveyList.getProductName());

			if (!surveyList.getProductBrand().equals(("null"))
					&& !surveyList.getProductBrand().equals(("false"))) {

				holder.tvProductBrand.setText(surveyList.getProductBrand());
			}

			holder.tvProductPrice
					.setText("Rs. " + surveyList.getProductPrice());

			if (!surveyList.getProductAvailability().equals(("0"))) {

				holder.tvProductAvailability.setText("in Stock");
			} else {

				holder.tvProductAvailability.setText("out of Stock");

			}

			holder.btnProductQuantity.setText(surveyList.getProductQuantity());

			imageLoader.displayImage(surveyList.getProductImage(),
					holder.imgViewProduct, options);

			return convertView;
		}

		public class ViewHolder {

			private TextView tvProductName;
			private TextView tvProductBrand;
			private TextView tvProductPrice;
			private TextView tvProductAvailability;
			private ImageView imgViewProduct;
			private Button btnProductQuantity;
			private Button btnRemove;

		}

	}
}
