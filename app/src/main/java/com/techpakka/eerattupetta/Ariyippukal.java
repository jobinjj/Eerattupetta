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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.List;

public class Ariyippukal extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Adapter adapter;
    private ProgressBar progressBar;
    int index = 0;
    private ViewFlipper view_flipper;
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariyippukal);
        setUpFlipView();
        progressBar = findViewById(R.id.progressBar);
        adapter = new Adapter(getServerData());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(Ariyippukal.this, R.dimen.item_offset);
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
    private List<Data> getServerData() {
        ArrayList<Data> list = new ArrayList<>();
        db.collection("Ariyippukal").orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setAriyipukal_title(document.getString("title"));
                            data.setAriyipukal_description(document.getString("description"));
                            data.setAriyipukal_date(document.getString("date"));
                            list.add(data);

                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });
        return list;
    }

    private List<Data> getData() {
        ArrayList<Data> list = new ArrayList<>();
        Data data = new Data();
        data.setAriyipukal_title("Muncipality ambulance erattupetta");
        data.setAriyipukal_description("Muncipality ambulance erattupetta Muncipality ambulance erattupetta");
        data.setAriyipukal_date("03-06-1998");
        list.add(data);
        Data data1 = new Data();
        data1.setAriyipukal_title("Muncipality ambulance erattupetta");
        data1.setAriyipukal_description("Muncipality ambulance erattupetta Muncipality ambulance erattupetta ");
        data1.setAriyipukal_date("03-06-1998");
        list.add(data1);
        return list;
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
                    .inflate(R.layout.content_ariyippukal, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = List.get(i);
            myViewHolder.txt_description.setText(data.getAriyipukal_description());
            myViewHolder.txt_date.setText(data.getAriyipukal_date());
            myViewHolder.txt_title.setText(data.getAriyipukal_title());
            myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "*" + data.getAriyipukal_title() + "*" + "\n" + "\n" + data.getAriyipukal_description() +"\n" +"\n" +
                                    "from nammal erattupettakkar app" );
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView txt_title;
            private TextView txt_description;
            private TextView txt_date;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.img_call);
                txt_title = itemView.findViewById(R.id.txt_name5);
                txt_date= itemView.findViewById(R.id.txt_ariyipu_date);
                txt_description= itemView.findViewById(R.id.txt_location4);
            }
        }
    }

}
