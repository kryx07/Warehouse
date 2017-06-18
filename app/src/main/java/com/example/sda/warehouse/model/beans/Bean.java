package com.example.sda.warehouse.model.beans;

public abstract class Bean {
    private long id;
    private String name;


    Bean(String name) {
        this.name = name;
    }

    Bean(long id, String name) {
        this.id = id;
        this.name = name;
    }

    Bean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getDetails();

    public String getTitle() {
        return this.name;
    }

    String includeColon(String string) {
        return string + ": ";
    }

    public abstract Class getUpdatingActivity();
}
