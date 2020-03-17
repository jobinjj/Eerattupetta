package com.techpakka.eerattupetta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AboutAcitvity extends AppCompatActivity {

    private ImageView imageView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_acitvity);
        if (getIntent().getStringExtra("adurl") != null){
            Constants.showLargeAd(this,getIntent() != null ? getIntent().getStringExtra("adurl") : "noads");
        }
        imageView10 = findViewById(R.id.imageView10);
        Glide.with(getApplicationContext()).load(R.drawable.about).into(imageView10);
    }
    }

