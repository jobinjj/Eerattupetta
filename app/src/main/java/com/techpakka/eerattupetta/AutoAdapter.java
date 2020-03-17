package com.techpakka.eerattupetta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.techpakka.eerattupetta.Model.Data;

import java.util.List;

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.MyViewHolder>{
    List<Data> list;
    Context context;
    public AutoAdapter(List<Data> list, Context context){
        this.context = context;
        this.list = list;
        Log.d("test","entered");
    }
    @NonNull
    @Override
    public AutoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_auto, viewGroup, false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoAdapter.MyViewHolder myViewHolder, int i) {

        Data data = list.get(i);
        Toast.makeText(context, data.getAuto_location(), Toast.LENGTH_SHORT).show();
        myViewHolder.txt_home.setText(data.getAuto_location());
         }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_home;
        public MyViewHolder(View itemView){
            super(itemView);
            txt_home = itemView.findViewById(R.id.txt_location4);

        }

    }
}
