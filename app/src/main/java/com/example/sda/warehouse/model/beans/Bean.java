package com.example.sda.warehouse.model.beans;

import org.w3c.dom.Text;

/**
 * Created by wd40 on 14.06.17.
 */

public abstract class Bean {
    private long id;
    private String name;


    public Bean(String name) {
        this.name = name;
    }

    public Bean(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bean() {
    }

    ;

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

    public String includeColon(String string) {
        return string + ": ";
    }
}
