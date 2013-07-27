package com.musicrecognizer.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import d.musicrecognizer.R;

public class ActivityMain extends FragmentActivity {

	private static final int NUM_PAGES = 3;

	private ViewPager mViewPager;
	private ScreenSlidePagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {

			setupViews();
			mViewPager.setCurrentItem(0);
		}
	}

	private void setupViews() {

		mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
		mViewPagerAdapter = new ScreenSlidePagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {

			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {

			switch (position) {

			case 0:

				return getString(R.string.fragment_search);
			case 1:
				return getString(R.string.fragment_tags);
			case 2:
				return getString(R.string.fragment_top_songs);
			default:
				return "";
			}
		}

		@Override
		public Fragment getItem(int position) {

			switch (position) {

			case 0:

				return FragmentTopSongs.newInstance();

			case 1:

				return FragmentTopSongs.newInstance();

			case 2:

				return FragmentSearch.newInstance();

			default:

				return null;
			}
		}

		@Override
		public int getCount() {

			return NUM_PAGES;
		}
	}
}
