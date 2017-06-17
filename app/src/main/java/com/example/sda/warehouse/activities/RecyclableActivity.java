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
import com.example.sda.warehouse.model.beans.Bean;
import com.example.sda.warehouse.model.stores.IStore;
import com.example.sda.warehouse.model.stores.StoreFactory;
import com.example.sda.warehouse.ui.ItemsAdapter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclableActivity<T extends Bean> extends RefreshableActivity implements ItemsAdapter.ItemClickListener<T> {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private IStore<T> itemsStore;
    private ItemsAdapter itemsAdapter;
    private List<T> currentlySelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclable);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        initStore();

        itemsAdapter = new ItemsAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemsAdapter);

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

    @SuppressWarnings("unchecked")
    private void initStore() {
        Class<T> typeClass = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
        itemsStore = (IStore<T>) StoreFactory.createStore(typeClass);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_delete_item) {
            if (currentlySelectedItems.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete multiple categories")
                        .setMessage("Are you sure you want to delete those entries?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (T selectedCategory : currentlySelectedItems) {
                                    itemsStore.remove(selectedCategory.getId());
                                }
                                refresh();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                logDebug("Nothing deleted.");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                //itemsAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refresh() {
        showProgressBar(swipeRefreshLayout);
        logDebug("Getting all categories.");
        itemsAdapter.setData(itemsStore.getAll());
        hideProgressBar(swipeRefreshLayout);
    }

    @Override
    public void onItemClick(T category) {

        logDebug(category + " clicked.");
        logDebug("Editing " + category);

        //  refresh();
    }

    @Override
    public void onDeleteClick(final T item) {
        logDebug(item + " clicked.");

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete item")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        itemsStore.remove(item.getId());
                        logDebug(item + " deleted.");
                        makeShortToast("Item deleted");
                        refresh();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logDebug(item + " not deleted.");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onEditClick(T item) {
        logDebug(item + " clicked.");
        startUpdatingActivity(item.getId(), item.getUpdatingActivity());
    }

    @Override
    public void onItemCheck(T item) {
        currentlySelectedItems.add(item);
        logDebug(currentlySelectedItems.toString());
    }

    @Override
    public void onItemUnCheck(T item) {
        currentlySelectedItems.remove(item);
        logDebug(currentlySelectedItems.toString());

    }

}
