package com.techpakka.eerattupetta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String item;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        Spinner spinner = findViewById(R.id.spinner);

        setUpFlipView();
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        List<String> blood_group = new ArrayList<String>();
        blood_group.add("A +ve");
        blood_group.add("B +ve");
        blood_group.add("AB +ve");
        blood_group.add("O +ve");
        blood_group.add("A -ve");
        blood_group.add("B -ve");
        blood_group.add("AB -ve");
        blood_group.add("O -ve");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, blood_group);

        // Drop down layout style - list2 view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    private void setUpFlipView() {
        view_flipper = findViewById(R.id.view_flipper);
        db.collection("bottomads")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            ad_images[index] = document.getString("image");
                            index++;
                        }
                        for(int i=0;i< ad_images.length;i++)
                        {
                            ImageView image = new ImageView(getApplicationContext());
                            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(getApplicationContext()).load(ad_images[i]).into(image);
                            view_flipper.addView(image);
                        }
                        view_flipper.startFlipping();
                    } else {
                        Log.d("error", "Error getting documents.", task.getException());
                    }
                });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = String.valueOf(i + 1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void searchBlood(View view) {
        Intent intent = new Intent(BloodBankActivity.this,BloodDetail.class);
        intent.putExtra("item",item);
        startActivity(intent);
    }
}
