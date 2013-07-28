package com.musicrecognizer.ui.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.musicrecognizer.utils.UtilsHelper;

import java.util.List;

import d.musicrecognizer.R;

public class TrackListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    List<Track> objects;

    TrackListAdapter(Context context, List<Track> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.top_list_item, parent, false);
        }

        Track track = getTrack(position);

        try {
            ((TextView) view.findViewById(R.id.artist_name)).setText(track.ArtistName);
            ((TextView) view.findViewById(R.id.song_name)).setText(track.Title);
        } catch (Exception e) {
            UtilsHelper.printException(e);
        }
        return view;
    }

    Track getTrack(int position) {
        return ((Track) getItem(position));
    }
}