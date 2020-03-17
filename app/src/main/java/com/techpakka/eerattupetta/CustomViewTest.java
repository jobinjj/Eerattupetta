package com.techpakka.eerattupetta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.techpakka.eerattupetta.customviews.CategorySelectView;

public class CustomViewTest extends AppCompatActivity {

    private CategorySelectView categorySelectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);


    }
}
