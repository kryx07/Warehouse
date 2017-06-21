package com.example.sda.warehouse.activities.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.common.UpdatingActivity;
import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProviderActivity extends UpdatingActivity {

    @BindView(R.id.provider_name_input)
    EditText providerNameInput;
    @BindView(R.id.provider_phone_input)
    EditText providerPhoneInput;
    @BindView(R.id.provider_address_input)
    EditText providerAddressInput;

    private IStore<Provider> store;
    private Provider provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void init() {
        store = StoreFactory.createProvidersStore();
        provider = store.getById(getId());
        if (isRequestingEDIT()) {
            populateViewFromIntent();
        }
    }


    @Override
    protected void populateViewFromIntent() {

            providerNameInput.setText(provider.getName());
            providerPhoneInput.setText(provider.getTel());
            providerAddressInput.setText(provider.getAddress());

    }

    @Override
    protected boolean areAllFieldsPopulated() {
        return providerNameInput.length() != 0 &&
                providerPhoneInput.length() != 0 &&
                providerAddressInput.length() != 0;
    }

    @Override
    protected Provider getItemFromView() {
        Provider provider;
        if (this.provider == null) {
            provider = new Provider();
        } else {
            provider = this.provider;
        }
        provider.setName(providerNameInput.getText().toString());
        provider.setTel(providerPhoneInput.getText().toString());
        provider.setAddress(providerAddressInput.getText().toString());
        return provider;
    }

    @Override
    protected void save() {
        if (areAllFieldsPopulated()) {
            provider = getItemFromView();
            if (isRequestingEDIT()) {
                store.update(provider);
            } else if (isRequestingADD()) {
                store.add(provider);
            }
            finishActivityWithOKResult();
        } else {
            logAndToast("Please populate all fields");
        }

    }

}
