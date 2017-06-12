package com.example.sda.warehouse.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.Category;
import com.example.sda.warehouse.model.stores.CategoriesStore;
import com.example.sda.warehouse.ui.CategoriesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdapter.CategoryClickListener{

    @BindView(R.id.categories_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CategoriesStore categoriesStore;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        init();


    }

    private void init() {


        categoriesStore = new CategoriesStore();

        categoriesStore.add(new Category("Yet Another Category", categoriesStore.getById(3)));

        categoriesAdapter = new CategoriesAdapter(this);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoriesAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories();
                swipeRefreshLayout.setColorSchemeResources(
                        R.color.blue_bright,
                        R.color.green_light,
                        R.color.orange_light,
                        R.color.red_light);
            }
        });

        getCategories();
    }

    private void getCategories() {
        showProgressBar();
        logDebug("Getting all categories.");
        categoriesAdapter.setData(categoriesStore.getAll());
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
                        categoriesStore.remove(category.getId());
                        logDebug(category + " deleted.");
                        makeShortToast("Item deleted");
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



   private void makeShortToast(String string){
       Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
   }
}
