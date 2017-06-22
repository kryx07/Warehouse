package com.example.sda.warehouse.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.model.IStore;
import com.example.sda.warehouse.model.beans.Article;
import com.example.sda.warehouse.model.stores.StoreFactory;

import java.util.List;

/**
 * Created by sda on 22.06.17.
 */

class SimpleViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Article> articleList;
    private Context context;

    public SimpleViewFactory(Context context) {
        this.context = context;

        IStore<Article> store = StoreFactory.createArticlesStore();
        articleList = store.getAll();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.simple_row);
        row.setTextViewText(R.id.text_view, articleList.get(position).getName());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
