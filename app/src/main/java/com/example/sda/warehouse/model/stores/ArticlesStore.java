package com.example.sda.warehouse.model.stores;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.beans.Article;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;
import com.example.sda.warehouse.utils.MyApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.sda.warehouse.model.stores.database.DatabaseHelper.*;

class ArticlesStore implements IStore<Article> {

    private DatabaseHelper databaseHelper;

    ArticlesStore() {
        this.databaseHelper = DatabaseHelper.getInstance();
    }


    @Override
    public List<Article> getAll() {
        return getAll(ID_COL, SORT_DESC);
    }

    @Override
    public List<Article> getAll(String column, String order) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(ARTICLES_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, PRICE_COL, CATEGORY_ID_COL, PROVIDER_ID_COL},
                null, null, null, null,
                column + " " + order,
                null);

        return getAllFromCursor(cursor);

    }

    private List<Article> getAllFromCursor(Cursor cursor) {

        cursor.moveToFirst();
        List<Article> articles = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            Article article = new Article();
            article.setId(cursor.getLong(cursor.getColumnIndex(ID_COL)));
            article.setName(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            article.setPrice(
                    new BigDecimal(cursor.getLong(cursor.getColumnIndex(PRICE_COL)))
                            .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_CEILING));
            article.setCategory(
                    StoreFactory.createCategoriesStore()
                            .getById(cursor.getLong(cursor.getColumnIndex(CATEGORY_ID_COL))));
            article.setProvider(
                    StoreFactory.createProvidersStore()
                            .getById(cursor.getLong(cursor.getColumnIndex(PROVIDER_ID_COL))));

            articles.add(article);
            cursor.moveToNext();
        }
        cursor.close();
        databaseHelper.close();

        logDebug("Providers DB returns: " + articles);
        return articles;
    }

    @Override
    public Article getById(long id) {
        if (id == 0) {
            return null;
        }
        Cursor cursor = databaseHelper.getReadableDatabase().query(
                ARTICLES_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, PRICE_COL, CATEGORY_ID_COL, PROVIDER_ID_COL},
                ID_COL + " = " + id, null, null, null, null);
        return cursor.getCount() == 0 ? null : getAllFromCursor(cursor).get(0);

    }

    @Override
    public List<Article> getWithoutId(long withoutId) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(
                ARTICLES_TABLE_NAME,
                new String[]{ID_COL, NAME_COL, PRICE_COL, CATEGORY_ID_COL, PROVIDER_ID_COL},
                DatabaseHelper.ID_COL + "!=" + withoutId, null, null, null, null, null);

        return getAllFromCursor(cursor);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Article getEmpty() {
        return new Article(0,
                MyApplication.getContext().getString(R.string.empty),
                BigDecimal.valueOf(0),
                null,
                null);
    }

    @Override
    public void add(Article item) {

        logDebug("Adding " + item);
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, item.getName());
        contentValues.put(PRICE_COL, item.getPrice().multiply(BigDecimal.valueOf(100)).longValue());
        contentValues.put(CATEGORY_ID_COL, item.getCategory().getId());
        contentValues.put(PROVIDER_ID_COL, item.getProvider().getId());

        long recordId = this.databaseHelper
                .getWritableDatabase()
                .insert(ARTICLES_TABLE_NAME, null, contentValues);
        logDebug("New record added under id: " + recordId);
        this.databaseHelper.close();

    }

    @Override
    public void update(Article item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COL, item.getId());
        contentValues.put(NAME_COL, item.getName());
        contentValues.put(CATEGORY_ID_COL, item.getCategory().getId());
        contentValues.put(PROVIDER_ID_COL, item.getProvider().getId());

        logDebug(contentValues.toString());

        this.databaseHelper
                .getWritableDatabase()
                .update(ARTICLES_TABLE_NAME, contentValues, ID_COL + "=" + item.getId(), null);
        logDebug("Record updated");
        this.databaseHelper.close();
    }

    @Override
    public void remove(long id) {
        logDebug("Removing item id: " + id);
        databaseHelper.getWritableDatabase().delete(ARTICLES_TABLE_NAME, ID_COL + " = " + id, null);
        databaseHelper.close();
    }

    /*private DatabaseHelper databaseHelper;

    ArticlesStore() {
        this.databaseHelper = DatabaseHelper.getInstance();
    }

    @Override
    public List<Article> getAll() {
        return getAll(DatabaseHelper.ID_COL, DatabaseHelper.SORT_DESC);
        return null;
    }

    @Override
    public List<Article> getWithoutId(long withoutId) {
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
*/

    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }
}
