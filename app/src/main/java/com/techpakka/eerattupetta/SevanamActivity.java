package com.techpakka.eerattupetta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SevanamActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<Data> list = new ArrayList<>();
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevanam);

        setUpFlipView();
        String json = "{\"services:\"[{\"id\":\"1\"},{\"id\":\"2\"},{\"id\":\"2\"}]}";
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray services = jsonObj.getJSONArray("services");
            for (int i = 0; i < services.length(); i++) {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                JSONObject obj = services.getJSONObject(i);
                Log.d("id",obj.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Adapter adapter = new Adapter(getSevenamData());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(SevanamActivity.this, R.dimen.item_offset);
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
    private List<Data> getSevenamData() {
        String services = loadJSONFromAsset();

        try {
            JSONObject jsonObj = new JSONObject(services);
            JSONArray jsonservices = jsonObj.getJSONArray("services");

            // looping through All Contacts
            for (int i = 0; i < jsonservices.length(); i++) {
                JSONObject c = jsonservices.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("name");
                    Data data = new Data();
                    data.setSevanam_title(name);
                    data.setSevanam_id(id);
                    data.setSevanam_image(c.getString("image"));
                    list.add(data);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("sevanam.json");
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
    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        List<Data> list;

        public Adapter(List<Data> list){
            this.list = list;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_sevanam, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_sevanam_title.setText(data.getSevanam_title());
            Glide.with(getApplicationContext()).load(data.getSevanam_image()).into(myViewHolder.imageView);
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SevanamActivity.this,SevanamDetail.class);
                    intent.putExtra("title",data.getSevanam_title());
                    intent.putExtra("id",data.getSevanam_id());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView txt_sevanam_title;
            CardView container;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_sevanam_title = itemView.findViewById(R.id.txt_sevanam_title);
                imageView = itemView.findViewById(R.id.imageView);
                container = itemView.findViewById(R.id.container);
            }
        }
    }
}
