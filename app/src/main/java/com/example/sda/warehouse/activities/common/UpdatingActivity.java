package com.example.sda.warehouse.activities.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;

public abstract class UpdatingActivity<T> extends AppCompatActivity {
    private long id;
    private int requestCode;
    private boolean isRequestingEDIT;
    private boolean isRequestingADD = getRequestCode() == RefreshableActivity.ADD;

    public boolean isRequestingEDIT() {
        return isRequestingEDIT;
    }

    public boolean isRequestingADD() {
        return isRequestingADD;
    }

    public long getId() {
        return id;
    }

    public int getRequestCode() {
        return requestCode;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.id = getIntent().getLongExtra(DatabaseHelper.ID_COL, 0);
        this.requestCode = getIntent().getIntExtra(RefreshableActivity.REQUEST_CODE, -1);
        isRequestingADD = getRequestCode() == RefreshableActivity.ADD;
        isRequestingEDIT = getRequestCode() == RefreshableActivity.EDIT;

    }

    protected abstract void init();

    protected abstract void populateViewFromIntent();

    protected abstract boolean areAllFieldsPopulated();

    protected abstract T getItemFromView();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save_item) {
            save();
            return true;
        }
        return false;
    }

    protected abstract void save();


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityWithCANCELLEDResult();
    }

    protected void finishActivityWithOKResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    protected void finishActivityWithCANCELLEDResult() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void logAndToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), string);

    }
}
