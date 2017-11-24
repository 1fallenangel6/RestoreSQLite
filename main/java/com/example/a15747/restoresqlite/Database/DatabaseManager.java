package com.example.a15747.restoresqlite.Database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by 15747 on 2017/11/24.
 */

public class DatabaseManager {
    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private static Context mContext;

    private DatabaseProxy mDatabaseProxy;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper, Context context) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
            mContext = context.getApplicationContext();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public DatabaseProxy openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabaseProxy = new DatabaseProxy(mDatabaseHelper.getWritableDatabase(), mContext);
        }

        Log.d("Database open counter: ",Integer.toString(mOpenCounter.get()));
        return mDatabaseProxy;
    }

    public void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabaseProxy.close();
        }

        Log.d("Database open counter: ", Integer.toString(mOpenCounter.get()));
    }
}
