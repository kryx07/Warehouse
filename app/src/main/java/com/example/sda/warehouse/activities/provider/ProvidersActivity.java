package com.example.sda.warehouse.activities.provider;

import android.os.Bundle;

import com.example.sda.warehouse.activities.common.RecyclableActivity;
import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.stores.StoreFactory;

public class ProvidersActivity extends RecyclableActivity<Provider> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //StoreFactory.createProvidersStore().add(new Provider("Dupex","90 00 800 123 456","ul. dupna 6/9"));
        //refresh();
    }

}
