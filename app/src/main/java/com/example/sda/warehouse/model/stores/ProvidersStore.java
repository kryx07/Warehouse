package com.example.sda.warehouse.model.stores;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


class ProvidersStore implements IStore<Provider> {

    private DatabaseHelper databaseHelper;

    ProvidersStore() {
        this.databaseHelper = DatabaseHelper.getInstance();
    }

    @Override
    public List<Provider> getAll() {
        return getAll(DatabaseHelper.ID_COL, DatabaseHelper.SORT_DESC);
    }

    @Override
    public List<Provider> getWithoutId(long withoutId) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.PROVIDERS_TABLE_NAME,
                new String[]{DatabaseHelper.ID_COL, DatabaseHelper.NAME_COL, DatabaseHelper.TELEPHONE_COL, DatabaseHelper.ADDRESS_COL},
                DatabaseHelper.ID_COL + "!=" + withoutId, null, null, null, null, null);

        return getAllFromCursor(cursor);
    }


    @Override
    public List<Provider> getAll(String column, String order) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.PROVIDERS_TABLE_NAME,
                new String[]{DatabaseHelper.ID_COL, DatabaseHelper.NAME_COL, DatabaseHelper.TELEPHONE_COL, DatabaseHelper.ADDRESS_COL},
                null, null, null, null,
                column + " " + order,
                null);

        return getAllFromCursor(cursor);
    }

    @Override
    public Provider getById(long id) {
        if (id == 0) {
            return null;
        }
        Cursor cursor = databaseHelper.getReadableDatabase().query(
                DatabaseHelper.PROVIDERS_TABLE_NAME,
                new String[]{DatabaseHelper.ID_COL, DatabaseHelper.NAME_COL, DatabaseHelper.TELEPHONE_COL, DatabaseHelper.ADDRESS_COL},
                DatabaseHelper.ID_COL + " = " + id, null, null, null, null);
        return cursor.getCount() == 0 ? null : getAllFromCursor(cursor).get(0);
    }

    @Override
    public Provider getEmpty() {
        return new Provider(0, "Empty", "", "");
    }

    private List<Provider> getAllFromCursor(Cursor cursor) {

        cursor.moveToFirst();
        List<Provider> providers = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            Provider providerRecord = new Provider();
            providerRecord.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID_COL)));
            providerRecord.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COL)));
            providerRecord.setTel(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TELEPHONE_COL)));
            providerRecord.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ADDRESS_COL)));
            providers.add(providerRecord);
            cursor.moveToNext();
        }
        cursor.close();
        databaseHelper.close();

        logDebug("Providers DB returns: " + providers);
        return providers;
    }

    @Override
    public void add(Provider provider) {

        logDebug("Adding " + provider);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_COL, provider.getName());
        contentValues.put(DatabaseHelper.TELEPHONE_COL, provider.getTel());
        contentValues.put(DatabaseHelper.ADDRESS_COL, provider.getAddress());

        long recordId = this.databaseHelper
                .getWritableDatabase()
                .insert(DatabaseHelper.PROVIDERS_TABLE_NAME, null, contentValues);
        logDebug("New record added under id: " + recordId);
        this.databaseHelper.close();

    }

    @Override
    public void update(Provider provider) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID_COL, provider.getId());
        contentValues.put(DatabaseHelper.NAME_COL, provider.getName());
        contentValues.put(DatabaseHelper.TELEPHONE_COL, provider.getTel());
        contentValues.put(DatabaseHelper.ADDRESS_COL, provider.getAddress());

        logDebug(contentValues.toString());

        this.databaseHelper
                .getWritableDatabase()
                .update(DatabaseHelper.PROVIDERS_TABLE_NAME, contentValues, DatabaseHelper.ID_COL + "=" + provider.getId(), null);
        logDebug("Record updated");
        this.databaseHelper.close();
    }

    @Override
    public void remove(long id) {
        logDebug("Removing category id: " + id);
        databaseHelper.getWritableDatabase().delete(DatabaseHelper.PROVIDERS_TABLE_NAME, DatabaseHelper.ID_COL + " = " + id, null);
        databaseHelper.close();
    }


    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
