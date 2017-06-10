package com.example.sda.warehouse.model.datasource.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sda.warehouse.model.datasource.CategoryDataSource;
import com.example.sda.warehouse.utils.MyApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseHelper databaseHelper;

    public DatabaseHelper() {
        super(MyApplication.getContext(), CategoryDataSource.CATEGORY_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        createCategoryTable();
        databaseHelper = this;
    }

    public static DatabaseHelper getInstance() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper();
            return databaseHelper;
        } else {
            return databaseHelper;
        }

    }

    private void createCategoryTable() {
        String sqlCommand = "CREATE TABLE " + CategoryDataSource.CATEGORY_TABLE_NAME + " (" + CategoryDataSource.ID_COL
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CategoryDataSource.NAME_COL +
                " TEXT NOT NULL, " + CategoryDataSource.ID_PARENT_COL + " INTEGER);";
        logDebug(sqlCommand);
        sqLiteDatabase.execSQL(sqlCommand);
    }

    private void createProviderTable() {
        String sqlCommand = "CREATE TABLE ";
        logDebug(sqlCommand);
        sqLiteDatabase.execSQL(sqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
