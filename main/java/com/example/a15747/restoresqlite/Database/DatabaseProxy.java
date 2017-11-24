package com.example.a15747.restoresqlite.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 15747 on 2017/11/24.
 */

public class DatabaseProxy {

    private SQLiteDatabase mDatabase;
    private Context mContext;

    DatabaseProxy(SQLiteDatabase database, Context context) {
        mDatabase = database;
        mContext = context;
    }

    void close() {
        mDatabase.close();
    }

    public Cursor rawQuery(int sql) {
        return rawQuery(sql, null);
    }

    public Cursor rawQuery(int sql, String[] selectionArgs) {
        return mDatabase.rawQuery(mContext.getString(sql), selectionArgs);
    }

    public void execSQL(int sql) {
        mDatabase.execSQL(mContext.getString(sql));
    }

    public void execSQL(int sql, String[] bindArgs) {
        mDatabase.execSQL(mContext.getString(sql), bindArgs);
    }
}
