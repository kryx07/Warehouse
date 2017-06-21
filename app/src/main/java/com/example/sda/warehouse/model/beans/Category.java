package com.example.sda.warehouse.model.beans;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.category.CategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

    public Category(JSONObject category) throws JSONException {
        super();
        setId(category.getLong("id"));
        String name = category.getString("name");

        if (category.has("parent_category")) {
            parentCategory = new Category(category.getJSONObject("parent_category"));
        }
    }

    public Category() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return getId() == category.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public class IdComparator implements Comparator<Category> {

        @Override
        public int compare(Category o1, Category o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    }

    public class NameComparator implements Comparator<Category> {

        @Override
        public int compare(Category o1, Category o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}