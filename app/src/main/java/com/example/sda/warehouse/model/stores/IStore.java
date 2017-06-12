package com.example.sda.warehouse.model.stores;

import java.util.List;

public interface IStore<T> {

    List<T> getAll();

    List<T> getAll(String column, String order);

    T getById(long id);

    void add(T category);

    void remove(long id);

}
