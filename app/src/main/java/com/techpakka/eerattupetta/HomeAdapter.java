package com.techpakka.eerattupetta;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techpakka.eerattupetta.Model.Data;
import com.techpakka.eerattupetta.Shopukal.Shopukal;
import com.techpakka.eerattupetta.Vahanangal.Vahanangal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
    private List<Data> list;
    private Context context;
    private ArrayList<Data> adlist;
    Random random = new Random();
    public HomeAdapter(List<Data> List, Context context, ArrayList<Data> adlist){
        this.list = List;
        this.context = context;
        this.adlist = adlist;
    }
    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_home, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder myViewHolder, int i) {
        Data data = list.get(i);
        myViewHolder.imageView.setImageResource(data.getImg_path());
        myViewHolder.txt_home.setText(data.getHome());
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0){
                    Intent intent = new Intent(context,VarthaActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 1){
                    Intent intent = new Intent(context,SecondHandActivity.class);
                    context.startActivity(intent);
                }
                if (i == 2){
                    Intent intent = new Intent(context,JobActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 3){
                    Intent intent = new Intent(context,FeaturesActivity.class);
                    context.startActivity(intent);
                }
                if (i == 4){
                    Intent intent = new Intent(context,TrollActivtiy.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 5){
                    Intent intent = new Intent(context,Ariyippukal.class);
                    context.startActivity(intent);
                }
                if (i == 6){
                    Intent intent = new Intent(context,BusActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 7){
                    Intent intent = new Intent(context,EmergencyActivity.class);
                    context.startActivity(intent);
                }
                if (i == 8){
                    Intent intent = new Intent(context,VideoActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 9){
                    Intent intent = new Intent(context,AutoActivity.class);
                    context.startActivity(intent);
                }
                if (i == 10){
                    Intent intent = new Intent(context,Vahanangal.class);
                    context.startActivity(intent);
                }
                if (i == 11){
                    Intent intent = new Intent(context,Shopukal.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 12){
                    Intent intent = new Intent(context,SthapanangalActivity.class);
                    context.startActivity(intent);
                }
                if (i == 13){
                    Intent intent = new Intent(context,SevanamActivity.class);
                    context.startActivity(intent);
                }
                if (i == 14){
                    Intent intent = new Intent(context,ThozhilalikalActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 15){
                    Intent intent = new Intent(context,BloodBankActivity.class);
                    context.startActivity(intent);
                }
                if (i == 16){
                    Intent intent = new Intent(context,HospitalHomeActivity.class);
                    context.startActivity(intent);
                }
                if (i == 17){
                    Intent intent = new Intent(context,PoliticianActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 18){
                    Intent intent = new Intent(context,MediaActivity.class);
                    context.startActivity(intent);
                }
                if (i == 19){
                    Intent intent = new Intent(context,TourismActivity.class);
                    context.startActivity(intent);
                }
                if (i == 20){
                    Intent intent = new Intent(context,OthersActivity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
                if (i == 21){
                    Intent intent = new Intent(context,FeedbackActivity.class);
                    context.startActivity(intent);
                }
                if (i == 22){
                    Intent intent = new Intent(context,MarriageBeaurauActivity.class);
                    context.startActivity(intent);
                }
                if (i == 23){
                    Intent intent = new Intent(context,AboutAcitvity.class);
                    if (adlist.size() > 0){
                        Data data = adlist.get(random.nextInt(4));
                        intent.putExtra("adurl",data.getLarge_adurl());
                    }
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txt_home;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txt_home = itemView.findViewById(R.id.txt_home);

        }
    }
}

