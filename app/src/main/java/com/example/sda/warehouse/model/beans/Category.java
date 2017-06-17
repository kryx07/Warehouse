package com.example.sda.warehouse.model.beans;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.CategoryActivity;
import com.example.sda.warehouse.utils.MyApplication;

import java.util.List;

import static com.example.sda.warehouse.utils.MyApplication.*;

public class Category extends Bean {
    private Category parentCategory;

    public Category(long id, String name, Category parentCategory) {
        super(id, name);
        this.parentCategory = parentCategory;
    }

    public Category(String name, Category parentCategory) {
        super(name);
        this.parentCategory = parentCategory;
    }

    public Category() {
    }

    public static final Category EMPTY_CATEGORY = new Category(0, "Empty", null);

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public static int getPositionById(long id, List<Category> list) {
        int position = -1;

        for (int i = 0; i < list.size(); ++i) {
            Category category = list.get(i);
            if (category.getId() == id) {
                position = i;
                break;
            }
        }

        return position;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String getDetails() {
        return parentCategory == null ? getContext().getString(R.string.empty) :
                includeColon(getContext().getString(R.string.parent)) +
                        this.parentCategory.toString();
    }

    @Override
    public Class getUpdatingActivity() {
        return CategoryActivity.class;
    }

    @Override
    public String toString() {
        return getName();
    }
}