package com.techpakka.eerattupetta.Shopukal;

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
import com.techpakka.eerattupetta.Ariyippukal;
import com.techpakka.eerattupetta.AutoActivity;
import com.techpakka.eerattupetta.BusActivity;
import com.techpakka.eerattupetta.Constants;
import com.techpakka.eerattupetta.EmergencyActivity;
import com.techpakka.eerattupetta.HomeActivity;
import com.techpakka.eerattupetta.HomeAdapter;
import com.techpakka.eerattupetta.ItemOffsetDecoration;
import com.techpakka.eerattupetta.JobActivity;
import com.techpakka.eerattupetta.Model.Data;
import com.techpakka.eerattupetta.R;
import com.techpakka.eerattupetta.SecondHandActivity;
import com.techpakka.eerattupetta.ShopDetailActivity;
import com.techpakka.eerattupetta.TrollActivtiy;
import com.techpakka.eerattupetta.Vahanangal.Vahanangal;
import com.techpakka.eerattupetta.VarthaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Shopukal extends AppCompatActivity {
    ArrayList<Data> list = new ArrayList<>();
    ShopAdapter adapter;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopukal);
        getData();
        setUpFlipView();
        adapter = new ShopAdapter(list);
        recyclerView = findViewById(R.id.recyclerView);
        if (getIntent().getStringExtra("adurl") != null){
            Constants.showLargeAd(this,getIntent() != null ? getIntent().getStringExtra("adurl") : "noads");
        }
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(Shopukal.this, R.dimen.item_offset);
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
    private void getData() {
        ArrayList<Data> locallist = new ArrayList<>();
        String data = loadJSONFromAsset();
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray shop = jsonObj.getJSONArray("shops");

            // looping through All Contacts
            for (int i = 0; i < shop.length(); i++) {
                JSONObject c = shop.getJSONObject(i);
                    String name = c.getString("name_malayalam");
                    Data data2 = new Data();
                    data2.setShoptitle(name);
                    data2.setShopimage(c.getString("image"));
                    data2.setShop_id(c.getString("id"));
                    locallist.add(data2);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list = locallist;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("shop.json");
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
    public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
        List<Data> list;

        public ShopAdapter(List<Data> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_shop, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
           Data data = list.get(i);
            Glide.with(getApplicationContext()).load(data.getShopimage()).into(myViewHolder.imageView);
            myViewHolder.txt_shopname.setText(data.getShoptitle());
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Shopukal.this,ShopDetailActivity.class);
                    intent.putExtra("shopid",data.getShop_id());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView txt_shopname;
            private CardView container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                txt_shopname = itemView.findViewById(R.id.txt_shopname);
                container = itemView.findViewById(R.id.container);

            }
        }
    }
}
