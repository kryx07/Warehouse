package com.example.sda.warehouse.activities.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.common.UpdatingActivity;
import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.stores.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProviderActivity extends UpdatingActivity {

    @BindView(R.id.provider_name_input)
    EditText providerNameInput;
    @BindView(R.id.provider_phone_input)
    EditText providerPhoneInput;
    @BindView(R.id.provider_address_input)
    EditText providerAddressInput;

    private IStore<Provider> store;
    private Provider provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        store = StoreFactory.createProvidersStore();
        provider = store.getById(getId());
        if (isRequestingEDIT()) {
            populateViewFromIntent();
        }
    }

    @Override
    protected void populateViewFromIntent() {
        providerNameInput.setText(provider.getName());
        providerPhoneInput.setText(provider.getTel());
        providerAddressInput.setText(provider.getAddress());
    }

    @Override
    protected boolean areAllFieldsPopulated() {
        return providerNameInput.length() != 0 &&
                providerPhoneInput.length() != 0 &&
                providerAddressInput.length() != 0;
    }

    @Override
    protected Provider populateItemFromView() {
        Provider provider;
        if (this.provider == null) {
            provider = new Provider();
        } else {
            provider = this.provider;
        }
        provider.setName(providerNameInput.getText().toString());
        provider.setTel(providerPhoneInput.getText().toString());
        provider.setAddress(providerAddressInput.getText().toString());
        return provider;
    }

    @Override
    protected void save() {
        if (areAllFieldsPopulated()) {
            provider = populateItemFromView();
            if (isRequestingEDIT()) {
                store.update(provider);
            } else if (isRequestingADD()) {
                store.add(provider);
            }
            finishActivityWithOKResult();
        }else{
            logAndToast("Please populate all fields");
        }

    }




    /*



    @BindView(R.id.category_name_input)
    EditText categoryNameTextField;
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
            categoryNameTextField.setText(category.getName());
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
        if (categoryNameTextField.getText().length() != 0) {
            if (requestCode == RecyclableActivity.EDIT) {
                category.setName(categoryNameTextField.getText().toString());
                category.setParentCategory(parentCategory);
                store.update(category);
            } else {
                store.add(new Category(categoryNameTextField.getText().toString(), parentCategory));
            }
            finishActivityWithOKResult();

        } else {
            //categoryNameTextField.setPaintFlags(categoryNameTextField.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

    }*/
}
