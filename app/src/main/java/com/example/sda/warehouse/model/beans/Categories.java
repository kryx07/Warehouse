package com.example.sda.warehouse.model.beans;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.category.CategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.example.sda.warehouse.utils.MyApplication.getContext;

public class Categories {

    private List<Category> categoryList;

    private long nextId;

    public Categories(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Categories(List<Category> categoryList, long nextId) {
        this.categoryList = categoryList;
        this.nextId = nextId;
    }

    public Categories() {
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }


    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public long getNextId() {
        return nextId;
    }

    public void setNextId(long nextId) {
        this.nextId = nextId;
    }
}