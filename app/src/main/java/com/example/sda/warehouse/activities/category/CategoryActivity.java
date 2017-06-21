package com.example.sda.warehouse.activities.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.common.UpdatingActivity;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryActivity extends UpdatingActivity<Category> {

    @BindView(R.id.category_name_input)
    EditText categoryNameInput;
    @BindView(R.id.category_parent_spinner)
    Spinner parentCategorySpinner;

    private IStore<Category> store;
    private Category category;

    private List<Category> parents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void init() {
        store = StoreFactory.createCategoriesStore();
        category = store.getById(getId());

        setupSpinner();

        if (isRequestingEDIT()) {
            populateViewFromIntent();
        }

    }

    private void setupSpinner() {

        parents = new ArrayList<>();
        parents.add(store.getEmpty());
        parents.addAll(store.getWithoutId(getId()));
        ArrayAdapter<Category> parentsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, parents);
        parentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parentCategorySpinner.setAdapter(parentsAdapter);
    }

    @Override
    protected void populateViewFromIntent() {
        categoryNameInput.setText(category.getName());
        int parentPosition;
        if (category.getParentCategory() == null) {
            parentPosition = 0;
        } else {
            parentPosition = Category.getPositionById(category.getParentCategory().getId(), parents);
        }
        if (parentPosition == -1) {
            parentPosition = 0;
        }
        parentCategorySpinner.setSelection(parentPosition);
    }

    @Override
    protected boolean areAllFieldsPopulated() {
        return categoryNameInput.length() != 0;
    }

    @Override
    protected Category getItemFromView() {
        Category category;

        if (this.category == null) {
            category = new Category();
        } else {
            category = this.category;
        }

        category.setName(categoryNameInput.getText().toString());
        category.setParentCategory((Category) parentCategorySpinner.getSelectedItem());
        return category;
    }

    @Override
    protected void save() {
        if (areAllFieldsPopulated()) {
            category = getItemFromView();
            if (isRequestingEDIT()) {
                store.update(category);
            } else if (isRequestingADD()) {
                store.add(category);
            }
            finishActivityWithOKResult();
        } else {
            logAndToast("Please populate all fields");
        }

    }
}
