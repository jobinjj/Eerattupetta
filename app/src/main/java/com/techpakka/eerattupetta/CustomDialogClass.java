package com.techpakka.eerattupetta;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CustomDialogClass extends Dialog{

    public Activity c;
    private int position;
    public Dialog d;
    private String ad_url;

    public CustomDialogClass(Activity a,int position,String ad_url) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.position = position;
        this.ad_url = ad_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(c).load(ad_url).into(imageView);
    }


}