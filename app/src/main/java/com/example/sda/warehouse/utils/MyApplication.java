package com.example.sda.warehouse.utils;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class MyApplication extends Application {

    public static MyApplication thisApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        thisApplication = this;
    }

    public static MyApplication getInstance() {

        return thisApplication;
    }

    public static Application getContext() {
        try {
            return (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            logDebug(e.getMessage());
        }
        return null;
    }

    private static void logDebug(String string) {
        Log.e(MyApplication.class.toString(), string);

    }


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
