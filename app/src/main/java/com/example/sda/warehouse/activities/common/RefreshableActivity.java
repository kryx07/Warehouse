package com.example.sda.warehouse.activities.common;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;
import com.example.sda.warehouse.utils.MyApplication;

public abstract class RefreshableActivity extends AppCompatActivity {

    public final static int EDIT = 87;
    public final static int ADD = 88;

    @SuppressWarnings("ConstantConditions")
    public final static String REQUEST_CODE = MyApplication.getContext().getString(R.string.request_code);

    public abstract void refresh();

    public void startUpdatingActivity(long id, Class updatingActivityClass) {
        Intent intent = new Intent(getApplicationContext(), updatingActivityClass);
        intent.putExtra(DatabaseHelper.ID_COL, id);
        if (id > 0) {
            intent.putExtra(REQUEST_CODE, EDIT);
            startActivityForResult(intent, EDIT);
        } else {
            intent.putExtra(REQUEST_CODE, ADD);
            startActivityForResult(intent, ADD);
        }
    }

    public void showProgressBar(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void hideProgressBar(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(false);
    }


    public void makeShortToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }

    public void logAndToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), string);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: {
                switch (requestCode) {
                    case EDIT:
                }

            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT) {
                logAndToast("Item successfully updated.");
            } else {
                logAndToast("Item added.");
            }

        } else {
            if (requestCode == EDIT) {
                logAndToast("Item not updated.");
            } else {
                logAndToast("No item added.");
            }
        }

        refresh();

    }
}
