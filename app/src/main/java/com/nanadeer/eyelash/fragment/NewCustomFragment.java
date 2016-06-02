package com.nanadeer.eyelash.fragment;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.customview.wheelview.OnItemSelectedListener;
import com.nanadeer.eyelash.customview.wheelview.WheelView;
import com.nanadeer.eyelash.parameter.EyelashParameter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCustomFragment extends Fragment {

    private WheelView wvYear, wvMonth, wvDay, wvEyesType, wvStyle;
    private LinearLayout llDateWheel, llEyesTypeWheel, llStyleWheel;
    private ArrayList<String> eyesTypeList, styleList;
    private TextView tvDate, tvEyesType, tvLashStyle;
    private LinearLayout.LayoutParams layoutParams;
    private boolean dataWheelStatus, eyesWheelStatus, styleWheelStatus = false;

    public NewCustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_custom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setEyesWheelView(view);
        setStyleWheelView(view);
        setPhotoView(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void setPhotoView(View view){
        LinearLayout photoLayout = (LinearLayout)view.findViewById(R.id.photoLayout);
        photoLayout.setOnClickListener(mClickListener);
    }

    private void setDateWheelView(View view) {
        wvYear = new WheelView(getActivity());
        wvMonth = new WheelView(getActivity());
        wvDay = new WheelView(getActivity());

        LinearLayout dateLayout = (LinearLayout)view.findViewById(R.id.dateLayout);

        dateLayout.setOnClickListener(mClickListener);

        llDateWheel = (LinearLayout)view.findViewById(R.id.dateWheel);
        tvDate = (TextView)view.findViewById(R.id.dateValue);
    }

    private void setEyesWheelView(View view){
        LinearLayout eyesLayout = (LinearLayout)view.findViewById(R.id.eyeLayout);
        eyesLayout.setOnClickListener(mClickListener);

        llEyesTypeWheel = (LinearLayout)view.findViewById(R.id.eyeWheel);
        tvEyesType = (TextView)view.findViewById(R.id.eyeValue);
        wvEyesType = new WheelView(getActivity());

        eyesTypeList = new ArrayList<String>((Arrays.asList(getResources().getStringArray(R.array.eyes_type))));
        wvEyesType.setWVValue(eyesTypeList, false, 18, WheelView.CENTER);
        wvEyesType.setInitPosition(0);

        llEyesTypeWheel.addView(wvEyesType, layoutParams);
        llEyesTypeWheel.setGravity(Gravity.CENTER);

        tvEyesType.setText(wvEyesType.getSelectedItemText());
        wvEyesType.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                tvEyesType.setText(view.getSelectedItemText());
            }
        });

    }

    private void setStyleWheelView(View view){
        LinearLayout styleLayout = (LinearLayout)view.findViewById(R.id.styleLayout);
        styleLayout.setOnClickListener(mClickListener);

        llStyleWheel = (LinearLayout)view.findViewById(R.id.styleWheel);
        tvLashStyle = (TextView)view.findViewById(R.id.styleValue);
        wvStyle = new WheelView(getActivity());

        styleList = new ArrayList<String>((Arrays.asList(getResources().getStringArray(R.array.lash_style))));
        wvStyle.setWVValue(styleList, false, 18, WheelView.CENTER);
        wvStyle.setInitPosition(0);

        llStyleWheel.addView(wvStyle, layoutParams);
        llStyleWheel.setGravity(Gravity.CENTER);

        tvLashStyle.setText(wvStyle.getSelectedItemText());
        wvStyle.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                tvLashStyle.setText(view.getSelectedItemText());
            }
        });
    }

    public View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dateLayout:
                    break;
                case R.id.eyeLayout:
                    switchWheelStatus(llEyesTypeWheel, wvEyesType, tvEyesType, eyesWheelStatus);

                    if (eyesWheelStatus)
                        eyesWheelStatus = false;
                    else
                        eyesWheelStatus = true;

                    break;

                case R.id.styleLayout:
                    switchWheelStatus(llStyleWheel, wvStyle, tvLashStyle, styleWheelStatus);

                    if (styleWheelStatus)
                        styleWheelStatus = false;
                    else
                        styleWheelStatus = true;

                    break;

                case R.id.photoLayout:
                    PhotoFragment photoFragment = new PhotoFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(EyelashParameter.FRAGMENT_PHOTOS);
                    fragmentTransaction.replace(R.id.mainActivityContent, photoFragment);
                    fragmentTransaction.commit();
                    break;
                default:
                    break;
            }
        }
    };

    public void switchWheelStatus(View view, WheelView wheelView, TextView tvView, boolean status){
        if (status){
            view.setVisibility(View.GONE);
            tvView.setVisibility(View.VISIBLE);
            tvView.setText(wheelView.getSelectedItemText());
        } else {
            view.setVisibility(View.VISIBLE);
            tvView.setVisibility(View.INVISIBLE);
        }
    }
}
