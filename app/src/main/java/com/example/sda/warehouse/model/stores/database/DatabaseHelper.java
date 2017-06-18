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

    public static final String CATEGORY_TABLE_NAME = "Categories";
    public static final String ID_COL = "_id";
    public static final String NAME_COL = "name";
    public static final String PARENT_ID_COL = "id_parent";

    public static final String PROVIDERS_TABLE_NAME = "Providers";
    public static final String TELEPHONE_COL = "telephone";
    public static final String ADDRESS_COL = "address";

    public static final String ARTICLES_TABLE_NAME = "Articles";
    public static final String PRICE_COL = "price";
    public static final String CATEGORY_ID_COL = "category_id";
    public static final String PROVIDER_ID_COL = "provider_id";

    public static final String SORT_DESC = "DESC";
    public static final String SORT_ASC = "ASC";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + CATEGORY_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT, "
            + PARENT_ID_COL + " INTEGER NULL"
            + ");";

    private static final String CREATE_TABLE_PROVIDERS = "CREATE TABLE " + PROVIDERS_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT, "
            + TELEPHONE_COL + " TEXT, "
            + ADDRESS_COL + " TEXT "
            + ");";

    private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + ARTICLES_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT, "
            + PRICE_COL + " FLOAT, "
            + CATEGORY_ID_COL + " INTEGER, "
            + PROVIDER_ID_COL + " INTEGER "
            + ");";


    private DatabaseHelper() {
        super(MyApplication.getContext(), DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;

        databaseHelper = this;

        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORIES);
        sqLiteDatabase.execSQL(CREATE_TABLE_PROVIDERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_ARTICLES);
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
