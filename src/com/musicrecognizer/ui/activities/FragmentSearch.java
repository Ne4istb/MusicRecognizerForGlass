package com.musicrecognizer.ui.activities;

import d.musicrecognizer.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSearch extends Fragment {

	private View mViewContent;

	public static FragmentSearch newInstance() {

		FragmentSearch fragmentSearch = new FragmentSearch();

		return fragmentSearch;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mViewContent = inflater.inflate(R.layout.fragment_search, container,
				false);

		return mViewContent;
	}
}