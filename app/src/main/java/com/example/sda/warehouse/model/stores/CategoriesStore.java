package com.example.sda.warehouse.model.stores;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


class CategoriesStore implements IStore<Category> {

    private DatabaseHelper databaseHelper;

    CategoriesStore() {
        this.databaseHelper = DatabaseHelper.getInstance();
    }

    @Override
    public List<Category> getAll() {
        return getAll(DatabaseHelper.ID_COL, "DESC");
    }

    private boolean containsParent(Category category, Category parentCategory) {
        if (category.getParentCategory() == null) {
            return false;
        } else if (category.getParentCategory().equals(parentCategory)) {
            return true;
        } else {
            return containsParent(category.getParentCategory(), parentCategory);
        }
    }

    @Override
    public List<Category> getAll(String column, String order) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.CATEGORY_TABLE_NAME,
                new String[]{DatabaseHelper.ID_COL, DatabaseHelper.NAME_COL, DatabaseHelper.PARENT_ID_COL},
                null, null, null, null,
                column + " " + order,
                null);

        return getCategoriesFromCursor(cursor);
    }


    @Override
    public Category getById(long id) {
        if (id <= 0) {
            return null;
        }
        Cursor cursor = databaseHelper.getReadableDatabase().query(
                DatabaseHelper.CATEGORY_TABLE_NAME,
                new String[]{DatabaseHelper.ID_COL, DatabaseHelper.NAME_COL, DatabaseHelper.PARENT_ID_COL},
                DatabaseHelper.ID_COL + " = " + id, null, null, null, null);
        return cursor.getCount() == 0 ? null : getCategoriesFromCursor(cursor).get(0);
    }

    @Override
    public List<Category> getWithoutId(long withoutId) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.CATEGORY_TABLE_NAME,
                new String[]{DatabaseHelper.ID_COL, DatabaseHelper.NAME_COL, DatabaseHelper.PARENT_ID_COL},
                DatabaseHelper.ID_COL + "!=" + withoutId, null, null, null, null, null);

        List<Category> tmpCategoriesList = getCategoriesFromCursor(cursor);
        List<Category> categoriesList = new ArrayList<>();

        Category withoutCategory = getById(withoutId);
        for (int i = 0; i < tmpCategoriesList.size(); ++i) {
            if (!containsParent(tmpCategoriesList.get(i), withoutCategory)) {
                categoriesList.add(tmpCategoriesList.get(i));
            }
        }
        return categoriesList;
    }

    @Override
    public Category getEmpty() {
        return new Category(0, "Empty", null);
    }

    private List<Category> getCategoriesFromCursor(Cursor cursor) {

        cursor.moveToFirst();
        List<Category> categories = new ArrayList<>();
        long id_parent;
        while (!cursor.isAfterLast()) {
            Category categoryRecord = new Category();
            categoryRecord.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID_COL)));
            categoryRecord.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COL)));
            if (cursor.isNull(cursor.getColumnIndex(DatabaseHelper.PARENT_ID_COL))) {
                categoryRecord.setParentCategory(null);
            } else {
                id_parent = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.PARENT_ID_COL));
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

    @Override
    public void add(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_COL, category.getName());

        try {
            contentValues.put(DatabaseHelper.PARENT_ID_COL, category.getParentCategory().getId());
        } catch (NullPointerException e) {
            contentValues.put(DatabaseHelper.PARENT_ID_COL, 0);
        }

        long recordId = this.databaseHelper
                .getWritableDatabase()
                .insert(DatabaseHelper.CATEGORY_TABLE_NAME, null, contentValues);
        logDebug("New record added under id: " + recordId);
        this.databaseHelper.close();

    }

    @Override
    public void update(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID_COL, category.getId());
        contentValues.put(DatabaseHelper.NAME_COL, category.getName());
        contentValues.put(DatabaseHelper.PARENT_ID_COL, category.getParentCategory().getId());

        this.databaseHelper
                .getWritableDatabase()
                .update(DatabaseHelper.CATEGORY_TABLE_NAME, contentValues, DatabaseHelper.ID_COL + "=" + category.getId(), null);
        logDebug("Record updated");
        this.databaseHelper.close();
    }

    @Override
    public void remove(long id) {
        logDebug("Removing category id: " + id);
        databaseHelper.getWritableDatabase().delete(DatabaseHelper.CATEGORY_TABLE_NAME, DatabaseHelper.ID_COL + " = " + id, null);
        databaseHelper.close();
    }


    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
