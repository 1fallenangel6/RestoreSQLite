package com.example.a15747.restoresqlite.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15747 on 2017/11/24.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "sample_database";
    public static final int DATABASE_VERSION = 1;
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create all tables
        sqLiteDatabase.execSQL(UserDAO.getCreateTable(mContext));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // drop all tables
            sqLiteDatabase.execSQL(UserDAO.getDropTable(mContext));
            //re-create all tables
            onCreate(sqLiteDatabase);
        }
    }
}