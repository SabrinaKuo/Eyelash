package com.nanadeer.eyelash.fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.adapter.AlbumListAdapter;
import com.nanadeer.eyelash.database.PhotoInfo;
import com.nanadeer.eyelash.parameter.EyelashParameter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * eyelash photos fragment
 */
public class PhotoFragment extends Fragment {

    private ImageView mImg;
    private DisplayMetrics mPhone;
    private ArrayList<PhotoInfo> mPhotoDataList = new ArrayList<>();

    public PhotoFragment() {
        // fake data
        mPhotoDataList.add(new PhotoInfo("test", "testing"));
        mPhotoDataList.add(new PhotoInfo("test2", "cool"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get phone display metrics
        mPhone = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mPhone);

        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ImageButton backBtn = (ImageButton) view.findViewById(R.id.photo_back_btn);
        ImageButton captureBtn = (ImageButton) view.findViewById(R.id.camera_btn);

        backBtn.setOnClickListener(mBarClickListener);
        captureBtn.setOnClickListener(mBarClickListener);

        RecyclerView photoRecycler = (RecyclerView) view.findViewById(R.id.photo_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        photoRecycler.setLayoutManager(layoutManager);

        AlbumListAdapter adapter = new AlbumListAdapter(getActivity(), mPhotoDataList);
        photoRecycler.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        File tmpFile = new File(Environment.getExternalStorageDirectory(), "image.jpg");
//        Uri outputFileUri = Uri.fromFile(tmpFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//        startActivityForResult(intent, EyelashParameter.PICK_FROM_CAMERA);

        ContentValues value = new ContentValues();
        value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri uri = getActivity().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, value);
        String path = uri.getPath();

        if (!path.isEmpty()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, path);
            startActivityForResult(intent, EyelashParameter.PICK_FROM_CAMERA);
        }
    }

    private View.OnClickListener mBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.photo_back_btn:
                    getActivity().getSupportFragmentManager().popBackStack();
                    break;
                case R.id.camera_btn:
//                    openCamera();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((resultCode == EyelashParameter.PICK_FROM_CAMERA) && data != null) {
            // get photo path
            Uri uri = data.getData();
            ContentResolver cr = getActivity().getContentResolver();

            try {
                // load photo, type is bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                if (bitmap.getWidth() > bitmap.getHeight())
                    ScalePic(bitmap, mPhone.heightPixels);
                else
                    ScalePic(bitmap, mPhone.widthPixels);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ScalePic(Bitmap bitmap, int phonePixels){
        // default scale
        float mScale;

        // if pic width > phone width, scaling pic. otherwise, pic directly inputted ImageView.
        if(bitmap.getWidth() > phonePixels){
            mScale = (float)phonePixels / (float)bitmap.getWidth();

            Matrix mMat = new Matrix();
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mMat, false);
            mImg.setImageBitmap(mScaleBitmap);

        }else{
            mImg.setImageBitmap(bitmap);
        }
    }
}
