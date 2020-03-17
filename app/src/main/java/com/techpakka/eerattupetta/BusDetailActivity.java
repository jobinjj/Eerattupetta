package com.techpakka.eerattupetta;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
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

public class BusDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String route_id;
    private ArrayList<Data> busList;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        setUpFlipView();
        route_id = getIntent().getStringExtra("route_id");
        Adapter  adapter  = new Adapter(getData());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(BusDetailActivity.this, R.dimen.item_offset);
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
        busList = new ArrayList<>();


        String timing = loadJSONFromAsset();

        try {
            JSONObject jsonObj = new JSONObject(timing);
            JSONArray contacts = jsonObj.getJSONArray("timing");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String route_id2 = c.getString("route_id");
                if (route_id2.equals(route_id)){
                    Data data = new Data();
                    String name = c.getString("name");
                    String time = c.getString("time").trim();
                    String[] separated = time.split(":");
                    String value = separated[0].trim();
                    if (Integer.parseInt(value) >= 12){
                        data.setBus_time(time + " PM");
                    } else {
                        data.setBus_time(time + " AM");
                    }

                    data.setBus_name(name);
                   // data.setBus_time(time);
                    busList.add(data);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Data> list = new ArrayList<>();
        Data data = new Data();
        data.setBus_name("jobin");
        data.setBus_time("09:00 AM");
        list.add(data);
        Data data1 = new Data();
        data1.setBus_name("jithin");
        data1.setBus_time("10:00 AM");
        list.add(data1);
        return busList;
    }

    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        private java.util.List<Data> List;
        public Adapter(List<Data> List){
            this.List = List;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_detail_bus, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = List.get(i);
            myViewHolder.txt_bus_name.setText(data.getBus_name());
            myViewHolder.txt_time.setText(data.getBus_time());
        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txt_bus_name;
            private TextView txt_time;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_bus_name = itemView.findViewById(R.id.txt_bus_name);
                txt_time= itemView.findViewById(R.id.txt_time);

            }
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("bus.json");
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
