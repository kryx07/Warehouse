package com.example.sda.warehouse.model.beans;

import java.math.BigDecimal;

public class Article extends Bean {
    private BigDecimal price;
    private Category category;
    private Provider provider;

    public Article(long id, String name, BigDecimal price, Category category, Provider provider) {
        super(id, name);
        this.price = price;
        this.category = category;
        this.provider = provider;
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