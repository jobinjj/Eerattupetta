package com.techpakka.eerattupetta.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.techpakka.eerattupetta.ItemOffsetDecoration;
import com.techpakka.eerattupetta.Model.Data;
import com.techpakka.eerattupetta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HospitalFragment extends Fragment {
    Adapter adapter;
    ArrayList<Data> list = new ArrayList<>();

    private RecyclerView recyclerView;

    public HospitalFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hospital, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new Adapter(getHospitalData());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private List<Data> getHospitalData() {
        String timing = loadJSONFromAsset();
        try {
            JSONObject jsonObj = new JSONObject(timing);
            JSONArray contacts = jsonObj.getJSONArray("hospital");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                Data data = new Data();
                data.setHospital_name(c.getString("name_malayalam"));
                data.setHospital_location(c.getString("place"));
                data.setHospital_phone(c.getString("phone"));
                list.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
return list;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("hospital.json");
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
        private List<Data> list;
        public Adapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_emergency, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_name.setText(data.getHospital_name());
            myViewHolder.txt_location.setText(data.getHospital_location());
            myViewHolder.txt_mobile.setText(data.getHospital_phone());
            myViewHolder.img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri number = Uri.parse("tel:" + data.getHospital_phone());
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
            ImageView img_call;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_name = itemView.findViewById(R.id.txt_name5);
                txt_location = itemView.findViewById(R.id.txt_location4);
                txt_mobile = itemView.findViewById(R.id.txt_emergency_mobile);
                img_call = itemView.findViewById(R.id.img_call);

            }
        }
    }
}
