package com.example.sda.warehouse.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sda.warehouse.R;
import com.example.sda.warehouse.activities.article.ArticlesActivity;
import com.example.sda.warehouse.activities.category.CategoriesActivity;
import com.example.sda.warehouse.activities.provider.ProvidersActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.categories)
    public void startCategoriesActivity() {
        startActivityFromThisContext(CategoriesActivity.class);
    }

    @OnClick(R.id.providers)
    public void startProvidersActivity() {
        startActivityFromThisContext(ProvidersActivity.class);
    }

    @OnClick(R.id.articles)
    public void startArticlesActivity() {
        startActivityFromThisContext(ArticlesActivity.class);
    }

    @OnClick(R.id.settings)
    public void startSettingsActivity() {
        // startActivityFromThisContext(SettingsActivity.class);
    }

    @OnClick(R.id.note)
    public void startNoteActivity() {
        startActivityFromThisContext(NoteActivity.class);
    }

    private void startActivityFromThisContext(Class activityClass) {
        startActivity(new Intent(getApplicationContext(), activityClass));

    }
}
