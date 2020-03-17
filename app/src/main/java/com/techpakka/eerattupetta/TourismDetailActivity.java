package com.techpakka.eerattupetta;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TourismDetailActivity extends AppCompatActivity {

    private String type;
    private RecyclerView recyclerView;
    ArrayList<Data> list1 = new ArrayList<>();
    ArrayList<Data> list2 = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AttractionAdapter attractionAdapter;
    int index = 0;
    private ViewFlipper view_flipper;
    private String[] ad_images = new String[3];
    private ResortsAdapter resortsAdapter;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_detail);
        setUpFlipView();
        progress = findViewById(R.id.progress);
        attractionAdapter = new AttractionAdapter(getAttractionsData());
        resortsAdapter = new ResortsAdapter(getResortdata());
        type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(TourismDetailActivity.this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (type != null){
            if (type.equals("resort")){
                recyclerView.setAdapter(resortsAdapter);
            }else if (type.equals("tourism")){
                recyclerView.setAdapter(attractionAdapter);
            }
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
    private List<Data> getResortdata() {
        ArrayList<Data> resortlist = new ArrayList<>();
        db.collection("resort").orderBy("id", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setResorts_Name(document.getString("name"));
                            data.setResorts_location(document.getString("place"));
                            data.setResorts_mobile(document.getString("phone"));
                            data.setResorts_image(document.getString("image"));
                            resortlist.add(data);

                            resortsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });
        return resortlist;
    }
    private List<Data> getAttractionsData() {
        ArrayList<Data> attractionlist = new ArrayList<>();
        db.collection("attraction").orderBy("id", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setAttraction_name(document.getString("head"));
                            data.setAttraction_image(document.getString("image"));
                            data.setAttraction_detail(document.getString("body"));
                            attractionlist.add(data);

                            attractionAdapter.notifyDataSetChanged();
                        }
                        if (attractionlist.size() == 0){
                            showNoDataMessage();
                        }
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });
        return attractionlist;
    }

    private void showNoDataMessage() {
        Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
    }

    private List<Data> getResortsData() {

        String timing = loadJSONFromAsset("resorts.json");
        try {
            JSONObject jsonObj = new JSONObject(timing);
            JSONArray contacts = jsonObj.getJSONArray("resorts");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                Data data = new Data();
                data.setResorts_Name(c.getString("name"));
                data.setResorts_location(c.getString("place"));
                data.setResorts_mobile(c.getString("phone"));
                data.setResorts_image(c.getString("image"));
                list2.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list2;
    }

   private List<Data> getAttractionData() {
        String timing = loadJSONFromAsset("attraction.json");
        try {
            JSONObject jsonObj = new JSONObject(timing);
            JSONArray contacts = jsonObj.getJSONArray("attraction");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                Data data = new Data();
                data.setAttraction_name(c.getString("head"));
                data.setAttraction_image(c.getString("image"));
                data.setAttraction_detail(c.getString("body"));

                list1.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list1;
    }

    public class  AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.MyViewHolder>{
        List<Data> list;
        public AttractionAdapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public AttractionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_attraction, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AttractionAdapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_name.setText(data.getAttraction_name());
            Glide.with(getApplicationContext()).load(data.getAttraction_image()).into(myViewHolder.img_tourism);
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String action;
                    Intent intent = new Intent(TourismDetailActivity.this,TourisDetail2.class);
                    intent.putExtra("title",data.getAttraction_name());
                    intent.putExtra("detail",data.getAttraction_detail());
                    intent.putExtra("image",data.getAttraction_image());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView img_tourism;
            private CardView container;
            private TextView txt_name;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img_tourism = itemView.findViewById(R.id.img_resorts);
                txt_name = itemView.findViewById(R.id.txt_name);
                container = itemView.findViewById(R.id.container);

            }
        }
    }
    public class  ResortsAdapter extends RecyclerView.Adapter<ResortsAdapter.MyViewHolder>{
        List<Data> list;
        public ResortsAdapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public ResortsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_resorts, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ResortsAdapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            Glide.with(getApplicationContext()).load(data.getResorts_image()).into(myViewHolder.img_resorts);
            myViewHolder.txt_location.setText(data.getResorts_location());
            myViewHolder.txt_mobile.setText(data.getResorts_mobile());
            myViewHolder.txt_name.setText(data.getResorts_Name());
            myViewHolder.imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri number = Uri.parse("tel:" + data.getResorts_mobile());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView img_resorts;
            private ImageView imageView8;
            private TextView txt_name,txt_location,txt_mobile;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img_resorts = itemView.findViewById(R.id.img_resorts);
                imageView8 = itemView.findViewById(R.id.imageView8);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_location = itemView.findViewById(R.id.txt_location);
                txt_mobile = itemView.findViewById(R.id.txt_mobile);

            }
        }
    }
    public String loadJSONFromAsset(String type) {
        String json = null;
        try {
            InputStream is = getAssets().open(type);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
