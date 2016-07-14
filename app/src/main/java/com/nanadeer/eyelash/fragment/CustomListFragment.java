package com.nanadeer.eyelash.fragment;


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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new ItemDecoration(getContext()));

        super.onViewCreated(view, savedInstanceState);
    }

    private void getDataFromDB(){

        mList = CustomerTable.getAllCustoms();
    }

}
