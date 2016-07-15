package com.nanadeer.eyelash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.adapter.CustomDetailListAdapter;
import com.nanadeer.eyelash.customview.ItemDecoration;
import com.nanadeer.eyelash.database.CustomInfo;
import com.nanadeer.eyelash.database.CustomerTable;
import com.nanadeer.eyelash.parameter.EyelashParameter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDetailFragment extends Fragment {
    private ArrayList<CustomInfo> mList;
    private String mCustomName;
    private String mCustomPhone;
    public static RecyclerView recyclerView;

    public CustomDetailFragment() {
        mList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCustomName = bundle.getString("NAME");
        mCustomPhone = bundle.getString("PHONE");
        getDataFromDB(mCustomPhone);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView titleTextView = (TextView)view.findViewById(R.id.custom_name_title);
        ImageButton addBtn = (ImageButton)view.findViewById(R.id.detail_add_btn);

        titleTextView.setText(mCustomName);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("IS_NEW_RECORD", true);
                bundle.putString("NAME", mCustomName);
                bundle.putString("PHONE", mCustomPhone);

                NewCustomFragment fragment = new NewCustomFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActivityContent, fragment)
                        .addToBackStack(EyelashParameter.FRAGMENT_ADD)
                        .commit();
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.custom_detail_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new ItemDecoration(getActivity()));
        recyclerView.setAdapter(new CustomDetailListAdapter(getActivity(), mList));

        super.onViewCreated(view, savedInstanceState);
    }

    private void getDataFromDB(String customName){
        CustomerTable table = new CustomerTable();

        mList = table.getDetectCustom(customName);
    }
}
