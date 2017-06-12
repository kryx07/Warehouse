package com.example.sda.warehouse.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.Category;
import com.example.sda.warehouse.model.stores.CategoriesStore;
import com.example.sda.warehouse.model.stores.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wd40 on 12.06.17.
 */

public class CategoryActivity  extends AppCompatActivity {

    @BindView(R.id.category_name_input)
    EditText categoryName;
    /*@BindView(R.id.button_save_category)
    Button saveButton;*/
    @BindView(R.id.category_parent_spinner)
    Spinner parentCategorySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        init();

    }

    private void init() {

        long id =getIntent().getIntExtra(CategoriesStore.ID_COL,0);


        IStore<Category> store = StoreFactory.createCategoriesStore();
        List<Category> parents = new ArrayList<>();
        parents.add(new Category(0, "Brak", null));
        parents.addAll(store.getWithoutId(id));


        ArrayAdapter<Category> parentsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, parents);
        parentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parentCategorySpinner.setAdapter(parentsAdapter);

        if(id != 0) {
            Category category = store.getById(id);
            categoryName.setText(category.getName());
            int parentPosition = Category.getPositionById(category.getParentCategory().getId(), parents);
            if(parentPosition == -1) {
                parentPosition = 0;
            }

            parentCategorySpinner.setSelection(parentPosition);
        }
    }

    @OnClick(R.id.button_save_category)
    public void update(){

    }
}
