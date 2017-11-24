package com.example.a15747.restoresqlite.Database;

import android.content.Context;
import android.database.Cursor;

import com.example.a15747.restoresqlite.R;

import java.util.List;

/**
 * Created by 15747 on 2017/11/24.
 */

public class UserDAO extends AbstractDAO<User> {

    interface Table {

        String COLUMN_ID = "id";
        String COLUMN_NAME = "name";
        String COLUMN_AGE = "age";
    }

    public static String getCreateTable(Context context) {
        return context.getString(R.string.create_table_user);
    }

    public static String getDropTable(Context context) {
        return context.getString(R.string.drop_table_users);
    }

    public void updateNameByAge(String name, int age) {
        DatabaseProxy databaseProxy = openDatabase();
        String[] bindArgs = {
                name,
                String.valueOf(age)
        };
        databaseProxy.execSQL(R.string.update_user_name_by_age, bindArgs);
        closeDatabase();
    }

    public List<User> selectByAge(int age) {
        DatabaseProxy databaseProxy = openDatabase();
        String[] selectionArgs = {
                String.valueOf(age)
        };
        Cursor cursor = databaseProxy.rawQuery(R.string.select_users_by_age, selectionArgs);

        List<User> dataList = manageCursor(cursor);

        closeCursor(cursor);
        closeDatabase();

        return dataList;
    }

    public List<User> selectAll() {
        DatabaseProxy databaseProxy = openDatabase();
        Cursor cursor = databaseProxy.rawQuery(R.string.select_all_users);

        List<User> dataList = manageCursor(cursor);

        closeCursor(cursor);
        closeDatabase();

        return dataList;
    }


    @Override
    protected User cursorToData(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Table.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(Table.COLUMN_NAME);
        int ageIndex = cursor.getColumnIndex(Table.COLUMN_AGE);

        User user = new User();
        user.setId(cursor.getLong(idIndex));
        user.setAge(cursor.getInt(ageIndex));
        user.setName(cursor.getString(nameIndex));

        return user;
    }

    public void deleteAll() {
        DatabaseProxy databaseProxy = openDatabase();
        databaseProxy.execSQL(R.string.delete_all_users);
        closeDatabase();
    }

    public void insert(List<User> userList) {
        DatabaseProxy databaseProxy = openDatabase();

        for (User user : userList) {
            String[] bindArgs = {
                    user.getName(),
                    String.valueOf(user.getAge())
            };
            databaseProxy.execSQL(R.string.insert_user, bindArgs);
        }

        closeDatabase();
    }

    public void insert(User user) {
        DatabaseProxy databaseProxy = openDatabase();
        String[] bindArgs = {
                user.getName(),
                String.valueOf(user.getAge())
        };
        databaseProxy.execSQL(R.string.insert_user, bindArgs);
        closeDatabase();
    }
}
