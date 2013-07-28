package com.musicrecognizer.ui.activities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.Track;
import com.musicrecognizer.utils.UtilsHelper;

import d.musicrecognizer.R;

public class FragmentSearch extends Fragment implements OnClickListener {

	public int RECORD_FILE_TIME = 10;
	public int UPDATE_PERIOD = 1000;

	private View mViewContent;

	private static String mFileName = null;

	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;
	private boolean mIsRecording = false;

	private Button mBtnRecord;
	private ProgressBar mProgressBar;

	private int counter = 0;

	private EchoNestAPI mEchoNestAPI;

	public static FragmentSearch newInstance() {

		FragmentSearch fragmentSearch = new FragmentSearch();

		return fragmentSearch;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mViewContent = inflater.inflate(R.layout.fragment_search, container,
				false);

		setupViews();

		return mViewContent;
	}

	private void setupViews() {

		mBtnRecord = (Button) mViewContent.findViewById(R.id.search_btn_record);
		mBtnRecord.setOnClickListener(this);
		mProgressBar = (ProgressBar) mViewContent
				.findViewById(R.id.search_progress_bar);
		mProgressBar.setMax(RECORD_FILE_TIME);
	}

	private void onRecord(boolean isRecording) {

		if (!isRecording) {

			mFileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			mFileName += "/" + UtilsHelper.FILE_RECORD_NAME;
			File tempFileRecording = new File(mFileName);
			if (tempFileRecording.exists()) {

				tempFileRecording.delete();
			}
			startRecording();
		} else {

			stopRecording(false);
		}
	}

	private void startRecording() {

		mIsRecording = true;

		mBtnRecord.setText(R.string.fragment_search_btn_search_stop);
		mProgressBar.setVisibility(View.VISIBLE);
		mProgressBar.setProgress(0);

		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {

			mRecorder.prepare();
		} catch (IOException e) {

			UtilsHelper.printException(e);
		}

		mRecorder.start();

		counter = 0;

		updateProg();
	}

	private void updateProg() {

		new Thread() {

			public void run() {

				try {

					TimeUnit.SECONDS.sleep(1);

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {

							if (!mIsRecording)
								stopRecording(true);
							mProgressBar.setProgress(++counter);
							if (counter == RECORD_FILE_TIME) {

								stopRecording(false);
							}
							updateProg();
						}
					});
				} catch (Exception e) {

					UtilsHelper.printException(e);
				}
			};
		}.start();
	}

	private void stopRecording(boolean isForce) {

		mIsRecording = false;
		mBtnRecord.setText(R.string.fragment_search_btn_search);
		mProgressBar.setVisibility(View.GONE);

		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		mProgressBar.setProgress(0);

		if (!isForce) {

			new AsyncTaskSeachSong().execute();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.search_btn_record:

			onRecord(mIsRecording);
			break;
		}
	}

	private class AsyncTaskSeachSong extends AsyncTask<Void, Void, Track> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mBtnRecord.setVisibility(View.GONE);
		}

		@Override
		protected Track doInBackground(Void... params) {

			try {

				File file = new File(mFileName);
				Track track = mEchoNestAPI.uploadTrack(file);
				track.waitForAnalysis(30000);

				return track;
			} catch (Exception e) {

				UtilsHelper.printException(e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Track result) {
			super.onPostExecute(result);

			try {

				Toast.makeText(getActivity(), result.getTitle(),
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {

				UtilsHelper.printException(e);
			}
		}
	}
}