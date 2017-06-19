package com.example.sda.warehouse.activities.article;

import android.os.Bundle;

import com.example.sda.warehouse.activities.common.RecyclableActivity;
import com.example.sda.warehouse.model.beans.Article;

public class ArticlesActivity extends RecyclableActivity<Article> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /*StoreFactory.createArticlesStore().add(
                new Article("Dupa",
                        BigDecimal.valueOf(5.32),
                        StoreFactory.createCategoriesStore().getAll().get(0),
                        StoreFactory.createProvidersStore().getAll().get(0)));*/
    }

}
