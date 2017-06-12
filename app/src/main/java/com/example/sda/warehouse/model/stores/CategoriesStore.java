package com.example.sda.warehouse.model.stores;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.sda.warehouse.model.Category;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class CategoriesStore implements IStore<Category> {

    private DatabaseHelper databaseHelper;

    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String ID_COL = "_id";
    public static final String NAME_COL = "name";
    public static final String ID_PARENT_COL = "id_parent";

    public CategoriesStore() {
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
        if (id == 0) {
            return null;
        }
        Cursor cursor = databaseHelper.getReadableDatabase().query(
                CATEGORY_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, ID_PARENT_COL},
                ID_COL + " = " + id, null, null, null, null);
        return cursor.getCount() == 0 ? null : getCategoriesFromCursor(cursor).get(0);
    }

    private List<Category> getCategoriesFromCursor(Cursor cursor) {
       /* if (cursor.getCount() == 0) {
            return null;
        }*/
        cursor.moveToFirst();
        List<Category> categories = new ArrayList<>();
        long id_parent;
        while (!cursor.isAfterLast()) {
            Category categoryRecord = new Category();
            categoryRecord.setId(cursor.getLong(cursor.getColumnIndex(ID_COL)));
            categoryRecord.setName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            if (cursor.isNull(cursor.getColumnIndex(ID_PARENT_COL))) {
                categoryRecord.setParentCategory(null);
            } else {
                id_parent = cursor.getLong(cursor.getColumnIndex(ID_PARENT_COL));
                categoryRecord.setParentCategory(getById(id_parent));
            }
            categories.add(categoryRecord);
            cursor.moveToNext();
        }
        cursor.close();
        databaseHelper.close();

        logDebug("Category DB returns: " + categories);
        return categories;
    }

    /*private Category getCategoryFromCursor(Cursor cursor) {
        cursor.moveToFirst();

        int parentColumnIndex = cursor.getColumnIndex(ID_PARENT_COL);
        Category parentCategory;
        if (cursor.getCount() == 0) {
            return null;
        }
        if (cursor.isNull(parentColumnIndex)) {
            parentCategory = null;
        } else {
            parentCategory = getById(cursor.getLong(parentColumnIndex));
        }
        Category category = new Category(
                cursor.getLong(cursor.getColumnIndex(ID_COL)),
                cursor.getString(cursor.getColumnIndex(NAME_COL)),
                parentCategory);

        cursor.close();
        databaseHelper.close();

        logDebug("Category DB returns: " + category);
        return category;
    }
*/
    @Override
    public void add(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, category.getName());

        try {
            contentValues.put(ID_PARENT_COL, category.getParentCategory().getId());
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
