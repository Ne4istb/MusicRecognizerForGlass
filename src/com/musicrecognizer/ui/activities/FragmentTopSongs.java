package com.musicrecognizer.ui.activities;

import android.support.v4.app.Fragment;
import android.view.View;

public class FragmentTopSongs extends Fragment {

	private View mViewContent;

	public static FragmentTopSongs newInstance() {

		FragmentTopSongs fragmentDirectionPoint = new FragmentTopSongs();

		return fragmentDirectionPoint;
	}
}