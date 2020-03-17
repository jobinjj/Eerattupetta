package com.techpakka.eerattupetta;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AutoDetail extends AppCompatActivity {

    private String location_id;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detail);

        location_id = getIntent().getStringExtra("location_id");
        Adapter adapter = new Adapter(getAuto());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(AutoDetail.this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private List<Data> getAuto() {

        ArrayList<Data> list = new ArrayList<>();
        String autodata = loadJSONFromAsset();
        try {
            JSONObject jsonObj = new JSONObject(autodata);
            JSONArray contacts = jsonObj.getJSONArray("drivers");
            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
               // Log.d("location","test");
                JSONObject c = contacts.getJSONObject(i);
                String location_id2 = c.getString("location_id");
                if (location_id2.equals(location_id)){
                    Data data = new Data();
                    data.setDriverName(c.getString("name_malayalam"));
                    data.setDriverLocation(c.getString("place"));
                    data.setDriverPhone(c.getString("phone"));
                    list.add(data);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

return list;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("auto.json");
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
    public class Adapter extends  RecyclerView.Adapter<Adapter.MyViewHolder>{
        List<Data> list;
        public Adapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_auto_detail, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder muViewHolder, int i) {
            Data data = list.get(i);
            muViewHolder.txt_location.setText(data.getDriverLocation());
            muViewHolder.txt_mobile.setText(data.getDriverPhone());
            muViewHolder.txt_name.setText(data.getDriverName());
            muViewHolder.img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri number = Uri.parse("tel:" + data.getDriverPhone());
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
            private TextView txt_name,txt_location,txt_mobile;
            private ImageView img_call;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_name = itemView.findViewById(R.id.txt_name5);
                txt_location = itemView.findViewById(R.id.txt_location4);
                txt_mobile = itemView.findViewById(R.id.txt_mobile);
                img_call = itemView.findViewById(R.id.img_call);

            }
        }
    }
}
