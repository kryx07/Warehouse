package com.example.sda.warehouse.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sda.warehouse.R;

import butterknife.ButterKnife;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}
