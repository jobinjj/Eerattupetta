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
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ThozhilalikalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<Data> list = new ArrayList<>();
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thozhilalikal);
        setUpFlipView();

        if (getIntent().getStringExtra("adurl") != null){
            Constants.showLargeAd(this,getIntent() != null ? getIntent().getStringExtra("adurl") : "noads");
        }
        Adapter adapter = new Adapter(getData());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(ThozhilalikalActivity.this, R.dimen.item_offset);
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
        String workers = loadJSONFromAsset();
        try {
            JSONObject jsonObj = new JSONObject(workers);
            JSONArray contacts = jsonObj.getJSONArray("workers");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("name_malayalam");
                    String id = c.getString("id");
                    Data data = new Data();
                    data.setThozhil(name);
                    data.setThozhil_id(id);
                    data.setThozhil_image(c.getString("image"));
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
            InputStream is = getAssets().open("thozhl.json");
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
                    .inflate(R.layout.content_thozhilalikal, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_thozhil.setText(data.getThozhil());
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ThozhilalikalActivity.this,ThozhilalikalDetail.class);
                    intent.putExtra("id",data.getThozhil_id());
                    startActivity(intent);
                }
            });
            Glide.with(getApplicationContext()).load(data.getThozhil_image()).into(myViewHolder.imageView6);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private CardView container;
            private TextView txt_thozhil;
            ImageView imageView6;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_thozhil = itemView.findViewById(R.id.txt_thozhil);
                container = itemView.findViewById(R.id.container);
                imageView6 =itemView.findViewById(R.id.imageView6);
            }
        }
    }
}
