package com.example.sda.warehouse.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.common.RecyclableActivity;
import com.example.sda.warehouse.activities.common.RefreshableActivity;
import com.example.sda.warehouse.activities.common.UpdatingActivity;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.stores.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;

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
       /* if (isRequestingEDIT()) {
            for (int i = 0; i < parents.size(); ++i) {
                if (parents.get(i).getParentCategory() != null) {
                    if (parents.get(i).getParentCategory().getId() == (category.getId())) {
                        parents.remove(i);
                    }
                }
            }
        }*/
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



/*

    @BindView(R.id.category_name_input)
    EditText categoryNameInput;
    @BindView(R.id.category_parent_spinner)
    Spinner parentCategorySpinner;

    private IStore<Category> store;
    private long id;
    private Category category;
    private int requestCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //intent
        id = getIntent().getLongExtra(DatabaseHelper.ID_COL, 0);
        requestCode = getIntent().getIntExtra(RefreshableActivity.REQUEST_CODE, -1);

        store = StoreFactory.createCategoriesStore();
        //spinner setup
        List<Category> parents = new ArrayList<>();
        parents.add(Category.EMPTY_CATEGORY);
        parents.addAll(store.getWithoutId(id));
        ArrayAdapter<Category> parentsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, parents);
        parentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parentCategorySpinner.setAdapter(parentsAdapter);

        if (requestCode == RecyclableActivity.EDIT) {
            category = store.getById(id);
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
    }

    private void update() {
        Category parentCategory = (Category) parentCategorySpinner.getSelectedItem();
        if (categoryNameInput.getText().length() != 0) {
            if (requestCode == RecyclableActivity.EDIT) {
                category.setName(categoryNameInput.getText().toString());
                category.setParentCategory(parentCategory);
                store.update(category);
            } else {
                store.add(new Category(categoryNameInput.getText().toString(), parentCategory));
            }
            finishActivityWithOKResult();

        } else {
            //categoryNameInput.setPaintFlags(categoryNameInput.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            logAndToast("Category name cannot be empty");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save_item) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityWithCANCELLEDResult();
    }

    private void finishActivityWithOKResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void finishActivityWithCANCELLEDResult() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void logAndToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), string);

    }

    */
}
