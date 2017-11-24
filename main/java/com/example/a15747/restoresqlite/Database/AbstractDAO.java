package com.example.a15747.restoresqlite.Database;

import android.database.Cursor;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15747 on 2017/11/24.
 */

public abstract class AbstractDAO<T> {

    protected DatabaseProxy openDatabase() {
        return DatabaseManager.getInstance().openDatabase();
    }

    protected void closeDatabase() {
        DatabaseManager.getInstance().closeDatabase();
    }

    protected void closeCursor(@Nullable Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }


    protected List<T> manageCursor(Cursor cursor) {
        List<T> dataList = new ArrayList<T>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                T user = cursorToData(cursor);
                dataList.add(user);
                cursor.moveToNext();
            }
        }
        return dataList;
    }

    protected abstract T cursorToData(Cursor cursor);
}
