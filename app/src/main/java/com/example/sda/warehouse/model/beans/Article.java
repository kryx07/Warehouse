package com.example.sda.warehouse.model.beans;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.article.ArticleActivity;
import com.example.sda.warehouse.utils.MyApplication;

import java.math.BigDecimal;
import java.util.List;

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

    public Article(String name, BigDecimal price, Category category, Provider provider) {
        super(name);
        this.price = price;
        this.category = category;
        this.provider = provider;
    }

    public Article() {

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

    public static int getPositionById(long id, List<Article> list) {
        int position = -1;

        for (int i = 0; i < list.size(); ++i) {
            Article article = list.get(i);
            if (article.getId() == id) {
                position = i;
                break;
            }
        }

        return position;
    }

    @Override
    public String toString() {
        return "Article{" +
                "price=" + price +
                ", category=" + category +
                ", provider=" + provider +
                '}';
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String getDetails() {
        return includeColon(MyApplication.getContext().getString(R.string.price)) +
                this.price.toString() + System.lineSeparator() +
                includeColon(MyApplication.getContext().getString(R.string.category)) +
                this.category.toString() + System.lineSeparator() +
                includeColon(MyApplication.getContext().getString(R.string.provider)) +
                this.provider.toString();
    }

    @Override
    public Class getUpdatingActivity() {
        return ArticleActivity.class;
    }
}