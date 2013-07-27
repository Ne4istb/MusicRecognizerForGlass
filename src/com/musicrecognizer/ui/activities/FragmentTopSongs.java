package com.musicrecognizer.ui.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import d.musicrecognizer.R;

public class FragmentTopSongs extends Fragment {

	private View mViewContent;

	public static FragmentTopSongs newInstance() {

		FragmentTopSongs fragmentDirectionPoint = new FragmentTopSongs();

		return fragmentDirectionPoint;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mViewContent = inflater.inflate(R.layout.fragment_search,
				container, false);

		return mViewContent;
	}
}