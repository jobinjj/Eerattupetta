package com.techpakka.eerattupetta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecondHandActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progress;
    private Adapter adapter;
    private FloatingActionButton fab;
    private String price;
    private String description;
    private String image;
    private String email;
    private ImageView img_house;
    private ImageView img_electronics;
    private ImageView img_mobile;
    private ImageView img_car,img_motor;
    private ImageView img_furniture;
    private RecyclerView recyclerView;
    public static ArrayList<Data> list;
    public static Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondHandActivity.this,AddSecondsActivity.class);
                startActivity(intent);
            }
        });
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        img_house = findViewById(R.id.img_house);
        img_electronics = findViewById(R.id.img_electronics);
        img_car = findViewById(R.id.img_car);
        img_motor = findViewById(R.id.img_motor);
        img_mobile = findViewById(R.id.img_mobile);
        img_furniture = findViewById(R.id.img_furniture);
        img_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                adapter = new Adapter(getData("House"));
                recyclerView.setAdapter(adapter);
            }
        });
        img_furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                adapter = new Adapter(getData("Furniture"));
                recyclerView.setAdapter(adapter);
            }
        });
        img_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                adapter = new Adapter(getData("Mobile"));
                recyclerView.setAdapter(adapter);
            }
        });
        img_electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                adapter = new Adapter(getData("Electronics"));
                recyclerView.setAdapter(adapter);
            }
        });
        img_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                adapter = new Adapter(getData("Car"));
                recyclerView.setAdapter(adapter);
            }
        });

        img_motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                adapter = new Adapter(getData("Motor"));
                recyclerView.setAdapter(adapter);
            }
        });

        adapter = new Adapter(getData("House"));
        recyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private List<Data> getData(String category) {
        list = new ArrayList<>();
        db.collection(category).orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    progress.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setPrice(document.getString("price"));
                            data.setSecond_description(document.getString("description"));
                            data.setSecond_image(document.getString("image"));
                            data.setMobile(document.getString("mobile"));
                            data.setEmail(document.getString("email"));
                            data.setSecond_username(document.getString("username"));
                            data.setSecond_documentid(document.getString("document"));
                            data.setSecond_category(document.getString("category"));
                            data.setSecond_date(document.getString("date"));
                            list.add(data);

                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });
        return list;
    }

    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        private final java.util.List<Data> List;
        Adapter(List<Data> List){
            this.List = List;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_second_hand, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            data = List.get(i);
            myViewHolder.txt_price.setText(data.getPrice());
            myViewHolder.txt_description.setText(data.getSecond_description());
            Glide.with(getApplicationContext()).load(data.getSecond_image()).into(myViewHolder.img_second);
            myViewHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data = List.get(i);
                    Intent intent = new Intent(SecondHandActivity.this,SecondDetailActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("price", data.getPrice());
                    intent.putExtra("description", data.getSecond_description());
                    intent.putExtra("mobile", data.getMobile());
                        intent.putExtra("email", data.getEmail());
                    intent.putExtra("image", data.getSecond_image());
                    intent.putExtra("username", data.getSecond_username());
                    intent.putExtra("documentid", data.getSecond_documentid());
                    intent.putExtra("category", data.getSecond_category());
                    intent.putExtra("date", data.getSecond_date());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView img_second;
            private final TextView txt_price;
            private final TextView txt_description;
            CardView card;
            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img_second = itemView.findViewById(R.id.img_second);
                txt_price = itemView.findViewById(R.id.txt_title);
                txt_description = itemView.findViewById(R.id.txt_description);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.size() > 0){
            recreate();
        }
    }
}
