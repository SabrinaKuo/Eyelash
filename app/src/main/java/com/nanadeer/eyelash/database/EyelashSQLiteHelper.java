package com.nanadeer.eyelash.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sabrina Kuo on 2016/6/17.
 */
public class EyelashSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "eyelash.db";
    private static final int DB_VERSION = 1;

    public EyelashSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CustomerTable.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
