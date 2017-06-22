package com.example.sda.warehouse.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by sda on 22.06.17.
 */

public class SimpleService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new SimpleViewFactory(getApplicationContext());
    }
}
