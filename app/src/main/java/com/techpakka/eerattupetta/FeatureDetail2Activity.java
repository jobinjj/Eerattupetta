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

public class FeatureDetail2Activity extends AppCompatActivity {

    private String image;
    private String description;
    private String title;
    private TextView title1;
    private TextView description1;
    private ImageView imageView;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_detail2);
        setUpFlipView();
        initializeObjects();
        description1.setText(description);
        title1.setText(title);
        Glide.with(getApplicationContext()).load(image).into(imageView);



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
    private void initializeObjects() {
        image = getIntent().getStringExtra("image");
        description = getIntent().getStringExtra("description");
        title = getIntent().getStringExtra("title");
        title1 = findViewById(R.id.title);
        description1 = findViewById(R.id.description);
        imageView = findViewById(R.id.imageView);
    }
}
