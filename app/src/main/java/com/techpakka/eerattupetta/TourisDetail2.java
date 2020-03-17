package com.techpakka.eerattupetta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TourisDetail2 extends AppCompatActivity {

    private String title;
    private String detail;
    private String image;
    private TextView txt_title;
    private TextView txt_detail;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touris_detail2);

        title = getIntent().getStringExtra("title");
        detail= getIntent().getStringExtra("detail");
        image = getIntent().getStringExtra("image");

        txt_title = findViewById(R.id.txt_title);
        txt_detail = findViewById(R.id.txt_detail);
        imageView = findViewById(R.id.imageView);
        txt_title.setText(title);
        txt_detail.setText(detail);
        Glide.with(getApplicationContext()).load(image).into(imageView);

    }

}
