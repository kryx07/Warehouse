package com.example.sda.warehouse.model.stores;

import com.example.sda.warehouse.model.Category;
import com.example.sda.warehouse.model.stores.database.DatabaseHelper;

public class StoreFactory {
    public static IStore<Category> createCategoriesStore() {
        return new CategoriesStore();
    }

   /* public static IStore<Provider> createProvidersStore() {
        return new ProvidersStore(new DatabaseHelper(Application.get().getApplicationContext()));
    }

    public static IStore<Article> createArticlesStore(IStore<Category> categoriesStore, IStore<Provider> providersStore) {
        return new ArticlesStore(new DatabaseHelper(Application.get().getApplicationContext()), categoriesStore, providersStore);
    }*/
}
