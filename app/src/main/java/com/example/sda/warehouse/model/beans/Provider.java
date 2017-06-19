package com.example.sda.warehouse.model.beans;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.provider.ProviderActivity;
import com.example.sda.warehouse.utils.MyApplication;

public class Provider extends Bean {
    private String tel;
    private String address;

    public Provider(long id, String name, String tel, String address) {
        super(id, name);
        this.tel = tel;
        this.address = address;
    }

    public Provider(String name, String tel, String address) {
        super(name);
        this.tel = tel;
        this.address = address;
    }


    public Provider() {
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
        return ProviderActivity.class;
    }

    /*@Override
    public String toString() {
        return "Provider{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                "tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + getName() + '\'' +
                '}';
    }
}