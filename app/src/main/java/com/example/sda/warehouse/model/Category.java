package com.example.sda.warehouse.model;

import java.util.List;

public class Category {
    private long id;
    private String name;
    private Category parentCategory;

    public Category(long id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }
    public Category( String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Category(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public static int getPositionById(long id, List<Category> list) {
        int position = -1;

        for(int i = 0;i < list.size();++i) {
            Category category = list.get(i);
            if(category.getId() == id) {
                position = i;
                break;
            }
        }

        return position;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                '}';
    }
}