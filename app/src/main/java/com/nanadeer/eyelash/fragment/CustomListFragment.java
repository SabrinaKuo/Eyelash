package com.nanadeer.eyelash.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.adapter.CustomListAdapter;
import com.nanadeer.eyelash.customview.ItemDecoration;
import com.nanadeer.eyelash.database.CustomInfo;
import com.nanadeer.eyelash.database.CustomerTable;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomListFragment extends Fragment {
    private ArrayList<CustomInfo> mList;
    private FragmentManager mFragmentManager = null;
    private RecyclerView mResultRecyclerView = null;

    public CustomListFragment() {
        mList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDataFromDB();
        return inflater.inflate(R.layout.fragment_custom_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        ImageButton addBtn = (ImageButton) view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("IS_NEW_RECORD", true);

                NewCustomFragment fragment = new NewCustomFragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.mainActivityContent, fragment);
                transaction.commit();
            }
        });

        CustomListAdapter adapter = new CustomListAdapter(getActivity(), mList);

        mResultRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mResultRecyclerView.setAdapter(adapter);
        mResultRecyclerView.setItemAnimator(null);
        mResultRecyclerView.addItemDecoration(new ItemDecoration(getContext()));

        // Create SearchView
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);

        // Customize SearchView UI
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = searchView.findViewById(searchPlateId);
        if(searchPlateView != null){
            searchPlateView.setBackgroundColor(Color.WHITE);
        }

        int textviewId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView)searchView.findViewById(textviewId);
        textView.setTextSize(16);
        textView.setHintTextColor(Color.GRAY);

        // Set SearchView Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mResultRecyclerView.setAdapter(new CustomListAdapter(getActivity(), OnFilter(mList, newText)));
                return true;
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private ArrayList<CustomInfo> OnFilter(ArrayList<CustomInfo>filterLocales, String text){

        ArrayList<CustomInfo> filtered = new ArrayList<>();

        for(CustomInfo info : filterLocales){
            if (info.getPhone().contains(text)){
                filtered.add(info);
            }
        }

        return filtered;
    }

    private void getDataFromDB(){

        mList = CustomerTable.getAllCustoms();
    }

}
