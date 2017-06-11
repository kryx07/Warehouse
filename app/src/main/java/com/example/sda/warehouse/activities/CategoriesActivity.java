package com.example.sda.warehouse.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.Category;
import com.example.sda.warehouse.model.datasource.CategoryDataSource;
import com.example.sda.warehouse.ui.CategoriesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdapter.CategoryClickListener {

    @BindView(R.id.categories_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CategoryDataSource categoryDataSource;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        init();


    }

    private void init() {


        categoryDataSource = new CategoryDataSource();

        categoryDataSource.add(new Category("Yet Another Category", categoryDataSource.getById(3)));


        categoriesAdapter = new CategoriesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(categoriesAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories();
            }
        });

        getCategories();
    }

    private void getCategories() {
        showProgressBar();
        logDebug("Getting all categories.");
        categoriesAdapter.setData(categoryDataSource.getAll());
        hideProgressBar();
    }

    private void logDebug(String string) {
        Log.e(getClass().getSimpleName(), string);

    }

    @Override
    public void onCategoryClick(Category category) {

        logDebug(category + " clicked.");
        logDebug("Editing " + category);
        //categoryDataSource.remove(category.getId());
        getCategories();
    }

    @Override
    public void onDeleteClick(final Category category) {
        logDebug(category + " clicked.");

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete category")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        categoryDataSource.remove(category.getId());
                        logDebug(category + " deleted.");
                        // TODO: 11.06.17 Toast to be added
                        getCategories();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logDebug(category + " not deleted.");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    private void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
