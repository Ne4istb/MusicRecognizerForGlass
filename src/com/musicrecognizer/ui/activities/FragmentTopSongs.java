package com.musicrecognizer.ui.activities;

import java.util.ArrayList;
import java.util.List;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestAPI;
import com.musicrecognizer.utils.UtilsHelper;

import d.musicrecognizer.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentTopSongs extends Fragment {

	private View mViewContent;
	private ListView mArtistList;

	public static FragmentTopSongs newInstance() {

		FragmentTopSongs fragmentDirectionPoint = new FragmentTopSongs();

		return fragmentDirectionPoint;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mViewContent = inflater.inflate(R.layout.fragment_top, container,
				false);
		
		mArtistList = (ListView) mViewContent.findViewById(R.id.fragment_list);
				
		GetTopArtistsAsync task = new GetTopArtistsAsync();
		task.execute();

		return mViewContent;
	}
	
	private class GetTopArtistsAsync extends AsyncTask<Void,Void,List<Artist>> {
		
		private List<Artist> artists = null;
		
		@Override
		protected List<Artist> doInBackground(Void... params) {
				
			EchoNestAPI echoNest = new EchoNestAPI(UtilsHelper.API_KEY);
			
			try{
				artists =  echoNest.topHotArtists(10);
			}
			catch(Exception e){
				UtilsHelper.printException(e);
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(List<Artist> result) {
			super.onPostExecute(result);
			
			List<String> names = new ArrayList<String>();
			
			try
			{
				for (Artist artist : artists) {
					names.add(artist.getName());
				}
			}
			catch(Exception e){
				UtilsHelper.printException(e);
			}
			
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		    		R.layout.top_list_item, names);

		    mArtistList.setAdapter(adapter);
		}
	}
}