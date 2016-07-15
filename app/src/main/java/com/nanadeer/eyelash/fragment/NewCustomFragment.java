package com.nanadeer.eyelash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.adapter.CustomDetailListAdapter;
import com.nanadeer.eyelash.customview.wheelview.OnItemSelectedListener;
import com.nanadeer.eyelash.customview.wheelview.WheelView;
import com.nanadeer.eyelash.database.CustomInfo;
import com.nanadeer.eyelash.database.CustomerTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCustomFragment extends Fragment {

    private WheelView wvMaterial, wvEyesType, wvStyle, wvCurl, wvLength, wvThick;
    private LinearLayout llMaterialWheel, llEyesTypeWheel, llStyleWheel, llCurlWheel, llLengthWheel, llThickWheel;
    private DatePicker mDatePicker;
    private TextView tvDate, tvEyesType, tvLashStyle, tvMaterial, tvCurl, tvLength, tvThick;
    private LinearLayout.LayoutParams layoutParams;
    private boolean dataWheelStatus, eyesWheelStatus, styleWheelStatus, materialWheelStatus, curlWheelStatus, lengthWheelStatus, thickWheelStatus = false;

    private EditText mNameEditText;
    private EditText mPhoneEditText;

    private CustomInfo mCustomInfo;

    private boolean mIsNewRecord = true;
    private String mCustomName;
    private String mCustomPhone;
    private long mOrderId;

    public NewCustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mIsNewRecord = bundle.getBoolean("IS_NEW_RECORD");
            mOrderId = bundle.getLong("ORDER_ID");
            mCustomName = bundle.getString("NAME");
            mCustomPhone = bundle.getString("PHONE");
        }

        if (mIsNewRecord) {
            mCustomInfo = new CustomInfo();
        } else {
            mCustomInfo = CustomerTable.getCustomRecordById(mOrderId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_custom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mNameEditText = (EditText) view.findViewById(R.id.name);
        mPhoneEditText = (EditText) view.findViewById(R.id.phone);
        TextView saveTextView = (TextView) view.findViewById(R.id.save_textview);
        ImageButton backBtn = (ImageButton) view.findViewById(R.id.back_btn);

        saveTextView.setOnClickListener(mBarClickListener);
        backBtn.setOnClickListener(mBarClickListener);

        if (!mIsNewRecord) {
            mNameEditText.setText(mCustomInfo.getName());
            mPhoneEditText.setText(mCustomInfo.getPhone());
        } else {
            mNameEditText.setText(mCustomName);
            mPhoneEditText.setText(mCustomPhone);
        }

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setDateWheelView(view);
        setEyesWheelView(view);
        setMaterialWheelView(view);
        setStyleWheelView(view);
        setThickWheelView(view);
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

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        final Calendar cal = Calendar.getInstance();

        if (!mIsNewRecord) {
            try {
                cal.setTime(sdf.parse(mCustomInfo.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String date = sdf.format(cal.getTime());

        tvDate = (TextView) view.findViewById(R.id.dateValue);
        tvDate.setText(date);

        mDatePicker = (DatePicker)view.findViewById(R.id.datePicker);
        mDatePicker.setCalendarViewShown(false);

        mDatePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tvDate.setText(sdf.format(cal.getTime()));
            }
        });

    }


    private void setEyesWheelView(View view){
        LinearLayout eyesLayout = (LinearLayout)view.findViewById(R.id.eyeLayout);
        eyesLayout.setOnClickListener(mClickListener);

        llEyesTypeWheel = (LinearLayout)view.findViewById(R.id.eyeWheel);
        tvEyesType = (TextView)view.findViewById(R.id.eyeValue);
        wvEyesType = new WheelView(getActivity());

        ArrayList<String> eyesTypeList = new ArrayList<>((Arrays.asList(getResources().getStringArray(R.array.eyes_type))));
        wvEyesType.setWVValue(eyesTypeList, false, 18, WheelView.CENTER);
        wvEyesType.setInitPosition(0);

        llEyesTypeWheel.addView(wvEyesType, layoutParams);
        llEyesTypeWheel.setGravity(Gravity.CENTER);

        tvEyesType.setText(wvEyesType.getSelectedItemText());
        wvEyesType.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                String eyesType = view.getSelectedItemText();
                tvEyesType.setText(eyesType);
            }
        });

        if (!mIsNewRecord){
            wvEyesType.setInitPosition(eyesTypeList.indexOf(mCustomInfo.getEyesType()));
            tvEyesType.setText(mCustomInfo.getEyesType());
        }

    }

    private void setMaterialWheelView(View view){
        LinearLayout materialLayout = (LinearLayout)view.findViewById(R.id.materialLayout);
        materialLayout.setOnClickListener(mClickListener);

        llMaterialWheel = (LinearLayout)view.findViewById(R.id.materialWheel);
        tvMaterial = (TextView)view.findViewById(R.id.materialValue);
        wvMaterial = new WheelView(getActivity());

        ArrayList<String> materialList = new ArrayList<>((Arrays.asList(getResources().getStringArray(R.array.lash_material))));
        wvMaterial.setWVValue(materialList, false, 18, WheelView.CENTER);
        wvMaterial.setInitPosition(0);

        llMaterialWheel.addView(wvMaterial, layoutParams);
        llMaterialWheel.setGravity(Gravity.CENTER);

        tvMaterial.setText(wvMaterial.getSelectedItemText());
        wvMaterial.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                String material = view.getSelectedItemText();
                tvMaterial.setText(material);
            }
        });

        if (!mIsNewRecord) {
            wvMaterial.setInitPosition(materialList.indexOf(mCustomInfo.getMaterial()));
            tvMaterial.setText(mCustomInfo.getMaterial());
        }

    }

    private void setStyleWheelView(View view){
        LinearLayout styleLayout = (LinearLayout)view.findViewById(R.id.styleLayout);
        styleLayout.setOnClickListener(mClickListener);

        llStyleWheel = (LinearLayout)view.findViewById(R.id.styleWheel);
        tvLashStyle = (TextView)view.findViewById(R.id.styleValue);
        wvStyle = new WheelView(getActivity());

        ArrayList<String> styleList = new ArrayList<>((Arrays.asList(getResources().getStringArray(R.array.lash_style))));
        wvStyle.setWVValue(styleList, false, 18, WheelView.CENTER);
        wvStyle.setInitPosition(0);

        llStyleWheel.addView(wvStyle, layoutParams);
        llStyleWheel.setGravity(Gravity.CENTER);

        tvLashStyle.setText(wvStyle.getSelectedItemText());
        wvStyle.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                String style = view.getSelectedItemText();
                tvLashStyle.setText(style);
            }
        });

        if (!mIsNewRecord) {
            wvStyle.setInitPosition(styleList.indexOf(mCustomInfo.getStyle()));
            tvLashStyle.setText(mCustomInfo.getStyle());
        }

    }

    private void setThickWheelView(View view){
        LinearLayout thickLayout = (LinearLayout)view.findViewById(R.id.thickLayout);
        thickLayout.setOnClickListener(mClickListener);

        llThickWheel = (LinearLayout)view.findViewById(R.id.thickWheel);
        tvThick = (TextView)view.findViewById(R.id.thickValue);
        wvThick = new WheelView(getActivity());

        ArrayList<String> thickList = new ArrayList<>((Arrays.asList(getResources().getStringArray(R.array.thick))));
        wvThick.setWVValue(thickList, false, 18, WheelView.CENTER);
        wvThick.setInitPosition(0);

        llThickWheel.addView(wvThick, layoutParams);
        llThickWheel.setGravity(Gravity.CENTER);

        tvThick.setText(wvThick.getSelectedItemText());
        wvThick.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                String thick = view.getSelectedItemText();
                tvThick.setText(thick);
            }
        });

        if (!mIsNewRecord) {
            wvThick.setInitPosition(thickList.indexOf(mCustomInfo.getThick()));
            tvThick.setText(mCustomInfo.getThick());
        }
    }

    private void setCurlWheelView(View view){
        LinearLayout curlLayout = (LinearLayout)view.findViewById(R.id.curlLayout);
        curlLayout.setOnClickListener(mClickListener);

        llCurlWheel = (LinearLayout)view.findViewById(R.id.curlWheel);
        tvCurl = (TextView)view.findViewById(R.id.curlValue);
        wvCurl = new WheelView(getActivity());

        ArrayList<String> curlList = new ArrayList<>((Arrays.asList(getResources().getStringArray(R.array.curl))));
        wvCurl.setWVValue(curlList, false, 18, WheelView.CENTER);
        wvCurl.setInitPosition(0);

        llCurlWheel.addView(wvCurl, layoutParams);
        llCurlWheel.setGravity(Gravity.CENTER);

        tvCurl.setText(wvCurl.getSelectedItemText());
        wvCurl.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                String curl = view.getSelectedItemText();
                tvCurl.setText(curl);
            }
        });

        if (!mIsNewRecord) {
            wvCurl.setInitPosition(curlList.indexOf(mCustomInfo.getCurl()));
            tvCurl.setText(mCustomInfo.getCurl());
        }

    }

    private void setLengthWheelView(View view){
        LinearLayout lengthLayout = (LinearLayout)view.findViewById(R.id.lengthLayout);
        lengthLayout.setOnClickListener(mClickListener);

        llLengthWheel = (LinearLayout)view.findViewById(R.id.lengthWheel);
        tvLength = (TextView)view.findViewById(R.id.lengthValue);
        wvLength = new WheelView(getActivity());

        ArrayList<String> lengthList = new ArrayList<>((Arrays.asList(getResources().getStringArray(R.array.length))));
        wvLength.setWVValue(lengthList, false, 18, WheelView.CENTER);
        wvLength.setInitPosition(0);

        llLengthWheel.addView(wvLength, layoutParams);
        llLengthWheel.setGravity(Gravity.CENTER);

        tvLength.setText(wvLength.getSelectedItemText());
        wvLength.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView view, int index) {
                String length = view.getSelectedItemText();
                tvLength.setText(length);
            }
        });

        if (!mIsNewRecord) {
            wvLength.setInitPosition(lengthList.indexOf(mCustomInfo.getLength()));
            tvLength.setText(mCustomInfo.getLength());
        }

    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dateLayout:
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
                    eyesWheelStatus = (!eyesWheelStatus);

                    break;

                case R.id.materialLayout:
                    switchWheelStatus(llMaterialWheel, wvMaterial, tvMaterial, materialWheelStatus);
                    materialWheelStatus = (!materialWheelStatus);

                    break;

                case R.id.styleLayout:
                    switchWheelStatus(llStyleWheel, wvStyle, tvLashStyle, styleWheelStatus);
                    styleWheelStatus = (!styleWheelStatus);

                    break;

                case R.id.thickLayout:
                    switchWheelStatus(llThickWheel, wvThick, tvThick, thickWheelStatus);
                    thickWheelStatus = (!thickWheelStatus);

                    break;

                case R.id.curlLayout:
                    switchWheelStatus(llCurlWheel, wvCurl, tvCurl, curlWheelStatus);
                    curlWheelStatus = (!curlWheelStatus);

                    break;

                case R.id.lengthLayout:
                    switchWheelStatus(llLengthWheel, wvLength, tvLength, lengthWheelStatus);
                    lengthWheelStatus = (!lengthWheelStatus);

                    break;

                case R.id.photoLayout:
                    PhotoFragment photoFragment = new PhotoFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.mainActivityContent, photoFragment);
                    fragmentTransaction.commit();
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener mBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back_btn:
                    backToMainFragment();
                    break;
                case R.id.save_textview:
                    saveDataToDB();

                    if (CustomDetailFragment.recyclerView != null){
                        CustomDetailListAdapter adapter = (CustomDetailListAdapter) CustomDetailFragment.recyclerView.getAdapter();
                        adapter.addItem(adapter.getItemCount(), mCustomInfo);
                    }

                    backToMainFragment();
                    break;
                default:
                    break;
            }
        }
    };

    private void saveDataToDB(){
        mCustomInfo.setName(mNameEditText.getText().toString());
        mCustomInfo.setPhone(mPhoneEditText.getText().toString());
        mCustomInfo.setMaterial(tvMaterial.getText().toString());
        mCustomInfo.setDate(tvDate.getText().toString());
        mCustomInfo.setEyesType(tvEyesType.getText().toString());
        mCustomInfo.setThick(tvThick.getText().toString());
        mCustomInfo.setCurl(tvCurl.getText().toString());
        mCustomInfo.setLength(tvLength.getText().toString());
        mCustomInfo.setStyle(tvLashStyle.getText().toString());

        System.out.println(mCustomInfo.getStyle());

        if (mIsNewRecord)
            CustomerTable.addNewRecord(mCustomInfo);
        else
            CustomerTable.editRecord(mCustomInfo);

    }

    private void backToMainFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void switchWheelStatus(View view, WheelView wheelView, TextView tvView, boolean status){
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
