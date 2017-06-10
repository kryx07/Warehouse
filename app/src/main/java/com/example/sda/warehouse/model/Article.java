package com.example.sda.warehouse.model;

import java.math.BigDecimal;

public class Article {
    private long id;
    private String name;
    private BigDecimal price;
    private Category category;
    private Provider provider;

    public Article(long id, String name, BigDecimal price, Category category, Provider provider) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.provider = provider;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}