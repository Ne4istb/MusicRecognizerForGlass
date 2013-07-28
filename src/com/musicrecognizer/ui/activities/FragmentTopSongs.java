package com.musicrecognizer.ui.activities;

import java.util.ArrayList;
import java.util.List;

import com.echonest.api.v4.Artist;
import com.musicrecognizer.utils.UtilsHelper;

import d.musicrecognizer.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class FragmentTopSongs extends Fragment {

    private View mViewContent;
	private ListView mTrackList;
    private ProgressBar mProgressBar;
    private Button mButton;

    public static FragmentTopSongs newInstance() {
		FragmentTopSongs fragmentDirectionPoint = new FragmentTopSongs();
		return fragmentDirectionPoint;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mViewContent = inflater.inflate(R.layout.fragment_top, container, false);

        mProgressBar = (ProgressBar) mViewContent.findViewById(R.id.top_progress_bar);
        mButton = (Button) mViewContent.findViewById(R.id.top_subscribe_button);
		mTrackList = (ListView) mViewContent.findViewById(R.id.fragment_list);
				
		new GetTopTracksAsync().execute();

		return mViewContent;
	}
	
	private class GetTopTracksAsync extends AsyncTask<Void,Void,List<Artist>> {

        String topTracksUri = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=028d73272c6c83dec35817d247f511f9&format=json&limit=5";

        private List<Track> tracks = new ArrayList<Track>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
		protected List<Artist> doInBackground(Void... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(topTracksUri);
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String jsonString = EntityUtils.toString(entity);

                    JSONObject result = new JSONObject(jsonString);

                    JSONObject tracksRootJson = result.getJSONObject("tracks");
                    JSONArray tracksJson = tracksRootJson.getJSONArray("track");
                    for (int i=0; i<tracksJson.length(); i++) {
                        JSONObject trackJson = tracksJson.getJSONObject(i);
                        String trackTitle = trackJson.getString("name");
                        JSONObject artistJson = trackJson.getJSONObject("artist");
                        String artistName = artistJson.getString("name");

                        tracks.add(new Track(artistName, trackTitle));
                    }
                }
            } catch (Exception e) {
                UtilsHelper.printException(e);
            }

            return null;
		}

		@Override
		protected void onPostExecute(List<Artist> result) {
			super.onPostExecute(result);
			
            TrackListAdapter adapter = new TrackListAdapter(getActivity(), tracks);

		    mTrackList.setAdapter(adapter);

            mProgressBar.setVisibility(View.GONE);
            mButton.setVisibility(View.VISIBLE);
		}
	}

}