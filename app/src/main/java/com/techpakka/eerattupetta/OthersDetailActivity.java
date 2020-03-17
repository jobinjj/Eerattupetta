package com.techpakka.eerattupetta;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OthersDetailActivity extends AppCompatActivity {

    private String category_id;
    private RecyclerView recyclerView;
    private ArrayList<Data> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_detail);

        category_id = getIntent().getStringExtra("category_id");
        Adapter adapter = new Adapter(getOthersData());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(OthersDetailActivity.this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private List<Data> getOthersData() {
        list = new ArrayList<>();
        String timing = loadJSONFromAsset();

        try {
            JSONObject jsonObj = new JSONObject(timing);
            JSONArray contacts = jsonObj.getJSONArray("others");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                if (category_id.equals(c.getString("category_id"))){
                    Data data = new Data();
                    data.setOtherdetail_name(c.getString("name_malayalam"));
                    data.setOtherdetail_location(c.getString("place"));
                    data.setOtherdetail_mobile(c.getString("phone"));
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
            InputStream is = getAssets().open("othersdetail.json");
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
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_name.setText(data.getOtherdetail_name());
            myViewHolder.txt_location.setText(data.getOtherdetail_location());
            myViewHolder.txt_phone.setText(data.getOtherdetail_mobile());
            myViewHolder.img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri number = Uri.parse("tel:" + data.getOtherdetail_mobile());
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
            private TextView txt_name,txt_location,txt_phone;
            private ImageView img_call;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_name = itemView.findViewById(R.id.txt_name5);
                txt_location = itemView.findViewById(R.id.txt_location4);
                txt_phone = itemView.findViewById(R.id.txt_mobile);
                img_call = itemView.findViewById(R.id.img_call);

            }
        }
    }
}
