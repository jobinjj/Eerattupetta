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

public class FeautureNewsDetail extends AppCompatActivity {

    private String title;
    private String body;
    private String image;
    ImageView mimageView;
    TextView mtxt_title,mtxt_Description,mtxt_date;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feauture_news_detail);

        setUpFlipView();
        mtxt_Description = findViewById(R.id.txt_description);
        mtxt_date = findViewById(R.id.txt_date);
        mtxt_title = findViewById(R.id.txt_title);
        mimageView = findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            title = extras.getString("title");
            body = extras.getString("description");
            date = extras.getString("date");
            image = extras.getString("image");
            Glide.with(getApplicationContext()).load(image).into(mimageView);
            mtxt_title.setText(title);
            mtxt_Description.setText(body);
            mtxt_date.setText(date);

        }
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
}
