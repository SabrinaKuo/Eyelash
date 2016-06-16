package com.nanadeer.eyelash.fragment;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.customview.wheelview.OnItemSelectedListener;
import com.nanadeer.eyelash.customview.wheelview.WheelView;
import com.nanadeer.eyelash.parameter.EyelashParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCustomFragment extends Fragment {

    private WheelView wvMaterial, wvEyesType, wvStyle, wvCurl, wvLength;
    private LinearLayout llMaterialWheel, llEyesTypeWheel, llStyleWheel, llCurlWheel, llLengthWheel;
    private DatePicker mDatePicker;
    private ArrayList<String> eyesTypeList, styleList, materialList, curlList, lengthList;
    private TextView tvDate, tvEyesType, tvLashStyle, tvMaterial, tvCurl, tvLength;
    private LinearLayout.LayoutParams layoutParams;
    private boolean dataWheelStatus, eyesWheelStatus, styleWheelStatus, materialWheelStatus, curlWheelStatus, lengthWheelStatus = false;

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
        setDateWheelView(view);
        setEyesWheelView(view);
        setMaterialWheelView(view);
        setStyleWheelView(view);
        setCurlWheelView(view);
        setLengthWheelView(view);
        setPhotoView(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void setPhotoView(View view){
        LinearLayout photoLayout = (LinearLayout)view.findViewById(R.id.photoLayout);
        photoLayout.setOnClickListener(mClickListener);
    }

    private void setDateWheelView(View view) {
        LinearLayout dateLayout = (LinearLayout)view.findViewById(R.id.dateLayout);
        dateLayout.setOnClickListener(mClickListener);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        // month start from 0
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        tvDate = (TextView) view.findViewById(R.id.dateValue);
        tvDate.setText(year + "/" + month + "/" + day);

        mDatePicker = (DatePicker)view.findViewById(R.id.datePicker);
        mDatePicker.setCalendarViewShown(false);

        mDatePicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        });

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

    private void setMaterialWheelView(View view){
        LinearLayout materialLayout = (LinearLayout)view.findViewById(R.id.materialLayout);
        materialLayout.setOnClickListener(mClickListener);

        llMaterialWheel = (LinearLayout)view.findViewById(R.id.materialWheel);
        tvMaterial = (TextView)view.findViewById(R.id.materialValue);
        wvMaterial = new WheelView(getActivity());

        materialList = new ArrayList<String>((Arrays.asList(getResources().getStringArray(R.array.lash_material))));
        wvMaterial.setWVValue(materialList, false, 18, WheelView.CENTER);
        wvMaterial.setInitPosition(0);

        llMaterialWheel.addView(wvMaterial, layoutParams);
        llMaterialWheel.setGravity(Gravity.CENTER);

        tvMaterial.setText(wvMaterial.getSelectedItemText());
        wvMaterial.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                tvMaterial.setText(view.getSelectedItemText());
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

    private void setCurlWheelView(View view){
        LinearLayout curlLayout = (LinearLayout)view.findViewById(R.id.curlLayout);
        curlLayout.setOnClickListener(mClickListener);

        llCurlWheel = (LinearLayout)view.findViewById(R.id.curlWheel);
        tvCurl = (TextView)view.findViewById(R.id.curlValue);
        wvCurl = new WheelView(getActivity());

        curlList = new ArrayList<String>((Arrays.asList(getResources().getStringArray(R.array.curl))));
        wvCurl.setWVValue(curlList, false, 18, WheelView.CENTER);
        wvCurl.setInitPosition(0);

        llCurlWheel.addView(wvCurl, layoutParams);
        llCurlWheel.setGravity(Gravity.CENTER);

        tvCurl.setText(wvCurl.getSelectedItemText());
        wvCurl.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                tvCurl.setText(view.getSelectedItemText());
            }
        });
    }

    private void setLengthWheelView(View view){
        LinearLayout lengthLayout = (LinearLayout)view.findViewById(R.id.lengthLayout);
        lengthLayout.setOnClickListener(mClickListener);

        llLengthWheel = (LinearLayout)view.findViewById(R.id.lengthWheel);
        tvLength = (TextView)view.findViewById(R.id.lengthValue);
        wvLength = new WheelView(getActivity());

        lengthList = new ArrayList<String>((Arrays.asList(getResources().getStringArray(R.array.length))));
        wvLength.setWVValue(lengthList, false, 18, WheelView.CENTER);
        wvLength.setInitPosition(0);

        llLengthWheel.addView(wvLength, layoutParams);
        llLengthWheel.setGravity(Gravity.CENTER);

        tvLength.setText(wvLength.getSelectedItemText());
        wvLength.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                tvLength.setText(view.getSelectedItemText());
            }
        });
    }

    public View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dateLayout:
//                    datePickerDialog.show();
                    if (dataWheelStatus){
                        mDatePicker.setVisibility(View.GONE);
                        tvDate.setVisibility(View.VISIBLE);
                        dataWheelStatus = false;
                    } else {
                        mDatePicker.setVisibility(View.VISIBLE);
                        tvDate.setVisibility(View.INVISIBLE);
                        dataWheelStatus = true;
                    }
                    break;
                case R.id.eyeLayout:
                    switchWheelStatus(llEyesTypeWheel, wvEyesType, tvEyesType, eyesWheelStatus);

                    if (eyesWheelStatus)
                        eyesWheelStatus = false;
                    else
                        eyesWheelStatus = true;

                    break;

                case R.id.materialLayout:
                    switchWheelStatus(llMaterialWheel, wvMaterial, tvMaterial, materialWheelStatus);

                    if (materialWheelStatus)
                        materialWheelStatus = false;
                    else
                        materialWheelStatus = true;
                    break;

                case R.id.styleLayout:
                    switchWheelStatus(llStyleWheel, wvStyle, tvLashStyle, styleWheelStatus);

                    if (styleWheelStatus)
                        styleWheelStatus = false;
                    else
                        styleWheelStatus = true;

                    break;

                case R.id.curlLayout:
                    switchWheelStatus(llCurlWheel, wvCurl, tvCurl, curlWheelStatus);

                    if (curlWheelStatus)
                        curlWheelStatus = false;
                    else
                        curlWheelStatus = true;
                    break;

                case R.id.lengthLayout:
                    switchWheelStatus(llLengthWheel, wvLength, tvLength, lengthWheelStatus);

                    if (lengthWheelStatus)
                        lengthWheelStatus = false;
                    else
                        lengthWheelStatus = true;
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
