package com.techpakka.eerattupetta;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmergencyActivity extends AppCompatActivity {

    private ArrayList<Data> list;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        setUpFlipView();
        Adapter  adapter  = new Adapter(getData());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(EmergencyActivity.this, R.dimen.item_offset);
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
    private List<Data> getData() {
        list = new ArrayList<>();
        String emergency2 = getString(R.string.emergency);

        String emergency = "{ \"emergency\": [\n" +
                "    {\n" +
                "        \"id\": \"15\",\n" +
                "        \"name_english\": \"001 Ambulance - Erattupetta Municipality\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - ഈരാറ്റുപേട്ട മുൻസിപ്പാലിറ്റി \",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"8086844389\",\n" +
                "        \"created_at\": \"2018-07-17 18:59:25\",\n" +
                "        \"updated_at\": \"2018-10-09 20:27:58\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"9\",\n" +
                "        \"name_english\": \"001 Ambulance - Kanivu\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - കനിവ്\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9207006385\",\n" +
                "        \"created_at\": \"2018-06-26 17:48:10\",\n" +
                "        \"updated_at\": \"2018-10-09 20:28:06\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"6\",\n" +
                "        \"name_english\": \"001 Ambulance - Karuna\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - കരുണ \",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9562466566\",\n" +
                "        \"created_at\": \"2018-06-26 17:44:35\",\n" +
                "        \"updated_at\": \"2018-10-09 20:28:15\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"11\",\n" +
                "        \"name_english\": \"001 Ambulance - PMC Hospital \",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - പി എം സി ആശുപത്രി \",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"04822274636\",\n" +
                "        \"created_at\": \"2018-06-26 17:50:55\",\n" +
                "        \"updated_at\": \"2018-10-09 20:28:23\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"10\",\n" +
                "        \"name_english\": \"001 Ambulance - RIMS Hospital\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - റിംസ്\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"7558880926\",\n" +
                "        \"created_at\": \"2018-06-26 17:50:24\",\n" +
                "        \"updated_at\": \"2018-10-09 20:28:38\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"8\",\n" +
                "        \"name_english\": \"001 Ambulance - Rishi Hospital\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - ഋഷി ആശുപത്രി\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9400189000\",\n" +
                "        \"created_at\": \"2018-06-26 17:47:43\",\n" +
                "        \"updated_at\": \"2018-10-09 20:28:46\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"7\",\n" +
                "        \"name_english\": \"001 Ambulance -Youth League\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - യൂത്ത് ലീഗ് \",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9020885544\",\n" +
                "        \"created_at\": \"2018-06-26 17:47:09\",\n" +
                "        \"updated_at\": \"2018-10-09 20:29:35\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"2\",\n" +
                "        \"name_english\": \"001 FIRE and RESCUE STATION Erattupetta\",\n" +
                "        \"name_malayalam\": \"ഫയർ  റെസ്ക്യൂ സ്റ്റേഷൻ\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"04822274700\",\n" +
                "        \"created_at\": \"2018-06-26 16:39:25\",\n" +
                "        \"updated_at\": \"2018-10-09 20:29:43\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"3\",\n" +
                "        \"name_english\": \"001 KSEB Office, Erattupetta\",\n" +
                "        \"name_malayalam\": \"കെ എസ് ഇ ബി\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"04822 272 448\",\n" +
                "        \"created_at\": \"2018-06-26 16:40:39\",\n" +
                "        \"updated_at\": \"2018-10-09 20:29:53\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"1\",\n" +
                "        \"name_english\": \"001 Police Station Erattupetta\",\n" +
                "        \"name_malayalam\": \"പോലീസ് സ്റ്റേഷൻ\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"04822272228\",\n" +
                "        \"created_at\": \"2018-06-26 16:38:12\",\n" +
                "        \"updated_at\": \"2018-10-09 20:30:13\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"16\",\n" +
                "        \"name_english\": \"001z Ambulance - SBI Melukavu\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - എസ് ബി ഐ \",\n" +
                "        \"place\": \"മേലുകാവ്\",\n" +
                "        \"phone\": \"9544277700\",\n" +
                "        \"created_at\": \"2018-07-17 19:00:05\",\n" +
                "        \"updated_at\": \"2018-10-09 20:32:09\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"13\",\n" +
                "        \"name_english\": \"002 Ambulance - Poonjar CSS\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - സി എസ് എസ് \",\n" +
                "        \"place\": \"പൂഞ്ഞാർ\",\n" +
                "        \"phone\": \"9747550008\",\n" +
                "        \"created_at\": \"2018-07-17 18:57:54\",\n" +
                "        \"updated_at\": \"2018-10-09 20:28:31\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"5\",\n" +
                "        \"name_english\": \"002 KSEB Office, Poonjar\",\n" +
                "        \"name_malayalam\": \"കെ എസ് ഇ ബി\",\n" +
                "        \"place\": \"പൂഞ്ഞാർ\",\n" +
                "        \"phone\": \"04822 274 658\",\n" +
                "        \"created_at\": \"2018-06-26 16:44:23\",\n" +
                "        \"updated_at\": \"2018-10-09 20:31:11\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4\",\n" +
                "        \"name_english\": \"002 KSEB teekoy\",\n" +
                "        \"name_malayalam\": \"കെ എസ് ഇ ബി \",\n" +
                "        \"place\": \"തീക്കോയി \",\n" +
                "        \"phone\": \"04822 281 800\",\n" +
                "        \"created_at\": \"2018-06-26 16:43:49\",\n" +
                "        \"updated_at\": \"2018-10-09 20:30:02\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"14\",\n" +
                "        \"name_english\": \"003 Ambulance - I Care\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - ഐ കെയർ\",\n" +
                "        \"place\": \"പ്ലാശനൽ\",\n" +
                "        \"phone\": \"9656623307\",\n" +
                "        \"created_at\": \"2018-07-17 18:58:42\",\n" +
                "        \"updated_at\": \"2018-10-09 20:27:47\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"12\",\n" +
                "        \"name_english\": \"003 Ambulance - Thidanadu Panjayath\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - തിടനാട് പഞ്ചായത്ത്\",\n" +
                "        \"place\": \"തിടനാട്\",\n" +
                "        \"phone\": \"9446195000\",\n" +
                "        \"created_at\": \"2018-07-07 13:18:10\",\n" +
                "        \"updated_at\": \"2018-10-09 20:29:19\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"17\",\n" +
                "        \"name_english\": \"006 nanmakoottam\",\n" +
                "        \"name_malayalam\": \"നന്മകൂട്ടം(അഷ്\u200Cറഫ്\u200C കുട്ടി )\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9961019292\",\n" +
                "        \"created_at\": \"2018-09-16 11:19:51\",\n" +
                "        \"updated_at\": \"2018-10-09 20:30:37\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"18\",\n" +
                "        \"name_english\": \"006 naseeb\",\n" +
                "        \"name_malayalam\": \"പാമ്പ് പിടുത്തം (നസീബ് പടിപ്പുരക്കല്\u200D)\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9744753660\",\n" +
                "        \"created_at\": \"2018-09-16 11:23:24\",\n" +
                "        \"updated_at\": \"2018-10-09 20:30:52\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"19\",\n" +
                "        \"name_english\": \"010 Ambulance - Dennis\",\n" +
                "        \"name_malayalam\": \"ആംബുലൻസ് - ഡെന്നിസ്\u200C\",\n" +
                "        \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
                "        \"phone\": \"9446600062\",\n" +
                "        \"created_at\": \"2018-09-20 22:35:42\",\n" +
                "        \"updated_at\": \"2018-10-09 20:31:03\"\n" +
                "    }\n" +
                "] }\n";
        try {
            JSONObject jsonObj = new JSONObject(emergency);
            JSONArray contacts = jsonObj.getJSONArray("emergency");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                Data data = new Data();
                data.setEmergency_title(c.getString("name_malayalam"));
                data.setEmergency_location(c.getString("place"));
                data.setEmergency_number(c.getString("phone"));
                list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                    .inflate(R.layout.content_emergency, viewGroup, false);

            return new Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = List.get(i);
            myViewHolder.txt_emergency_title.setText(data.getEmergency_title());
            myViewHolder.txt_emergency_location.setText(data.getEmergency_location());
            myViewHolder.txt_emergency_mobile.setText(data.getEmergency_number());
            myViewHolder.img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri number = Uri.parse("tel:" + data.getEmergency_number());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txt_emergency_title,txt_emergency_location,txt_emergency_mobile;
            private ImageView img_call;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_emergency_title = itemView.findViewById(R.id.txt_name5);
                txt_emergency_location = itemView.findViewById(R.id.txt_location4);
                txt_emergency_mobile = itemView.findViewById(R.id.txt_emergency_mobile);
                img_call = itemView.findViewById(R.id.img_call);

            }
        }
    }
}
