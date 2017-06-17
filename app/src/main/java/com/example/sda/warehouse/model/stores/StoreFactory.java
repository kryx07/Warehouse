package com.example.sda.warehouse.model.stores;

import com.example.sda.warehouse.model.beans.Bean;
import com.example.sda.warehouse.model.beans.Category;

public class StoreFactory<T extends Bean> {


    public static IStore<?> createStore(final Class c) {

        if (c == Category.class) {
            return createCategoriesStore();
        } else {
            return null;
        }
    }

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
