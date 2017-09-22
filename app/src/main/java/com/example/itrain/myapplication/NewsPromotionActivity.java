package com.example.itrain.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsPromotionActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/promotion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_promotion);
    }
}
