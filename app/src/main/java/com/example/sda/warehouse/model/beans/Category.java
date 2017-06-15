package com.example.sda.warehouse.model.beans;

import java.util.List;

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


    @Override
    public String toString() {
        return getName();
    }
}