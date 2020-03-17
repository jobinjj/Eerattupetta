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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.List;

public class FeaturesDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Data> list;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Adapter adapter;
    private String features;
    private ProgressBar progressBar;
    int index = 0;
    private ViewFlipper view_flipper;
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_detail);
        if (getIntent() != null){
            features = getIntent().getStringExtra("feature");
        }
        setUpFlipView();
        adapter = new Adapter(getFeaureData(features));
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(FeaturesDetailActivity.this, R.dimen.item_offset);
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
    private List<Data> getFeaureData(String features) {
        list = new ArrayList<>();
        db.collection(features).orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
//                    db.disableNetwork()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    // Do offline things
//                                    // ...
//                                }
//                            });
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data2 = new Data();
                            data2.setFeature_title(document.getString("title"));
                            data2.setFeature_description(document.getString("description"));
                            data2.setFeature_image(document.getString("image"));
                            list.add(data2);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                });


        return list;
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        private List<Data> list;
        public Adapter(List<Data> list){
            this.list = list;

        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_shop, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {

            Data data = list.get(i);
            myViewHolder.txt_shopname.setText(data.getFeature_title());
            Glide.with(getApplicationContext()).load(data.getFeature_image()).into(myViewHolder.imageView);
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FeaturesDetailActivity.this,FeatureDetail2Activity.class);
                    intent.putExtra("title",data.getFeature_title());
                    intent.putExtra("description",data.getFeature_description());
                    intent.putExtra("image",data.getFeature_image());
                    startActivity(intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txt_shopname;
            private ImageView imageView;
            private CardView container;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_shopname = itemView.findViewById(R.id.txt_shopname);
                imageView = itemView.findViewById(R.id.imageView);
                container = itemView.findViewById(R.id.container);

            }
        }
    }

}
