package com.olivetag.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.olivetag.R;

public class SettingsFragment extends Fragment {

	View rootView;

	ToggleButton toggleButton;
	TextView tvShow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);

		initialize();

		onclick();

		return rootView;
	}

	private void initialize() {
		// TODO Auto-generated method stub

		toggleButton = (ToggleButton) rootView.findViewById(R.id.toggleButton);
		tvShow = (TextView) rootView.findViewById(R.id.tvShow);

	}

	private void onclick() {
		// TODO Auto-generated method stub

		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (isChecked) {
					tvShow.setVisibility(View.INVISIBLE);
				} else {
					tvShow.setVisibility(View.VISIBLE);
				}

			}
		});

	}
}