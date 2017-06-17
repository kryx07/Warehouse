package com.example.sda.warehouse.model.beans;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.utils.MyApplication;

public class Provider extends Bean {
    private String tel;
    private String address;

    public Provider(long id, String name, String tel, String address) {
        super(id, name);
        this.tel = tel;
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String getDetails() {
        return includeColon(MyApplication.getContext().getString(R.string.phone)) +
                this.tel + System.lineSeparator() +
                includeColon(MyApplication.getContext().getString(R.string.address)) +
                this.address;
    }

    @Override
    public Class getUpdatingActivity() {
        return null;
    }
}