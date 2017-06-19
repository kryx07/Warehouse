package com.example.sda.warehouse.activities.article;

import android.os.Bundle;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.common.RecyclableActivity;
import com.example.sda.warehouse.model.beans.Article;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.beans.Provider;
import com.example.sda.warehouse.model.stores.StoreFactory;

import java.math.BigDecimal;

public class ArticlesActivity extends RecyclableActivity<Article> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StoreFactory.createArticlesStore().add(new Article("Dupa", BigDecimal.valueOf(5.32), new Category("dupaCategory", null), new Provider("dupa", "telDupa", "addressDupa")));
    }

}
