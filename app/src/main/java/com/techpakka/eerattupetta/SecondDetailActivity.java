package com.techpakka.eerattupetta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.Model.Data;

import java.util.Random;

public class SecondDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private SharedPreferences preferences;
    private String phone_number;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String documentid;
    private String category;
    private Button btn_delete;
    Data data;
    private TextView txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_detail);
        preferences = this.getSharedPreferences("erattupetta",MODE_PRIVATE);
        imageView = findViewById(R.id.imageView);
        TextView txt_username = findViewById(R.id.txt_username);
        TextView txt_phone = findViewById(R.id.txt_contact);
        btn_delete = findViewById(R.id.btn_delete);


        TextView txt_description = findViewById(R.id.txt_description);
        TextView txt_email = findViewById(R.id.txt_contact2);
        TextView txt_price = findViewById(R.id.txt_title);
        txt_date = findViewById(R.id.txt_date);


            txt_username.setText(SecondHandActivity.data.getSecond_username());
            txt_phone.setText( SecondHandActivity.data.getMobile());
            phone_number = SecondHandActivity.data.getMobile();
            documentid =  SecondHandActivity.data.getSecond_documentid();
            category =  SecondHandActivity.data.getSecond_category();
            txt_date.setText(SecondHandActivity.data.getSecond_date());
            txt_email.setText(SecondHandActivity.data.getEmail());
            txt_description.setText(SecondHandActivity.data.getSecond_description());
            txt_price.setText(SecondHandActivity.data.getPrice());
            Glide.with(getApplicationContext()).load(SecondHandActivity.data.getSecond_image()).into(imageView);
            if (SecondHandActivity.data.getSecond_username().equals(preferences.getString("username","null"))){
                findViewById(R.id.btn_call).setVisibility(View.GONE);
                findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
            }


        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + phone_number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_delete.setEnabled(false);
                if (category != null){
                    db.collection(category).document(documentid)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SecondDetailActivity.this, "deleted succesfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SecondDetailActivity.this, "error deleting coupon", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });


    }
}
