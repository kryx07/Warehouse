package com.example.sda.warehouse.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sda.warehouse.R;

public class SharedPreferencesManager {

    private static SharedPreferencesManager sharedPreferencesManager;

    public SharedPreferencesManager getInstance() {
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager();
        }
        return sharedPreferencesManager;
    }

    private SharedPreferences sharedPreferences;

    public void write(String tag, String string) {
        sharedPreferences = MyApplication.getContext().getSharedPreferences(tag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(tag, 0).edit();

        editor.putString(tag, string);
        editor.apply();
    }

    public String read(String tag) {
        sharedPreferences = MyApplication.getContext().getSharedPreferences(tag, Context.MODE_PRIVATE);

        String defVal = MyApplication.getContext().getString(R.string.shared_prefs_error);
        return sharedPreferences.getString(tag, defVal);

    }
}
