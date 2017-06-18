package com.example.sda.warehouse.model.stores;

import com.example.sda.warehouse.model.beans.Article;
import com.example.sda.warehouse.model.beans.Category;
import com.example.sda.warehouse.model.beans.Provider;

import static com.example.sda.warehouse.model.stores.StoreFactory.BeanClass.getBeanFromClass;

public class StoreFactory {

    public static IStore<?> createStore(final Class clazz) {
        switch (getBeanFromClass(clazz)) {
            case CATEGORY:
                return createCategoriesStore();
            case PROVIDER:
                return createProvidersStore();
            default:
                return null;
        }
    }

    public static IStore<Category> createCategoriesStore() {
        return new CategoriesStore();
    }

    public static IStore<Provider> createProvidersStore() {
        return new ProvidersStore();
    }

    private static String getClassName(Class clazz) {
        return clazz.getSimpleName();
    }
/*
    public static IStore<Article> createArticlesStore(IStore<Category> categoriesStore, IStore<Provider> providersStore) {
        return new ArticlesStore(new DatabaseHelper(Application.get().getApplicationContext()), categoriesStore, providersStore);
    }*/

    enum BeanClass {

        CATEGORY(Category.class),
        PROVIDER(Provider.class),
        ARTICLE(Article.class),
        EMPTY(Object.class);

        Class clazz;

        BeanClass(Class clazz) {
            this.clazz = clazz;
        }

        public static BeanClass getBeanFromClass(Class clazz) {
            for (BeanClass b : values()) {
                if (b.clazz.equals(clazz)) {
                    return b;
                }
            }
            return EMPTY;
        }
    }
}

