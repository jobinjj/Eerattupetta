package com.techpakka.eerattupetta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.ScratchImageView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RewardActivity extends AppCompatActivity {
    ArrayList<Data> list = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ScratchImageView sample_image;
    private ImageView img_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        list = getData();
        sample_image = findViewById(R.id.sample_image);

        img_qrcode = findViewById(R.id.img_qrcode);
        int point = getIntent().getIntExtra("points",0);
        int total_questions = getIntent().getIntExtra("total",0);
        if (point > total_questions / 2){
            showScratchCard();
        }else {
            Intent intent = new Intent(RewardActivity.this,HomeActivity.class);
            startActivity(intent);
            Toast.makeText(this, "sorry not enough score :(", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private ArrayList<Data> getData() {
        ArrayList<Data> list = new ArrayList<>();
        db.collection("Coupons").orderBy("id", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setCoupon_code(document.getString("code"));
                            data.setCoupon_image(document.getString("image"));
                            data.setCoupon_qrcode(document.getString("qrcode"));
                            data.setCoupon_title(document.getString("title"));
                            list.add(data);
                        }
                        Random random = new Random();
                        int randomnum = random.nextInt(2 - 1 + 1) + 1;
                        if (String.valueOf(randomnum).equals("1")){
                            Random random2 = new Random();
                            int randomnum2 = random2.nextInt(list.size() - 1 + 1) + 1;
                            Data data = list.get(randomnum2-1);
                            Glide.with(getApplicationContext()).load(data.getCoupon_image()).into(sample_image);
                            Glide.with(getApplicationContext()).load(data.getCoupon_qrcode()).into(img_qrcode);
                        }else{
                            sample_image.setImageDrawable(getResources().getDrawable(R.drawable.betterluck));
                            img_qrcode.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });
        return list;
    }
    private void showScratchCard() {
        sample_image.setVisibility(View.VISIBLE);
        img_qrcode.setVisibility(View.VISIBLE);
    }
}
