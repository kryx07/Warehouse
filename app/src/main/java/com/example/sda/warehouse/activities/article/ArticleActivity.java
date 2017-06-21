package com.example.sda.warehouse.activities.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.common.UpdatingActivity;
import com.example.sda.warehouse.model.beans.Article;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sda.warehouse.model.beans.Category.getPositionById;


public class ArticleActivity extends UpdatingActivity<Article> {

    @BindView(R.id.article_name_input)
    EditText articleNameInput;
    @BindView(R.id.article_price_input)
    EditText articlePriceInput;
    @BindView(R.id.category_spinner)
    Spinner articleCategorySpinner;
    @BindView(R.id.provider_spinner)
    Spinner articleProviderSpinner;

    private IStore<Article> store;
    private Article article;

    private List<Category> categoryList;
    private List<Provider> providerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        init();
    }


    @Override
    protected void init() {
        store = StoreFactory.createArticlesStore();
        article = store.getById(getId());

        setupCategorySpinner();
        setupProvidersSpinner();

        if (isRequestingEDIT()) {
            populateViewFromIntent();
        }
    }

    @Override
    protected void populateViewFromIntent() {

        articleNameInput.setText(article.getName());
        articlePriceInput.setText(article.getPrice().toString());

        int categoryPosition;
        if (article.getCategory() == null) {
            categoryPosition = 0;
        } else {
            categoryPosition = getPositionById(article.getCategory().getId(), categoryList);
        }
        if (categoryPosition == -1) {
            categoryPosition = 0;
        }

        articleCategorySpinner.setSelection(categoryPosition);

        int providerPosition;
        if (article.getProvider() == null) {
            providerPosition = 0;
        } else {
            providerPosition = Provider.getPositionById(article.getProvider().getId(), providerList);
        }
        if (providerPosition == -1) {
            providerPosition = 0;
        }
        articleProviderSpinner.setSelection(providerPosition);
    }

    private void setupCategorySpinner() {
        categoryList = new ArrayList<>();
        IStore<Category> categoryStore = StoreFactory.createCategoriesStore();
        categoryList.addAll(categoryStore.getAll());
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        articleCategorySpinner.setAdapter(categoryArrayAdapter);
    }

    private void setupProvidersSpinner() {
        providerList = new ArrayList<>();
        IStore<Provider> articleStore = StoreFactory.createProvidersStore();
        providerList.addAll(articleStore.getAll());
        ArrayAdapter<Provider> articleArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, providerList);
        articleArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        articleProviderSpinner.setAdapter(articleArrayAdapter);
    }

    @Override
    protected boolean areAllFieldsPopulated() {
        return articleNameInput.length() != 0 && articlePriceInput.length() != 0;
    }

    @Override
    protected Article getItemFromView() {

        Article article;

        if (this.article == null) {
            article = new Article();
        } else {
            article = this.article;
        }

        article.setName(articleNameInput.getText().toString());
        article.setPrice(new BigDecimal(articlePriceInput.getText().toString()));
        article.setCategory((Category) articleCategorySpinner.getSelectedItem());
        article.setProvider((Provider) articleProviderSpinner.getSelectedItem());
        return article;

    }

    @Override
    protected void save() {

        if (areAllFieldsPopulated()) {
            article = getItemFromView();
            if (isRequestingEDIT()) {
                store.update(article);
            } else if (isRequestingADD()) {
                store.add(article);
            }
            finishActivityWithOKResult();
        } else {
            logAndToast("Please populate all fields");
        }
    }


 /*   @BindView(R.id.category_name_input)
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

    }*/

}
