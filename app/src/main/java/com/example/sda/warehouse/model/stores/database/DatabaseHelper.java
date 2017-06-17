package com.example.sda.warehouse.model.stores.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sda.warehouse.utils.MyApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseHelper databaseHelper;

    private static final String DATABASE_NAME = "warehouse.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String ID_COL = "_id";
    public static final String NAME_COL = "name";
    public static final String PARENT_ID_COL = "id_parent";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + CATEGORY_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT, "
            + PARENT_ID_COL + " INTEGER NULL"
            + ");";

    /*private static final String CREATE_TABLE_PROVIDERS = "CREATE TABLE " + TABLE_PROVIDERS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_TELEPHONE + " TEXT, "
            + COLUMN_ADDRESS + " TEXT "
            + ");";

    private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + TABLE_ARTICLES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_PRICE + " FLOAT, "
            + COLUMN_CATEGORY_ID + " INTEGER, "
            + COLUMN_PROVIDER_ID + " INTEGER "
            + ");";
*/

    private DatabaseHelper() {
        super(MyApplication.getContext(), DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;

        databaseHelper = this;

        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORIES);
    }

    public static DatabaseHelper getInstance() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper();
            return databaseHelper;
        } else {
            return databaseHelper;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
