package com.nanadeer.eyelash.database;

import android.content.Context;

/**
 * Created by Sabrina Kuo on 2016/6/6.
 */
public class PhotoInfo {
    private String describe;
    private String photoName;

    public PhotoInfo(){}

    public PhotoInfo(String photoName, String describe){
        this.photoName = photoName;
        this.describe = describe;
    }

    public int getImageResourceId(Context context){

        return context.getResources().getIdentifier(this.photoName, "drawable", context.getPackageName());
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
