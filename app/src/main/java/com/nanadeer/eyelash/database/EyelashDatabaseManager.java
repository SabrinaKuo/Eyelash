package com.nanadeer.eyelash.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sabrina Kuo on 2016/6/17.
 */
public class EyelashDatabaseManager {
    private Integer mOpenCounter = 0;

    private static EyelashDatabaseManager instance = null;
    private static SQLiteOpenHelper mHelper = null;
    private SQLiteDatabase mDatabase = null;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {

        if (instance == null) {
            instance = new EyelashDatabaseManager();
            mHelper = helper;
        }
    }

    public static synchronized EyelashDatabaseManager getInstance(){
        if (instance == null){
            throw new IllegalStateException(EyelashDatabaseManager.class.getSimpleName() +
            " is not initialized, call initializeInstance first");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter += 1;
        if(mOpenCounter == 1) {
            // open new database
            mDatabase = mHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter -= 1;
        if (mOpenCounter == 0){
            // close database
            mDatabase.close();
            mDatabase = null;
        }
    }

    public synchronized void closeHelper() {
        if(mHelper != null){
            mHelper.close();

            mHelper = null;
            instance = null;
        }
    }
}
