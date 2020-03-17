package com.techpakka.eerattupetta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class JobDetailActivity extends AppCompatActivity {
    private String title;
    private String body;
    private TextView txt_title;
    private TextView txt_body;
    private String image;
    private ImageView imageView;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];
    private String date;
    private TextView txt_date;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        setUpFlipView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_title = findViewById(R.id.txt_title);
        txt_body = findViewById(R.id.txt_body);
        imageView = findViewById(R.id.imageView);
        txt_date = findViewById(R.id.txt_date);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            body = extras.getString("body");
            image = extras.getString("image");
            date = extras.getString("date");

            Glide.with(getApplicationContext()).setDefaultRequestOptions(Constants.getThumbnail()).load(image).into(imageView);
            txt_title.setText(title);
            txt_body.setText(body);
            txt_date.setText(date);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "*" + title + "*" + "\n" + "\n" + body +"\n" +"\n" +
                                "from nammal erattupettakkar app" );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
