package com.example.h.azkary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by H on 10/08/2016.
 */

public class DBAdapter {

    // Fields of Table

    static final String ID = "_id";
    static final String NAME = "name";
    static final String COUNT = "count";

    static final String FAV_BOOLEAN = "fav_bool";
    static final String MYAZKAR_BOOLEAN = "myazkar_bool";
    static final String TAG = "DBAdapter";

    // DataBase Attribute
    static final String DATABASE_NAME = "mydatabase";
    static final String DATABASE_TABLE = "azkar";
    static final int DATABASE_VERSION = 8;

    //it is used to create a table
    static final String DATABASE_CREATE = "CREATE TABLE " + "azkar " + "(_id integer primary key autoincrement,"
            + "name text not null unique, count text, fav_bool integer not null, myazkar_bool integer not null);";

    //it is used by onUpgrade Function
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;

    Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {

        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException {

        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        DBHelper.close();
    }

    public long insertRow(String name, int fav_bool, int myazkar_bool) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBAdapter.NAME, name);
        contentValues.put(DBAdapter.COUNT, "0");
        contentValues.put(DBAdapter.FAV_BOOLEAN, fav_bool);
        contentValues.put(DBAdapter.MYAZKAR_BOOLEAN, myazkar_bool);

        return db.insert(DATABASE_TABLE, null, contentValues);
    }

    public boolean deleteRow(String name) {

        Cursor cursor = getRow(name);

        long id = cursor.getLong(0);

        String where = DBAdapter.ID + " = " + id;

        // long[] whereArgs = {id};

        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public boolean updateRow(String name, String count) {

        ContentValues contentValues = new ContentValues();

        String where = DBAdapter.NAME + " = ?";

        String[] whereArgs = {name};
        contentValues.put(DBAdapter.COUNT, count);

        return db.update(DATABASE_TABLE, contentValues, where, whereArgs) != 0;
    }

    public boolean updateRow(String name, int fav_bool) {

        ContentValues contentValues = new ContentValues();

        String where = DBAdapter.NAME + " = ?";

        String[] whereArgs = {name};
        contentValues.put(DBAdapter.FAV_BOOLEAN, fav_bool);

        return db.update(DATABASE_TABLE, contentValues, where, whereArgs) != 0;
    }


    public boolean editZekrName (String oldName, String newName) {

        ContentValues contentValues = new ContentValues();

        String where = DBAdapter.NAME + " = ?";

        String[] whereArgs = {oldName};
        contentValues.put(DBAdapter.NAME, newName);

        return db.update(DATABASE_TABLE, contentValues, where, whereArgs) != 0;
    }

    public Cursor getRow(String name) {

        String where = DBAdapter.NAME + " = ?";

        String [] whereArgs = new String[] {name};

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{ID, FAV_BOOLEAN}, where, whereArgs, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }


    public Cursor getAllAzkar() {

        String where = DBAdapter.MYAZKAR_BOOLEAN + " = " + "0";

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{ID, NAME, COUNT}, where, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getAllFavAzkar() {

        Cursor cursor;

        String where = DBAdapter.FAV_BOOLEAN + " = " + "1";

        cursor = db.query(DATABASE_TABLE, new String[]{ID, DBAdapter.NAME, DBAdapter.COUNT}, where, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getAllMyAzkar() {

        Cursor cursor;

        String where = DBAdapter.MYAZKAR_BOOLEAN + " = " + "1";

        cursor = db.query(DATABASE_TABLE, new String[]{ID, DBAdapter.NAME, DBAdapter.COUNT}, where, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        Context context;

        public DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            //Toast.makeText(context, "Constructor...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {

                e.printStackTrace();

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion + "to"
                    + newVersion + ", which will destroy all old data");

            db.execSQL(DROP_TABLE);

            onCreate(db);
        }
    }


}