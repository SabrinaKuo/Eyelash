package com.nanadeer.eyelash.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by sabrinakuo on 2016/6/17.
 */
public class CustomerTable {
    private static final String CUSTOMER_TABLE = "customerTable";

    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String DATE = "date";
    private static final String EYES_TYPE = "eyes_type";
    private static final String STYLE = "style";
    private static final String MATERIAL = "material";
    private static final String CURL = "curl";
    private static final String LENGTH = "length";
    private static final String PHOTOS= "photos";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_TABLE + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + PHONE + " TEXT, "
            + DATE + " TEXT, "
            + EYES_TYPE + " TEXT, "
            + STYLE + " TEXT, "
            + MATERIAL + " TEXT, "
            + CURL + " TEXT, "
            + LENGTH + " TEXT, "
            + PHOTOS + " TEXT)";

    private static SQLiteDatabase openDatabase(){
        return EyelashDatabaseManager.getInstance().openDatabase();
    }

    private static void closeDatabase() {
        EyelashDatabaseManager.getInstance().closeDatabase();
    }

    public static ArrayList<CustomInfo> getAllCustoms(){
        SQLiteDatabase db = openDatabase();

        Cursor cursor = db.query(true, CUSTOMER_TABLE, null, null, null, PHONE, null, NAME + " ASC", null);

        ArrayList<CustomInfo> infoList = new ArrayList<>();
        while (cursor.moveToNext()){
            infoList.add(setCustomInfo(cursor));
        }

        cursor.close();
        closeDatabase();
        return infoList;
    }

    public static ArrayList<CustomInfo> getDetectCustom(String phoneNumber){
        SQLiteDatabase db = openDatabase();

        Cursor cursor = db.query(CUSTOMER_TABLE, null, PHONE + " = ?", new String[]{phoneNumber}, null, null, DATE + " DESC");

        ArrayList<CustomInfo> customInfos = new ArrayList<>();
        while (cursor.moveToNext()){
            customInfos.add(setCustomInfo(cursor));
        }

        cursor.close();
        closeDatabase();

        return customInfos;
    }


    public static void addNewRecord(CustomInfo customInfo){
        SQLiteDatabase db = openDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME, customInfo.getName());
        cv.put(PHONE, customInfo.getPhone());
        cv.put(DATE, customInfo.getDate());
        cv.put(EYES_TYPE, customInfo.getEyesType());
        cv.put(STYLE, customInfo.getStyle());
        cv.put(MATERIAL, customInfo.getMaterial());
        cv.put(CURL, customInfo.getCurl());
        cv.put(LENGTH, customInfo.getLength());
//        cv.put(PHOTOS, customInfo.getPhotos());

        long id = db.insert(CUSTOMER_TABLE, null, cv);
        customInfo.setId(id);

        closeDatabase();
    }

    public static void editRecord(CustomInfo customInfo){
        SQLiteDatabase db = openDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME, customInfo.getName());
        cv.put(PHONE, customInfo.getPhone());
        cv.put(DATE, customInfo.getDate());
        cv.put(EYES_TYPE, customInfo.getEyesType());
        cv.put(STYLE, customInfo.getStyle());
        cv.put(MATERIAL, customInfo.getMaterial());
        cv.put(CURL, customInfo.getCurl());
        cv.put(LENGTH, customInfo.getLength());
//        cv.put(PHOTOS, customInfo.getPhotos());

//        String where = "_id" + " = '" + String.valueOf(customInfo.getId()) + "'";
        db.update(CUSTOMER_TABLE, cv, "_id = ?", new String[]{String.valueOf(customInfo.getId())});

        closeDatabase();
    }

    public static void deleteRecord(CustomInfo customInfo){
        SQLiteDatabase db = openDatabase();

        db.delete(CUSTOMER_TABLE, "_id = ?", new String[]{String.valueOf(customInfo.getId())});

        closeDatabase();
    }

    public static CustomInfo getCustomRecordById(long id){
        SQLiteDatabase db = openDatabase();

        CustomInfo info = new CustomInfo();
        Cursor cursor = db.query(CUSTOMER_TABLE, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()){
            info = setCustomInfo(cursor);
        }

        cursor.close();
        closeDatabase();

        return info;
    }

    private static CustomInfo setCustomInfo(Cursor cursor){
        CustomInfo info = new CustomInfo();
        info.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        info.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
        info.setLength(cursor.getString(cursor.getColumnIndex(LENGTH)));
        info.setCurl(cursor.getString(cursor.getColumnIndex(CURL)));
        info.setEyesType(cursor.getString(cursor.getColumnIndex(EYES_TYPE)));
        info.setStyle(cursor.getString(cursor.getColumnIndex(STYLE)));
        info.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
        info.setMaterial(cursor.getString(cursor.getColumnIndex(MATERIAL)));
        info.setId(cursor.getLong(cursor.getColumnIndex("_id")));

        return info;
    }
}
