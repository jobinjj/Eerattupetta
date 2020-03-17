package com.techpakka.eerattupetta;

import android.content.Intent;
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

public class AutoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        setUpFlipView();
        AutoAdapter autoAdapter = new AutoAdapter(getAutoData());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(AutoActivity.this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(autoAdapter);

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
    private List<Data> getAutoData() {
        ArrayList<Data> list = new ArrayList<>();
        String auto = "{\n" +
                "    \"auto\": [\n" +
                "        {\n" +
                "            \"id\": \"15\",\n" +
                "            \"name_english\": \"Aman junction\",\n" +
                "            \"name_malayalam\": \"അമാൻ ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-09 19:36:05\",\n" +
                "            \"updated_at\": \"2018-08-30 12:08:59\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"19\",\n" +
                "            \"name_english\": \"Central junction\",\n" +
                "            \"name_malayalam\": \"സെൻട്രൽ ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-14 10:09:38\",\n" +
                "            \"updated_at\": \"2018-09-25 09:57:24\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"6\",\n" +
                "            \"name_english\": \"Huda Junction, Nadakkal\",\n" +
                "            \"name_malayalam\": \"ഹുദാ ജങ്ഷൻ നടക്കൽ\",\n" +
                "            \"created_at\": \"2018-07-23 17:05:12\",\n" +
                "            \"updated_at\": \"2018-08-26 17:29:37\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"17\",\n" +
                "            \"name_english\": \"Kaduvamuzhi\",\n" +
                "            \"name_malayalam\": \"കടുവാമുഴി ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-09 19:46:15\",\n" +
                "            \"updated_at\": \"2018-08-26 17:29:46\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5\",\n" +
                "            \"name_english\": \"KSRTC Erattupetta\",\n" +
                "            \"name_malayalam\": \"കെ.എസ്.ആർ.ടി.സി  ഈരാറ്റുപേട്ട \",\n" +
                "            \"created_at\": \"2018-07-23 13:08:42\",\n" +
                "            \"updated_at\": \"2018-07-23 13:08:42\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"13\",\n" +
                "            \"name_english\": \"market\",\n" +
                "            \"name_malayalam\": \"മാർക്കറ്റ്\",\n" +
                "            \"created_at\": \"2018-08-06 21:25:17\",\n" +
                "            \"updated_at\": \"2018-08-06 21:25:17\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"10\",\n" +
                "            \"name_english\": \"muttam jn.\",\n" +
                "            \"name_malayalam\": \"മുട്ടം ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-07-30 11:50:34\",\n" +
                "            \"updated_at\": \"2018-08-26 17:30:11\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"12\",\n" +
                "            \"name_english\": \"nadackal jn.\",\n" +
                "            \"name_malayalam\": \"നടക്കൽ ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-06 19:33:09\",\n" +
                "            \"updated_at\": \"2018-08-26 17:30:26\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"20\",\n" +
                "            \"name_english\": \"nadackal, kollmkandam\",\n" +
                "            \"name_malayalam\": \"നടക്കൽ , കൊല്ലംകണ്ടം ജങ്ഷൻ \",\n" +
                "            \"created_at\": \"2018-08-31 10:46:18\",\n" +
                "            \"updated_at\": \"2018-08-31 10:46:18\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"24\",\n" +
                "            \"name_english\": \"night service centraljuncttion\",\n" +
                "            \"name_malayalam\": \"നൈറ്റ്\u200C സര്\u200Dവീസ് (സെന്\u200Dട്രല്\u200D ജംഗ്ഷന്\u200D)\",\n" +
                "            \"created_at\": \"2018-09-17 12:51:02\",\n" +
                "            \"updated_at\": \"2018-09-17 12:51:02\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"18\",\n" +
                "            \"name_english\": \"P.Aruvithura\",\n" +
                "            \"name_malayalam\": \"അരുവിത്തുറ ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-10 20:08:32\",\n" +
                "            \"updated_at\": \"2018-08-26 17:30:39\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"14\",\n" +
                "            \"name_english\": \"pmc jn.\",\n" +
                "            \"name_malayalam\": \"പി എം സി ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-09 19:18:23\",\n" +
                "            \"updated_at\": \"2018-08-26 17:30:48\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"7\",\n" +
                "            \"name_english\": \"pvt. bus stand\",\n" +
                "            \"name_malayalam\": \"പ്രൈവറ്റ് ബസ് സ്റ്റാൻഡ്\",\n" +
                "            \"created_at\": \"2018-07-25 20:20:22\",\n" +
                "            \"updated_at\": \"2018-07-25 20:20:22\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"21\",\n" +
                "            \"name_english\": \"rims\",\n" +
                "            \"name_malayalam\": \"റിംസ് ജംക്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-31 10:55:33\",\n" +
                "            \"updated_at\": \"2018-08-31 10:55:33\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"16\",\n" +
                "            \"name_english\": \"thekkekara chenad kavala\",\n" +
                "            \"name_malayalam\": \"തെക്കേക്കര, ചെന്നാട് കവല \",\n" +
                "            \"created_at\": \"2018-08-09 19:45:44\",\n" +
                "            \"updated_at\": \"2018-08-09 19:45:44\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"25\",\n" +
                "            \"name_english\": \"Thevaru para\",\n" +
                "            \"name_malayalam\": \"തേവരുപാറ\",\n" +
                "            \"created_at\": \"2018-09-18 10:38:04\",\n" +
                "            \"updated_at\": \"2018-09-18 10:38:04\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"22\",\n" +
                "            \"name_english\": \"Vadakkekara\",\n" +
                "            \"name_malayalam\": \"വടക്കേക്കര ജങ്ഷൻ\",\n" +
                "            \"created_at\": \"2018-08-31 11:06:34\",\n" +
                "            \"updated_at\": \"2018-08-31 11:06:34\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"26\",\n" +
                "            \"name_english\": \"Z. Aaniyilapp\",\n" +
                "            \"name_malayalam\": \"ആനിയിളപ്പ്\",\n" +
                "            \"created_at\": \"2018-09-21 08:44:26\",\n" +
                "            \"updated_at\": \"2018-09-21 08:46:07\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            JSONObject jsonObj = new JSONObject(auto);
            JSONArray contacts = jsonObj.getJSONArray("auto");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {

                JSONObject c = contacts.getJSONObject(i);
                    String location_name = c.getString("name_malayalam");
                    Data data = new Data();
                    data.setAuto_location(location_name);
                    data.setLocation_id(c.getString("id"));
                    list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }

    public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.MyViewHolder>{
        List<Data> list;
        public AutoAdapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public AutoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_auto, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AutoAdapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_location.setText(data.getAuto_location());
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Intent intent = new Intent(AutoActivity.this,AutoDetail.class);
                        intent.putExtra("location_id",data.getLocation_id());
                        startActivity(intent);


                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txt_location;
            private CardView container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_location = itemView.findViewById(R.id.txt_location4);
                container = itemView.findViewById(R.id.container);
            }
        }
    }

    public void container1(View view) {
        Intent intent = new Intent(AutoActivity.this,AutoDetail.class);
        intent.putExtra("location","erattupetta");
        startActivity(intent);
    }
}
