package com.olivetag.fragment;

import java.util.ArrayList;
import java.util.Vector;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olivetag.R;
import com.olivetag.adapter.MediaPagerAdapter;
import com.olivetag.bin.ProductDetailsItem;
import com.viewpagerindicator.CirclePageIndicator;

public class ProductZoomFragment extends Fragment {

	View rootView;

	ViewPager pagercontainer;
	CirclePageIndicator indicator;
	LayoutInflater _layoutInflater;
	Vector<View> pages;
	View page1, page2, page3, page4;

	ImageView imgProductView1, imgProductView2, imgProductView3,
			imgProductView4;

	static ArrayList<ProductDetailsItem> listofproductitems;
	static int current_product;
	static int current_view = 0;
	ArrayList<String> imageList;

	ImageLoader imageLoader;

	@SuppressWarnings("unchecked")
	public static ProductZoomFragment newInstance(Bundle bundleToPass) {
		// TODO Auto-generated method stub

		ProductZoomFragment fragment = new ProductZoomFragment();
		listofproductitems = (ArrayList<ProductDetailsItem>) bundleToPass
				.getSerializable("listofproductitems");
		current_product = (int) bundleToPass.getInt("current_product");
		current_view = (int) bundleToPass.getInt("current_view");
		fragment.setArguments(bundleToPass);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		rootView = inflater.inflate(R.layout.fragment_product_zoom, container,
				false);

		initialize();

		setvalue();

		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		listofproductitems.clear();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		listofproductitems.clear();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
		listofproductitems.clear();
	}

	private void initialize() {
		// TODO Auto-generated method stub

		imageLoader = ImageLoader.getInstance();

		pagercontainer = (ViewPager) rootView.findViewById(R.id.pagercontainer);
		indicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);

		pages = new Vector<View>();
		_layoutInflater = getActivity().getLayoutInflater();

		page1 = _layoutInflater.inflate(R.layout.product_details_one, null);
		page2 = _layoutInflater.inflate(R.layout.product_details_two, null);
		page3 = _layoutInflater.inflate(R.layout.product_details_three, null);
		page4 = _layoutInflater.inflate(R.layout.product_details_four, null);

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

	}

	private void setvalue() {
		// TODO Auto-generated method stub

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
		pagercontainer.setCurrentItem(current_view);
		indicator.setClickable(true);
		indicator.setSnap(true);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.showImageOnLoading(R.drawable.home_icon)
				.showImageForEmptyUri(R.drawable.home_icon)
				.showImageOnFail(R.drawable.home_icon).build();

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

	}

}
