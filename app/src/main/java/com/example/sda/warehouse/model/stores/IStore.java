package com.example.sda.warehouse.model.stores;

import com.example.sda.warehouse.model.beans.Category;

import java.util.List;

public interface IStore<T> {

    List<T> getAll();

    List<T> getWithoutId(long withoutId);

    List<T> getAll(String column, String order);

    T getById(long id);

    void add(T item);

    void update(T item);

    void remove(long id);

}
