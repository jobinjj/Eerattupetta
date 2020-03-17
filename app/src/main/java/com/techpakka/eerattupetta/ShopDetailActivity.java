package com.techpakka.eerattupetta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ShopDetailActivity extends AppCompatActivity {



    private String bakerydata;

    ArrayList<Data> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private JSONArray contacts;
    int index = 0;
    private String shopid;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        shopid = getIntent().getStringExtra("shopid");
        getShopData(shopid);
        Adapter adapter = new Adapter(list);
        recyclerView = findViewById(R.id.recyclerView);
        setUpFlipView();


        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(ShopDetailActivity.this, R.dimen.item_offset);
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

    private void getShopData(String shopid) {
        String timing = loadJSONFromAsset();


        try {
            JSONObject jsonObj = new JSONObject(timing);
            contacts = jsonObj.getJSONArray("shops");

            // looping through All Data
            for (int i = 0; i < contacts.length(); i++) {
                new parseJsonTask().execute(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private class parseJsonTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... ints) {

            try {
                JSONObject c = contacts.getJSONObject(ints[0]);
                if (c.getString("category_id").equals(shopid)){
                    Data data = new Data();
                    data.setShopdetailtitle(c.getString("name_malayalam"));
                    data.setShopdetaillocation(c.getString("place"));
                    data.setShopdetailphone(c.getString("phone"));
                    list.add(data);
                    return c.getString("name_malayalam");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("shopdetail.json");
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
                    .inflate(R.layout.content_shop_detail, viewGroup, false);
            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.shop_owner_name.setText(data.getShopdetailtitle());
            myViewHolder.shop_owner_mobile.setText(data.getShopdetailphone());
            myViewHolder.shop_owner_location.setText(data.getShopdetaillocation());
            myViewHolder.imageView11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri number = Uri.parse("tel:" + data.getShopdetailphone());
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
            TextView shop_owner_name,shop_owner_mobile,shop_owner_location;
            ImageView imageView11;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                shop_owner_name = itemView.findViewById(R.id.txt_shop_owner_name);
                shop_owner_location = itemView.findViewById(R.id.txt_shop_location);
                shop_owner_mobile = itemView.findViewById(R.id.txt_shop_mobile);
                imageView11 = itemView.findViewById(R.id.imageView11);
            }
        }
    }


}
