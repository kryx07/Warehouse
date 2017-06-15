package com.example.sda.warehouse.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.stores.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;
import com.example.sda.warehouse.ui.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends RefreshableActivity implements CategoriesAdapter.CategoryClickListener {

    @BindView(R.id.categories_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private IStore<Category> categoryStore;
    private CategoriesAdapter categoriesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Category> currentlySelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        categoryStore = StoreFactory.createCategoriesStore();

        categoriesAdapter = new CategoriesAdapter(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoriesAdapter);

        currentlySelectedItems = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setColorSchemeResources(
                        R.color.blue_bright,
                        R.color.green_light,
                        R.color.orange_light,
                        R.color.red_light);
                floatingActionButton.show();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdatingActivity(0, CategoryActivity.class);
            }
        });

        refresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_delete_item) {
            for (Category selectedCategory : currentlySelectedItems) {
                categoryStore.remove(selectedCategory.getId());
            }
            refresh();
            categoriesAdapter.notifyDataSetChanged();
            /*unCheckAll();*/
            return true;
        }
        return false;
    }

    @Override
    public void refresh() {
        showProgressBar(swipeRefreshLayout);
        logDebug("Getting all categories.");
        categoriesAdapter.setData(categoryStore.getAll());
        hideProgressBar(swipeRefreshLayout);
    }

    @Override
    public void onCategoryClick(Category category) {

        logDebug(category + " clicked.");
        logDebug("Editing " + category);

        //  refresh();
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
                        categoryStore.remove(category.getId());
                        logDebug(category + " deleted.");
                        makeShortToast("Item deleted");
                        refresh();
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
    public void onEditClick(Category category) {
        logDebug(category + " clicked.");
        startUpdatingActivity(category.getId(), CategoryActivity.class);

    }

    @Override
    public void onItemCheck(Category category) {
        currentlySelectedItems.add(category);
        logDebug(currentlySelectedItems.toString());
    }

    @Override
    public void onItemUnCheck(Category category) {
        currentlySelectedItems.remove(category);
        logDebug(currentlySelectedItems.toString());

    }

  /*  private void unCheckAll(){
        for(int i=0; i<recyclerView.getChildCount(); ++i){

            logDebug(recyclerView.getChildAt(i).toString());
        }
    }*/


}
