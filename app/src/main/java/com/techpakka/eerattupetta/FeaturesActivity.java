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
import com.google.android.gms.common.Feature;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.Model.Data;
import com.techpakka.eerattupetta.Shopukal.Shopukal;

import java.util.ArrayList;
import java.util.List;

public class FeaturesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        setUpFlipView();
        adapter = new Adapter(getFeatures());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(FeaturesActivity.this, R.dimen.item_offset);
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
    private List<Data> getFeatures() {
        ArrayList<Data> list = new ArrayList<>();
        Data data5 = new Data();
        data5.setFeature_title("ട്രെന്റ്സ് ON ഈരാറ്റുപേട്ട");
        data5.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/trends.jpeg?alt=media&token=adb34060-276b-4a7b-bfa9-d0874dba0e18");
        list.add(data5);
        Data data = new Data();
        data.setFeature_title("ഈരാറ്റുപേട്ടക്കാരൻ");
        data.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/eerattupetta.jpeg?alt=media&token=3c636813-65fb-405f-ae83-1ceea23de379");
        list.add(data);
        Data data8 = new Data();
        data8.setFeature_title("നസീബും പാമ്പുകളും");
        data8.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/naseeb.jpeg?alt=media&token=32cf8a2e-193c-43ac-b654-23dbf8fa49ef");
        list.add(data8);
        Data data2 = new Data();
        data2.setFeature_title("അണ്ണച്ചിമാരുടെ യാത്രകൾ");
        data2.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/annachi.jpeg?alt=media&token=86ce10f1-d46e-41f9-a54e-a173d11906f4");
        list.add(data2);
        Data data3 = new Data();
        data3.setFeature_title("വെള്ളി വെളിച്ചം");
        data3.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/velli.jpeg?alt=media&token=2b886f53-7bae-4bdf-bc83-af8a7cc9c122");
        list.add(data3);
        Data data4 = new Data();
        data4.setFeature_title("ഗൾഫ് കഥകൾ");
        data4.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/gulf.jpeg?alt=media&token=cbc4fa28-bb9f-40e8-9211-bbeb97e69c5e");
        list.add(data4);
        Data data6 = new Data();
        data6.setFeature_title("തോമയുടെ യാത്ര");
        data6.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/thoma.jpeg?alt=media&token=2a3c0f58-d554-4141-aa57-3a194d394b02");
        list.add(data6);
        Data data7 = new Data();
        data7.setFeature_title("മൺമറഞ്ഞ താരകങ്ങൾ");
        data7.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/manmaranja.jpeg?alt=media&token=95180935-460e-495c-a04b-21f203e9d572");
        list.add(data7);
        Data data9 = new Data();
        data9.setFeature_title("തായിടെ കൃതികൾ");
        data9.setFeature_image("https://firebasestorage.googleapis.com/v0/b/myapp-18bae.appspot.com/o/thayi.jpeg?alt=media&token=ca66d5ab-6ab4-49b4-bf87-ca6037a74b15");
        list.add(data9);
        return list;
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

        List<Data> list;
        public Adapter(List<Data> list) {
            this.list = list;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_shop, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_shopname.setText(data.getFeature_title());
            Glide.with(getApplicationContext()).setDefaultRequestOptions(Constants.getThumbnail()).load(data.getFeature_image()).into(myViewHolder.imageView);
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FeaturesActivity.this,FeaturesDetailActivity.class);
                    intent.putExtra("feature",data.getFeature_title());
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
