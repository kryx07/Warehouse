package com.example.sda.warehouse.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.sda.warehouse.model.Category;
import com.example.sda.warehouse.model.datasource.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class CategoryDataSource implements IDataProvider<Category> {

    private DatabaseHelper databaseHelper;

    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String ID_COL = "_id";
    public static final String NAME_COL = "name";
    public static final String ID_PARENT_COL = "id_parent";

    private static final long EMPTY = 0;


    public CategoryDataSource() {

        this.databaseHelper = DatabaseHelper.getInstance();

    }

    @Override
    public List<Category> getAll() {

        Cursor cursor = databaseHelper.getReadableDatabase().query(
                CATEGORY_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, ID_PARENT_COL},
                null, null, null, null, null, null);
        return getCategoriesFromCursor(cursor);
    }


    @Override
    public List<Category> getAll(String column, String order) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(CATEGORY_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, ID_PARENT_COL},
                null, null, null, null,
                column + " " + order,
                null);

        return getCategoriesFromCursor(cursor);
    }

    @Override
    public Category getById(long id) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(CATEGORY_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, ID_PARENT_COL}, ID_COL + " = " + id, null, null, null, null);

        return getCategoryFromCursor(cursor);

    }

    private List<Category> getCategoriesFromCursor(Cursor cursor) {
        cursor.moveToFirst();
        List<Category> categories = new ArrayList<>();
        long id_parent;
        while (!cursor.isAfterLast()) {
            Category categoryRecord = new Category();
            categoryRecord.setId(cursor.getLong(cursor.getColumnIndex(ID_COL)));
            categoryRecord.setName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            if (cursor.isNull(cursor.getColumnIndex(ID_PARENT_COL))) {
                categoryRecord.setParentId(EMPTY);
            } else {
                id_parent = cursor.getLong(cursor.getColumnIndex(ID_PARENT_COL));
                categoryRecord.setParentId(id_parent);
            }
            categories.add(categoryRecord);
            cursor.moveToNext();
        }
        cursor.close();
        databaseHelper.close();

        logDebug("Category DB returns: " + categories);
        return categories;
    }

    private Category getCategoryFromCursor(Cursor cursor) {
        cursor.moveToFirst();
        Category category = new Category(
                cursor.getLong(cursor.getColumnIndex(ID_COL)),
                cursor.getString(cursor.getColumnIndex(NAME_COL)),
                cursor.getLong(cursor.getColumnIndex(ID_PARENT_COL)));

        cursor.close();
        databaseHelper.close();

        logDebug("Category DB returns: " + category);
        return category;
    }


    @Override
    public void add(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, category.getName());

        try {
            contentValues.put(ID_PARENT_COL, category.getParentId());
        } catch (NullPointerException e) {
            contentValues.put(ID_PARENT_COL, 0);
        }

        long recordId = this.databaseHelper
                .getWritableDatabase()
                .insert(CATEGORY_TABLE_NAME, null, contentValues);
        logDebug("New record added under id: " + recordId);
        databaseHelper.close();

    }

    @Override
    public void remove(long id) {
        logDebug("Removing category id: " + id);
        databaseHelper.getWritableDatabase().delete(CATEGORY_TABLE_NAME, ID_COL + " = " + id, null);
        databaseHelper.close();
    }

    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
