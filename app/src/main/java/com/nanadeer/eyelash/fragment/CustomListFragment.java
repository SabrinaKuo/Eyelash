package com.nanadeer.eyelash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.adapter.CustomListAdapter;
import com.nanadeer.eyelash.customview.ItemDecoration;
import com.nanadeer.eyelash.database.CustomInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomListFragment extends Fragment {


    public CustomListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fake data
        ArrayList<CustomInfo> list = new ArrayList<>();
        list.add(new CustomInfo("Sabrina", "0987011812"));
        list.add(new CustomInfo("Sindy", "0989363821"));

        View view = inflater.inflate(R.layout.fragment_custom_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CustomListAdapter(list));
        recyclerView.addItemDecoration(new ItemDecoration(getContext(), OrientationHelper.HORIZONTAL));
        return view;
    }

}
