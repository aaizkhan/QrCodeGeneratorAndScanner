package com.encoderbytes.qrcodeScannerAndGenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager2 {


    private DatabaseHelper2 dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager2(Context c) {
        context = c;
    }

    public DBManager2 open() throws SQLException {
        dbHelper = new DatabaseHelper2(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String title, String desc, String type ) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper2.TITLE, title);
        contentValue.put(DatabaseHelper2.DESC, desc);
        contentValue.put(DatabaseHelper2.TYPE, type);
        database.insert(DatabaseHelper2.TABLE_NAME, null, contentValue);
    }


    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper2._ID,DatabaseHelper2.TITLE, DatabaseHelper2.DESC, DatabaseHelper2.TYPE};
        Cursor cursor = database.query(DatabaseHelper2.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public int update(long _id,String title, String desc, String type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper2.TITLE,title);
        contentValues.put(DatabaseHelper2.DESC, desc);
        contentValues.put(DatabaseHelper2.TYPE, type);
        int i = database.update(DatabaseHelper2.TABLE_NAME, contentValues, DatabaseHelper2._ID + " = " + _id, null);
        return i;
    }


    public void delete(long _id) {
        database.delete(DatabaseHelper2.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
