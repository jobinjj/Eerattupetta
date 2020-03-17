package com.techpakka.eerattupetta;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusActivity extends AppCompatActivity {
Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList busList;
    private String id;
    private ArrayList<Data> list;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        setUpFlipView();
        if (getIntent().getStringExtra("adurl") != null){
            Constants.showLargeAd(this,getIntent() != null ? getIntent().getStringExtra("adurl") : "noads");
        }
        adapter = new Adapter(getData());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(BusActivity.this, R.dimen.item_offset);
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
        busList = new ArrayList<String>();

        String json = "{\n" +
                "        \"bus\": [\n" +
                "            {\n" +
                "                \"id\": \"3\",\n" +
                "                \"route\": \"എറണാകുളം\",\n" +
                "                \"created_at\": \"2018-06-28 10:25:25\",\n" +
                "                \"updated_at\": \"2018-06-28 10:25:25\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"4\",\n" +
                "                \"route\": \"കോട്ടയം\",\n" +
                "                \"created_at\": \"2018-06-28 10:25:39\",\n" +
                "                \"updated_at\": \"2018-06-28 10:25:39\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"5\",\n" +
                "                \"route\": \"തിരുവനന്തപുരം\",\n" +
                "                \"created_at\": \"2018-06-28 10:26:13\",\n" +
                "                \"updated_at\": \"2018-06-28 10:26:13\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"6\",\n" +
                "                \"route\": \"ആലപ്പുഴ\",\n" +
                "                \"created_at\": \"2018-06-28 10:26:30\",\n" +
                "                \"updated_at\": \"2018-06-28 10:26:30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"7\",\n" +
                "                \"route\": \"തലയോലപ്പറമ്പ്\",\n" +
                "                \"created_at\": \"2018-06-28 10:29:51\",\n" +
                "                \"updated_at\": \"2018-06-28 10:29:51\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"8\",\n" +
                "                \"route\": \"പാലാ\",\n" +
                "                \"created_at\": \"2018-06-28 10:30:07\",\n" +
                "                \"updated_at\": \"2018-06-28 10:30:07\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"9\",\n" +
                "                \"route\": \"പിറവം \",\n" +
                "                \"created_at\": \"2018-06-28 10:36:43\",\n" +
                "                \"updated_at\": \"2018-06-28 10:36:43\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"10\",\n" +
                "                \"route\": \"ഉഴവൂർ\",\n" +
                "                \"created_at\": \"2018-06-28 10:36:59\",\n" +
                "                \"updated_at\": \"2018-06-28 10:36:59\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"11\",\n" +
                "                \"route\": \"ഈരാറ്റുപേട്ട\",\n" +
                "                \"created_at\": \"2018-06-28 10:37:35\",\n" +
                "                \"updated_at\": \"2018-06-28 10:37:35\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"12\",\n" +
                "                \"route\": \"തൊടുപുഴ\",\n" +
                "                \"created_at\": \"2018-06-28 10:41:03\",\n" +
                "                \"updated_at\": \"2018-06-28 10:41:03\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"13\",\n" +
                "                \"route\": \"മൂവാറ്റുപുഴ\",\n" +
                "                \"created_at\": \"2018-06-28 10:41:19\",\n" +
                "                \"updated_at\": \"2018-09-22 03:22:50\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"14\",\n" +
                "                \"route\": \"രാമപുരം\",\n" +
                "                \"created_at\": \"2018-06-28 11:12:04\",\n" +
                "                \"updated_at\": \"2018-06-28 11:12:04\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"15\",\n" +
                "                \"route\": \"കൂത്താട്ടുകുളം\",\n" +
                "                \"created_at\": \"2018-06-28 15:59:59\",\n" +
                "                \"updated_at\": \"2018-06-28 15:59:59\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"16\",\n" +
                "                \"route\": \"കട്ടപ്പന\",\n" +
                "                \"created_at\": \"2018-07-12 19:25:19\",\n" +
                "                \"updated_at\": \"2018-07-12 19:25:19\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"17\",\n" +
                "                \"route\": \"ഏലപ്പാറ\",\n" +
                "                \"created_at\": \"2018-07-12 19:26:16\",\n" +
                "                \"updated_at\": \"2018-07-12 19:26:16\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"18\",\n" +
                "                \"route\": \"വാഗമൺ \",\n" +
                "                \"created_at\": \"2018-07-12 19:26:46\",\n" +
                "                \"updated_at\": \"2018-07-17 18:52:36\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"19\",\n" +
                "                \"route\": \"കൊല്ലം \",\n" +
                "                \"created_at\": \"2018-07-17 18:49:29\",\n" +
                "                \"updated_at\": \"2018-07-17 18:49:29\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"21\",\n" +
                "                \"route\": \"മലപ്പുറം\",\n" +
                "                \"created_at\": \"2018-07-18 07:44:30\",\n" +
                "                \"updated_at\": \"2018-07-18 07:44:30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"22\",\n" +
                "                \"route\": \"കണ്ണൂർ\",\n" +
                "                \"created_at\": \"2018-07-18 07:45:13\",\n" +
                "                \"updated_at\": \"2018-07-18 07:45:13\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"23\",\n" +
                "                \"route\": \"കോഴിക്കോട്\",\n" +
                "                \"created_at\": \"2018-07-18 07:45:36\",\n" +
                "                \"updated_at\": \"2018-07-18 07:45:36\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"24\",\n" +
                "                \"route\": \"തൃശ്ശൂർ\",\n" +
                "                \"created_at\": \"2018-07-18 07:46:22\",\n" +
                "                \"updated_at\": \"2018-07-18 10:34:11\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"25\",\n" +
                "                \"route\": \"പാലക്കാട്\",\n" +
                "                \"created_at\": \"2018-07-18 07:49:20\",\n" +
                "                \"updated_at\": \"2018-07-18 07:49:20\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"26\",\n" +
                "                \"route\": \"കോയമ്പത്തൂർ\",\n" +
                "                \"created_at\": \"2018-07-18 07:51:53\",\n" +
                "                \"updated_at\": \"2018-07-18 07:51:53\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"27\",\n" +
                "                \"route\": \"കാസർഗോഡ്\",\n" +
                "                \"created_at\": \"2018-07-18 10:34:58\",\n" +
                "                \"updated_at\": \"2018-07-18 10:34:58\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"28\",\n" +
                "                \"route\": \"വയനാട്\",\n" +
                "                \"created_at\": \"2018-07-18 10:35:39\",\n" +
                "                \"updated_at\": \"2018-07-18 10:35:39\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"29\",\n" +
                "                \"route\": \"ആലപ്പുഴ\",\n" +
                "                \"created_at\": \"2018-07-31 13:50:40\",\n" +
                "                \"updated_at\": \"2018-07-31 13:50:40\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"30\",\n" +
                "                \"route\": \"കുമളി\",\n" +
                "                \"created_at\": \"2018-08-21 08:01:23\",\n" +
                "                \"updated_at\": \"2018-08-21 08:01:23\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"31\",\n" +
                "                \"route\": \"വണ്ടിപ്പെരിയാർ\",\n" +
                "                \"created_at\": \"2018-08-21 08:01:56\",\n" +
                "                \"updated_at\": \"2018-08-21 08:01:56\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"32\",\n" +
                "                \"route\": \"പത്തനംതിട്ട (റാന്നി)\",\n" +
                "                \"created_at\": \"2018-08-21 08:02:20\",\n" +
                "                \"updated_at\": \"2018-08-21 08:02:51\"\n" +
                "            }\n" +
                "        ]\n" +
                "}";

        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray contacts = jsonObj.getJSONArray("bus");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String id = c.getString("id");
                String route = c.getString("route");
                Data data = new Data();
                data.setBusroute(route);
                data.setRouteId(c.getString("id"));
                busList.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
                    .inflate(R.layout.content_bus, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = List.get(i);
            myViewHolder.txt_location.setText(data.getBusroute());
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BusActivity.this,BusDetailActivity.class);
                    intent.putExtra("route_id",data.getRouteId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txt_location;
            private LinearLayout container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_location = itemView.findViewById(R.id.txt_location4);
                container = itemView.findViewById(R.id.container);

            }
        }
    }

}
