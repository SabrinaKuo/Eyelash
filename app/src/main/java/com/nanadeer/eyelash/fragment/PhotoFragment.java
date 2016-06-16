package com.nanadeer.eyelash.fragment;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.adapter.AlbumListAdapter;
import com.nanadeer.eyelash.database.PhotoInfo;

import java.util.ArrayList;

/**
 * eyelash photos fragment
 */
public class PhotoFragment extends Fragment {


    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView photoRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_photo, container, false);

        // fake data
        ArrayList<PhotoInfo> photoDataList = new ArrayList<>();
        photoDataList.add(new PhotoInfo("test", "testing"));
        photoDataList.add(new PhotoInfo("test2", "coooooooool"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        photoRecycler.setLayoutManager(layoutManager);

        AlbumListAdapter adpater = new AlbumListAdapter(getActivity(), photoDataList);
        photoRecycler.setAdapter(adpater);

        return photoRecycler;
    }

}
