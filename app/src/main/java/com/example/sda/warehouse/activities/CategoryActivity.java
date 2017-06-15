package com.example.sda.warehouse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.stores.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryActivity extends AppCompatActivity {


    @BindView(R.id.category_name_input)
    EditText categoryName;
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
        id = getIntent().getLongExtra("id", 0);
        requestCode = getIntent().getIntExtra(RefreshableActivity.REQUEST_CODE, -1);

        store = StoreFactory.createCategoriesStore();
        //spinner setup
        List<Category> parents = new ArrayList<>();
        parents.add(Category.EMPTY_CATEGORY);
        parents.addAll(store.getWithoutId(id));
        ArrayAdapter<Category> parentsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, parents);
        parentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parentCategorySpinner.setAdapter(parentsAdapter);

        if (requestCode == RefreshableActivity.EDIT) {
            category = store.getById(id);
            categoryName.setText(category.getName());
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
        if (requestCode == RefreshableActivity.EDIT) {
            category.setName(categoryName.getText().toString());
            category.setParentCategory(parentCategory);
            store.update(category);
        } else {
            store.add(new Category(categoryName.getText().toString(), parentCategory));
        }
        finishActivityWithOKResult();

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
}
