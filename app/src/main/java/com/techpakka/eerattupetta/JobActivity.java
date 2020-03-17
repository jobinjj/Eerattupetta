package com.techpakka.eerattupetta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final ArrayList<Data> list = new ArrayList<>();
    private Adapter adapter;
    private ProgressBar progress;
    int index = 0;
    private ViewFlipper view_flipper;
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (getIntent().getStringExtra("adurl") != null){
            Constants.showLargeAd(this,getIntent() != null ? getIntent().getStringExtra("adurl") : "noads");
        }
        adapter = new Adapter(getData());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setUpFlipView();
        progress = findViewById(R.id.progress);

            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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
    private List<Data> getData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://nammalerattupettakkar.000webhostapp.com/jobs.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Data data = new Data(jsonObject.getString("image"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("date"));
                                list.add(data);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

        return list;
    }
    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        private final java.util.List<Data> List;
        Adapter(List<Data> List){
            this.List = List;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_vartha, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = List.get(i);
            myViewHolder.title.setText(data.getTitle());
            myViewHolder.txt_description.setText(data.getBody());

            Glide.with(getApplicationContext()).setDefaultRequestOptions(Constants.getThumbnail()).load(data.getImage()).into(myViewHolder.img_thumb);
            myViewHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(JobActivity.this,JobDetailActivity.class);
                      intent.putExtra("title",data.getTitle());
                    intent.putExtra("body" ,data.getBody());
                    intent.putExtra("image",data.getImage());
                    intent.putExtra("date",data.getDate());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView img_thumb;
            TextView title,txt_description;
            CardView card;
            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img_thumb = itemView.findViewById(R.id.img_thumb);
                title = itemView.findViewById(R.id.txt_title);
                card = itemView.findViewById(R.id.card);
                txt_description = itemView.findViewById(R.id.txt_description);

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
