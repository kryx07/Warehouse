package com.example.sda.warehouse.model;

import java.util.List;

public interface IStore<T> {

    List<T> getAll();

    List<T> getAll(String column, String order);

    T getById(long id);

    List<T> getWithoutId(long withoutId);

    T getEmpty();

    void add(T item);

    void update(T item);

    void remove(long id);

}
