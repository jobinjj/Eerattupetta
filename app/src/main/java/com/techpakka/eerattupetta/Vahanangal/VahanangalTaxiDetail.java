package com.techpakka.eerattupetta.Vahanangal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techpakka.eerattupetta.ItemOffsetDecoration;
import com.techpakka.eerattupetta.Model.Data;
import com.techpakka.eerattupetta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VahanangalTaxiDetail extends AppCompatActivity {

    private RecyclerView bus_recycler;
    private RecyclerView travellar_recycler;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];
    private Adapter travellaradapter;
    private Adapter busadapter;
    private String type_id;
    String data = "{\n" +
            "    \"vehicles\": [\n" +
            "        {\n" +
            "            \"id\": \"3\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Abhinav travellers\",\n" +
            "            \"name_malayalam\": \"അഭിനവ് ട്രാവൽസ്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "            \"phone\": \"9496803678\",\n" +
            "            \"created_at\": \"2018-06-29 17:40:17\",\n" +
            "            \"updated_at\": \"2018-09-16 07:49:10\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"53\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"abhul majeed\",\n" +
            "            \"name_malayalam\": \"അബ്\u200Cദുൾ മജീദ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9605385186\",\n" +
            "            \"created_at\": \"2018-09-19 18:13:54\",\n" +
            "            \"updated_at\": \"2018-09-19 18:13:54\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"76\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"abi mon\",\n" +
            "            \"name_malayalam\": \"അബി മോൻ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847790488\",\n" +
            "            \"created_at\": \"2018-09-20 09:51:18\",\n" +
            "            \"updated_at\": \"2018-09-20 09:51:18\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"27\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Ajith\",\n" +
            "            \"name_malayalam\": \"അജിത്ത് (ഇന്നോവ)9495162522\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "            \"phone\": \"9495162522\",\n" +
            "            \"created_at\": \"2018-09-15 13:35:06\",\n" +
            "            \"updated_at\": \"2018-09-15 13:35:06\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"34\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"AL AMEEN  Transport\",\n" +
            "            \"name_malayalam\": \"അൽ അമീൻ ട്രാൻസ്\u200Cപോർട് \",\n" +
            "            \"place\": \" ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447596767\",\n" +
            "            \"created_at\": \"2018-09-15 17:40:07\",\n" +
            "            \"updated_at\": \"2018-09-15 17:40:07\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"60\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"allappi\",\n" +
            "            \"name_malayalam\": \"അല്ലപ്പി(407) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"7034256204\",\n" +
            "            \"created_at\": \"2018-09-20 08:48:22\",\n" +
            "            \"updated_at\": \"2018-09-20 08:48:22\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"43\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"ameen \",\n" +
            "            \"name_malayalam\": \"അമീൻ നാകുന്നത്ത് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947386382\",\n" +
            "            \"created_at\": \"2018-09-17 16:17:26\",\n" +
            "            \"updated_at\": \"2018-09-17 16:17:26\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"33\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Ameen kaduvamuzhi\",\n" +
            "            \"name_malayalam\": \"അമീൻ  (ഇന്നോവ )\",\n" +
            "            \"place\": \"കടുവാമുഴി,ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9605544369\",\n" +
            "            \"created_at\": \"2018-09-15 17:22:39\",\n" +
            "            \"updated_at\": \"2018-09-16 08:47:23\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"81\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"amees holidays\",\n" +
            "            \"name_malayalam\": \"ആമീസ് ഹോളിഡേയ്\u200Cസ് \",\n" +
            "            \"place\": \"പിണ്ണാക്കനാട് \",\n" +
            "            \"phone\": \"9847676477\",\n" +
            "            \"created_at\": \"2018-09-20 09:58:00\",\n" +
            "            \"updated_at\": \"2018-11-03 10:01:03\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"82\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"amees holidays\",\n" +
            "            \"name_malayalam\": \"ആമീസ് ഹോളിഡേയ്\u200Cസ്  (17 seat)\",\n" +
            "            \"place\": \"പിണ്ണാക്കനാട് \",\n" +
            "            \"phone\": \"9847676477\",\n" +
            "            \"created_at\": \"2018-09-20 09:59:14\",\n" +
            "            \"updated_at\": \"2018-09-20 09:59:14\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"83\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"amees holidays\",\n" +
            "            \"name_malayalam\": \"ആമീസ് ഹോളിഡേയ്\u200Cസ്  (12 seat)\",\n" +
            "            \"place\": \"പിണ്ണാക്കനാട് \",\n" +
            "            \"phone\": \"9847676477\",\n" +
            "            \"created_at\": \"2018-09-20 10:00:15\",\n" +
            "            \"updated_at\": \"2018-09-20 10:00:15\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"84\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"amees holidays\",\n" +
            "            \"name_malayalam\": \"ആമീസ് ഹോളിഡേയ്\u200Cസ്  (ഇന്നോവ) \",\n" +
            "            \"place\": \"പിണ്ണാക്കനാട് \",\n" +
            "            \"phone\": \"9947133418\",\n" +
            "            \"created_at\": \"2018-09-20 10:02:05\",\n" +
            "            \"updated_at\": \"2018-09-20 10:02:05\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"134\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"AMMA TRAVELS PALA \",\n" +
            "            \"name_malayalam\": \"അമ്മ ട്രാവല്\u200Dസ് ( ഇന്നോവ,ക്രിസ്റ്റാ ) \",\n" +
            "            \"place\": \"പാലാ \",\n" +
            "            \"phone\": \"9744032158\",\n" +
            "            \"created_at\": \"2018-11-09 08:55:57\",\n" +
            "            \"updated_at\": \"2018-11-09 08:55:57\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"24\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Anees\",\n" +
            "            \"name_malayalam\": \"അനീസ് \",\n" +
            "            \"place\": \"നടക്കൽ, ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744958814\",\n" +
            "            \"created_at\": \"2018-09-12 15:08:32\",\n" +
            "            \"updated_at\": \"2018-09-13 18:20:12\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"13\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"ANil\",\n" +
            "            \"name_malayalam\": \"അനിൽ\",\n" +
            "            \"place\": \"കരിയിലകാനം\",\n" +
            "            \"phone\": \"8547469627\",\n" +
            "            \"created_at\": \"2018-09-04 19:02:30\",\n" +
            "            \"updated_at\": \"2018-09-04 19:02:30\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"5\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Annmaria Travels \",\n" +
            "            \"name_malayalam\": \"അന്നമറിയ ട്രാവൽസ്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446429466\",\n" +
            "            \"created_at\": \"2018-06-29 18:09:58\",\n" +
            "            \"updated_at\": \"2018-06-29 18:09:58\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"6\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Annmaria Travels \",\n" +
            "            \"name_malayalam\": \"അന്നമറിയ ട്രാവൽസ്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446429466\",\n" +
            "            \"created_at\": \"2018-06-29 18:09:59\",\n" +
            "            \"updated_at\": \"2018-06-29 18:09:59\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"45\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"11\",\n" +
            "            \"name_english\": \"ANoop\",\n" +
            "            \"name_malayalam\": \"അനൂപ് \",\n" +
            "            \"place\": \"കളത്തുകടവ്\",\n" +
            "            \"phone\": \"9961310181\",\n" +
            "            \"created_at\": \"2018-09-18 12:05:13\",\n" +
            "            \"updated_at\": \"2018-09-18 12:05:13\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"73\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"anoop\",\n" +
            "            \"name_malayalam\": \"അനൂപ്(ടോറസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847908597\",\n" +
            "            \"created_at\": \"2018-09-20 09:48:45\",\n" +
            "            \"updated_at\": \"2018-09-20 09:48:45\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"8\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Anugrah\",\n" +
            "            \"name_malayalam\": \"അനുഗ്രഹ് ട്രാവൽസ് - 1\",\n" +
            "            \"place\": \"തീക്കോയി\",\n" +
            "            \"phone\": \"9048480142\",\n" +
            "            \"created_at\": \"2018-08-07 17:42:10\",\n" +
            "            \"updated_at\": \"2018-08-07 17:43:31\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"9\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"ANUgrah\",\n" +
            "            \"name_malayalam\": \"അനുഗ്രഹ് ട്രാവൽസ് -2\",\n" +
            "            \"place\": \"തീക്കോയി\",\n" +
            "            \"phone\": \"9947200507\",\n" +
            "            \"created_at\": \"2018-08-07 17:43:06\",\n" +
            "            \"updated_at\": \"2018-08-07 17:43:06\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"80\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"arebiyan dheeya\",\n" +
            "            \"name_malayalam\": \"അറേബ്യൻ ദിയ (സിയാദ്)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9526643621 \",\n" +
            "            \"created_at\": \"2018-09-20 09:55:16\",\n" +
            "            \"updated_at\": \"2018-09-20 09:55:16\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"101\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"ashraf kandathil\",\n" +
            "            \"name_malayalam\": \"അഷറഫ് കണ്ടതിൽ  (വിക്രം)  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446204511\",\n" +
            "            \"created_at\": \"2018-09-25 17:28:20\",\n" +
            "            \"updated_at\": \"2018-09-25 17:28:20\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"42\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"Bharathbenz Lorry\",\n" +
            "            \"name_malayalam\": \"ഷെമീര്\u200D\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "            \"phone\": \"09447110610\",\n" +
            "            \"created_at\": \"2018-09-17 16:14:57\",\n" +
            "            \"updated_at\": \"2018-09-17 16:14:57\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"74\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"bijili\",\n" +
            "            \"name_malayalam\": \"ബിജിലി \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744601722\",\n" +
            "            \"created_at\": \"2018-09-20 09:49:25\",\n" +
            "            \"updated_at\": \"2018-09-20 09:49:25\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"121\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"booniy  \",\n" +
            "            \"name_malayalam\": \"ബിനോയി (17 സീറ്റ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"7558002901\",\n" +
            "            \"created_at\": \"2018-11-03 09:45:21\",\n" +
            "            \"updated_at\": \"2018-11-03 09:45:21\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"124\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Booniy  \",\n" +
            "            \"name_malayalam\": \"ബിനോയി (സീറ്റ്-26) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"7558002901\",\n" +
            "            \"created_at\": \"2018-11-03 09:57:21\",\n" +
            "            \"updated_at\": \"2018-11-03 09:57:21\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"128\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Booniy  \",\n" +
            "            \"name_malayalam\": \"ബിനോയി (34  മിനി ബസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"7558002901\",\n" +
            "            \"created_at\": \"2018-11-03 10:02:55\",\n" +
            "            \"updated_at\": \"2018-11-03 10:02:55\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"130\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Booniy  \",\n" +
            "            \"name_malayalam\": \"ബിനോയി (49 സീറ്റ് ബസ് ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"7558002901\",\n" +
            "            \"created_at\": \"2018-11-03 10:04:13\",\n" +
            "            \"updated_at\": \"2018-11-03 10:04:13\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"1\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"CCM travellers\",\n" +
            "            \"name_malayalam\": \"സി സി എം ട്രാവൽസ്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446796101\",\n" +
            "            \"created_at\": \"2018-06-29 17:35:49\",\n" +
            "            \"updated_at\": \"2018-06-29 17:35:49\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"46\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"Century Cargo Movers  \",\n" +
            "            \"name_malayalam\": \"സെഞ്ച്വറി കാര്\u200Dഗോ മൂവേർസ്  (407)\",\n" +
            "            \"place\": \"മുട്ടം ജങ്ഷൻ  \",\n" +
            "            \"phone\": \"9961401210\",\n" +
            "            \"created_at\": \"2018-09-19 15:55:32\",\n" +
            "            \"updated_at\": \"2018-09-19 15:55:32\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"105\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"Dileep \",\n" +
            "            \"name_malayalam\": \"ദിലീപ് ( വിക്രം ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447120045\",\n" +
            "            \"created_at\": \"2018-09-28 11:28:33\",\n" +
            "            \"updated_at\": \"2018-09-28 11:28:33\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"114\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Dileep  \",\n" +
            "            \"name_malayalam\": \"ദിലീപ്  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447952333\",\n" +
            "            \"created_at\": \"2018-11-03 09:28:25\",\n" +
            "            \"updated_at\": \"2018-11-03 09:28:25\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"38\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"esa \",\n" +
            "            \"name_malayalam\": \"ഈസാ (407) \",\n" +
            "            \"place\": \"മുട്ടം ജങ്ഷൻ  \",\n" +
            "            \"phone\": \"9526908301\",\n" +
            "            \"created_at\": \"2018-09-16 12:57:03\",\n" +
            "            \"updated_at\": \"2018-09-16 12:57:03\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"23\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Faisal\",\n" +
            "            \"name_malayalam\": \"ഫൈസൽ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744801810\",\n" +
            "            \"created_at\": \"2018-09-12 15:07:30\",\n" +
            "            \"updated_at\": \"2018-09-12 15:07:30\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"44\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"Faisal\",\n" +
            "            \"name_malayalam\": \"ഫൈസൽ\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947804235\",\n" +
            "            \"created_at\": \"2018-09-17 20:45:16\",\n" +
            "            \"updated_at\": \"2018-09-17 20:45:16\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"48\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"faisal \",\n" +
            "            \"name_malayalam\": \"ഫൈസൽ \",\n" +
            "            \"place\": \"തോട്ടുമുക്ക്\",\n" +
            "            \"phone\": \"+91 99478 04235\",\n" +
            "            \"created_at\": \"2018-09-19 17:48:04\",\n" +
            "            \"updated_at\": \"2018-09-19 17:48:04\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"96\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"Faris \",\n" +
            "            \"name_malayalam\": \"ഫാരിസ്  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9400233718\",\n" +
            "            \"created_at\": \"2018-09-24 21:35:37\",\n" +
            "            \"updated_at\": \"2018-09-24 21:35:37\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"100\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Genesis holidays\",\n" +
            "            \"name_malayalam\": \"Genesis ഹോളിഡേയ്\u200Cസ് ( 26 സീറ്റ് ) \",\n" +
            "            \"place\": \"പാലാ  \",\n" +
            "            \"phone\": \"7293336687\",\n" +
            "            \"created_at\": \"2018-09-25 17:18:05\",\n" +
            "            \"updated_at\": \"2018-09-25 17:18:05\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"67\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"haneefa\",\n" +
            "            \"name_malayalam\": \"ഹനീഫ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947128758\",\n" +
            "            \"created_at\": \"2018-09-20 09:43:05\",\n" +
            "            \"updated_at\": \"2018-09-20 09:43:05\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"55\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"haris\",\n" +
            "            \"name_malayalam\": \"ഹാരിസ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446219439\",\n" +
            "            \"created_at\": \"2018-09-19 18:15:07\",\n" +
            "            \"updated_at\": \"2018-09-19 18:15:07\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"119\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Haris  \",\n" +
            "            \"name_malayalam\": \"ഹാരീസ് (17 സീറ്റ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744388736\",\n" +
            "            \"created_at\": \"2018-11-03 09:44:13\",\n" +
            "            \"updated_at\": \"2018-11-03 09:44:13\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"126\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Haris  \",\n" +
            "            \"name_malayalam\": \"ഹാരിസ് (34  മിനി ബസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744388736\",\n" +
            "            \"created_at\": \"2018-11-03 09:59:47\",\n" +
            "            \"updated_at\": \"2018-11-03 09:59:47\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"37\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"hubail\",\n" +
            "            \"name_malayalam\": \"ഹുബൈല്\u200D \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947156701\",\n" +
            "            \"created_at\": \"2018-09-16 11:38:03\",\n" +
            "            \"updated_at\": \"2018-09-16 11:38:03\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"111\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Ibrahim  \",\n" +
            "            \"name_malayalam\": \"ഇബ്രാഹിം  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9388895301\",\n" +
            "            \"created_at\": \"2018-11-03 09:26:36\",\n" +
            "            \"updated_at\": \"2018-11-03 09:26:36\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"58\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"ismail\",\n" +
            "            \"name_malayalam\": \"ഇസ്മായിൽ (407)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447846750\",\n" +
            "            \"created_at\": \"2018-09-20 08:47:04\",\n" +
            "            \"updated_at\": \"2018-09-20 08:47:04\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"120\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Jalal  \",\n" +
            "            \"name_malayalam\": \"ജലാൽ (17 സീറ്റ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446756895\",\n" +
            "            \"created_at\": \"2018-11-03 09:44:44\",\n" +
            "            \"updated_at\": \"2018-11-03 09:44:44\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"68\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"jaleel\",\n" +
            "            \"name_malayalam\": \"ജലീൽ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947256451\",\n" +
            "            \"created_at\": \"2018-09-20 09:43:41\",\n" +
            "            \"updated_at\": \"2018-09-20 09:43:41\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"131\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Jithin (Vazhayil) \",\n" +
            "            \"name_malayalam\": \"ജിതിൻ വാഴയിൽ (49 സീറ്റ് ബസ് ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446204293\",\n" +
            "            \"created_at\": \"2018-11-03 10:04:51\",\n" +
            "            \"updated_at\": \"2018-11-03 10:04:51\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"92\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"khader\",\n" +
            "            \"name_malayalam\": \"ഖാദർ (ലൈലാന്റ് ദോസ്ത്)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847693707\",\n" +
            "            \"created_at\": \"2018-09-23 17:44:09\",\n" +
            "            \"updated_at\": \"2018-09-23 17:44:09\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"93\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Khader  Dost   \",\n" +
            "            \"name_malayalam\": \"ഖാദർ ( ദോസ്ത്)\",\n" +
            "            \"place\": \"നടക്കൽ \",\n" +
            "            \"phone\": \"9847693707\",\n" +
            "            \"created_at\": \"2018-09-23 19:07:44\",\n" +
            "            \"updated_at\": \"2018-09-23 19:07:44\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"85\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"kk rasheed\",\n" +
            "            \"name_malayalam\": \"കെ കെ റഷീദ് (വിക്രം)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447258486\",\n" +
            "            \"created_at\": \"2018-09-20 10:32:35\",\n" +
            "            \"updated_at\": \"2018-09-20 10:32:35\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"47\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Lechoos Holidays\",\n" +
            "            \"name_malayalam\": \"ലച്ചൂസ് ഹോളിഡേയ്\u200Cസ്  (17seat)\",\n" +
            "            \"place\": \"പിണ്ണാക്കനാട് \",\n" +
            "            \"phone\": \"9744017949\",\n" +
            "            \"created_at\": \"2018-09-19 16:37:48\",\n" +
            "            \"updated_at\": \"2018-09-19 16:37:48\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"77\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"letheef\",\n" +
            "            \"name_malayalam\": \"ലെത്തിഫ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"7510906371\",\n" +
            "            \"created_at\": \"2018-09-20 09:51:51\",\n" +
            "            \"updated_at\": \"2018-09-20 09:51:51\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"79\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"maheen\",\n" +
            "            \"name_malayalam\": \"മാഹീൻ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744387658\",\n" +
            "            \"created_at\": \"2018-09-20 09:53:00\",\n" +
            "            \"updated_at\": \"2018-09-20 09:53:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"7\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"mahin \",\n" +
            "            \"name_malayalam\": \"മാഹിൻ\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744387656\",\n" +
            "            \"created_at\": \"2018-06-29 18:12:51\",\n" +
            "            \"updated_at\": \"2018-06-29 18:12:51\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"28\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"mahin  \",\n" +
            "            \"name_malayalam\": \"മാഹിൻ   (ഗുഡ്സ് ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "            \"phone\": \"9744387656\",\n" +
            "            \"created_at\": \"2018-09-15 13:38:57\",\n" +
            "            \"updated_at\": \"2018-09-15 13:38:57\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"41\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"mahin\",\n" +
            "            \"name_malayalam\": \"മാഹിൻ മുസാഫിർ മാക്സിമോ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847909651\",\n" +
            "            \"created_at\": \"2018-09-17 12:47:27\",\n" +
            "            \"updated_at\": \"2018-09-17 12:47:27\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"72\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"masoor\",\n" +
            "            \"name_malayalam\": \"മൻസൂർ(ടോറസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446922151\",\n" +
            "            \"created_at\": \"2018-09-20 09:47:57\",\n" +
            "            \"updated_at\": \"2018-09-20 09:47:57\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"108\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"muhammed salim pp\",\n" +
            "            \"name_malayalam\": \"മുഹമ്മദ്\u200C സലിം പി. പി \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9446196928\",\n" +
            "            \"created_at\": \"2018-10-02 15:05:15\",\n" +
            "            \"updated_at\": \"2018-10-02 15:05:15\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"29\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"nadheer \",\n" +
            "            \"name_malayalam\": \"നദീർ \",\n" +
            "            \"place\": \"അടുക്കം \",\n" +
            "            \"phone\": \"9847903427\",\n" +
            "            \"created_at\": \"2018-09-15 13:49:58\",\n" +
            "            \"updated_at\": \"2018-09-15 13:49:58\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"70\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"nadheer\",\n" +
            "            \"name_malayalam\": \"നദീർ അടുക്കം \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447105736\",\n" +
            "            \"created_at\": \"2018-09-20 09:44:51\",\n" +
            "            \"updated_at\": \"2018-09-20 09:44:51\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"71\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"nadheer adukam\",\n" +
            "            \"name_malayalam\": \"നദീർ അടുക്കം(ടോറസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447105736\",\n" +
            "            \"created_at\": \"2018-09-20 09:46:19\",\n" +
            "            \"updated_at\": \"2018-09-20 09:46:19\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"40\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"nadir \",\n" +
            "            \"name_malayalam\": \"നാദിർ (ഭരത് ബേൻസ്)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447105736 \",\n" +
            "            \"created_at\": \"2018-09-16 16:58:54\",\n" +
            "            \"updated_at\": \"2018-09-16 16:58:54\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"31\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Nahas\",\n" +
            "            \"name_malayalam\": \"നഹാസ്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947415374\",\n" +
            "            \"created_at\": \"2018-09-15 14:57:14\",\n" +
            "            \"updated_at\": \"2018-09-15 15:13:15\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"30\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"najeeb\",\n" +
            "            \"name_malayalam\": \"നജീബ്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "            \"phone\": \"95266 42502\",\n" +
            "            \"created_at\": \"2018-09-15 13:50:37\",\n" +
            "            \"updated_at\": \"2018-09-15 14:55:23\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"112\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Najeeb  \",\n" +
            "            \"name_malayalam\": \"നജീബ്  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"8606396371\",\n" +
            "            \"created_at\": \"2018-11-03 09:27:12\",\n" +
            "            \"updated_at\": \"2018-11-03 09:27:12\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"94\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"NAJEEB  .\\t\",\n" +
            "            \"name_malayalam\": \" നെജീബ്  (DOST)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447808156\",\n" +
            "            \"created_at\": \"2018-09-24 08:46:27\",\n" +
            "            \"updated_at\": \"2018-09-24 08:46:27\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"20\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"NAseeb\",\n" +
            "            \"name_malayalam\": \"നസിബ് വെളിയത്ത്\",\n" +
            "            \"place\": \"നടക്കൽ,ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447105712\",\n" +
            "            \"created_at\": \"2018-09-11 17:56:59\",\n" +
            "            \"updated_at\": \"2018-09-11 17:56:59\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"103\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"national permitt\",\n" +
            "            \"name_malayalam\": \"നാഷണൽ പെർമിറ്റ് (കമ്പനി)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656713048\",\n" +
            "            \"created_at\": \"2018-09-26 21:47:27\",\n" +
            "            \"updated_at\": \"2018-09-26 21:47:27\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"26\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"NAufal\",\n" +
            "            \"name_malayalam\": \"നൗഫൽ\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9961897756\",\n" +
            "            \"created_at\": \"2018-09-15 06:39:36\",\n" +
            "            \"updated_at\": \"2018-09-15 06:39:36\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"135\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"Naufal elavunkal\",\n" +
            "            \"name_malayalam\": \"നൗഫൽ ഇലവുങ്കൽ\",\n" +
            "            \"place\": \"ഇളപ്പുങ്കൽ, ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"94 47 911525\",\n" +
            "            \"created_at\": \"2018-12-06 09:26:26\",\n" +
            "            \"updated_at\": \"2018-12-06 09:26:26\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"62\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"naushad\",\n" +
            "            \"name_malayalam\": \"നൗഷാദ്(മസ്താ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447488166\",\n" +
            "            \"created_at\": \"2018-09-20 08:52:31\",\n" +
            "            \"updated_at\": \"2018-09-20 08:52:31\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"52\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"navab \",\n" +
            "            \"name_malayalam\": \"നവാബ് \",\n" +
            "            \"place\": \"കടുവാമുഴി \",\n" +
            "            \"phone\": \"9446747749\",\n" +
            "            \"created_at\": \"2018-09-19 17:52:35\",\n" +
            "            \"updated_at\": \"2018-09-19 17:52:35\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"49\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"navas\",\n" +
            "            \"name_malayalam\": \"നവാസ് (407)\",\n" +
            "            \"place\": \"മുട്ടം ജങ്ഷൻ  \",\n" +
            "            \"phone\": \"9446126946\",\n" +
            "            \"created_at\": \"2018-09-19 17:49:44\",\n" +
            "            \"updated_at\": \"2018-09-19 17:49:44\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"50\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"navas \",\n" +
            "            \"name_malayalam\": \"നവാസ് (407)\",\n" +
            "            \"place\": \"കടുവാമുഴി \",\n" +
            "            \"phone\": \"9446126946\",\n" +
            "            \"created_at\": \"2018-09-19 17:50:48\",\n" +
            "            \"updated_at\": \"2018-09-19 17:50:48\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"113\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Navas  \",\n" +
            "            \"name_malayalam\": \"നവാസ്  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447204424\",\n" +
            "            \"created_at\": \"2018-11-03 09:27:44\",\n" +
            "            \"updated_at\": \"2018-11-03 09:27:44\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"51\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"nazeeb \",\n" +
            "            \"name_malayalam\": \"നസീബ് (eicher)\",\n" +
            "            \"place\": \"കടുവാമുഴി \",\n" +
            "            \"phone\": \"9747365659\",\n" +
            "            \"created_at\": \"2018-09-19 17:51:43\",\n" +
            "            \"updated_at\": \"2018-09-19 17:51:43\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"102\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"nazeeb kandathil\",\n" +
            "            \"name_malayalam\": \" നസീബ് കണ്ടത്തിൽ       \",\n" +
            "            \"place\": \"വുഡ്ലാൻഡ് ജംഗ്ഷൻ\",\n" +
            "            \"phone\": \"9207006352\",\n" +
            "            \"created_at\": \"2018-09-25 17:29:13\",\n" +
            "            \"updated_at\": \"2018-09-25 17:29:13\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"39\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"nazr\",\n" +
            "            \"name_malayalam\": \"  നാസർ  (1109) (NP)\",\n" +
            "            \"place\": \" മുട്ടം ജങ്ഷൻ  \",\n" +
            "            \"phone\": \"9744388831\",\n" +
            "            \"created_at\": \"2018-09-16 12:58:38\",\n" +
            "            \"updated_at\": \"2018-09-16 12:58:38\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"25\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"Nisam\",\n" +
            "            \"name_malayalam\": \"നിസാം\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744006697\",\n" +
            "            \"created_at\": \"2018-09-14 15:05:18\",\n" +
            "            \"updated_at\": \"2018-09-14 15:05:18\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"57\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"nizar\",\n" +
            "            \"name_malayalam\": \"നിസാർ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9544973116\",\n" +
            "            \"created_at\": \"2018-09-20 08:45:55\",\n" +
            "            \"updated_at\": \"2018-09-20 08:45:55\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"116\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Noushad \",\n" +
            "            \"name_malayalam\": \" നൗഷാദ് - (7 സീറ്റ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447156109\",\n" +
            "            \"created_at\": \"2018-11-03 09:38:43\",\n" +
            "            \"updated_at\": \"2018-11-03 09:38:43\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"118\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Ramshd  \",\n" +
            "            \"name_malayalam\": \"റംഷിദ് (17 സീറ്റ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947802137\",\n" +
            "            \"created_at\": \"2018-11-03 09:42:46\",\n" +
            "            \"updated_at\": \"2018-11-03 09:42:46\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"123\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Ramshd  \",\n" +
            "            \"name_malayalam\": \"റംഷിദ് (സീറ്റ്-26) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947802137\",\n" +
            "            \"created_at\": \"2018-11-03 09:55:49\",\n" +
            "            \"updated_at\": \"2018-11-03 09:55:49\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"127\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Ramshd  \",\n" +
            "            \"name_malayalam\": \"റംഷിദ് (34  മിനി ബസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947802137\",\n" +
            "            \"created_at\": \"2018-11-03 10:02:11\",\n" +
            "            \"updated_at\": \"2018-11-03 10:02:11\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"69\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"riyas\",\n" +
            "            \"name_malayalam\": \"റിയാസ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447367628\",\n" +
            "            \"created_at\": \"2018-09-20 09:44:18\",\n" +
            "            \"updated_at\": \"2018-09-20 09:44:18\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"36\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"6\",\n" +
            "            \"name_english\": \"Sadik kandathil\",\n" +
            "            \"name_malayalam\": \"സാദിഖ് കണ്ടതിൽ\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447981558\",\n" +
            "            \"created_at\": \"2018-09-15 19:26:32\",\n" +
            "            \"updated_at\": \"2018-09-15 19:26:32\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"56\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"salam\",\n" +
            "            \"name_malayalam\": \"സലാം \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"965667504\",\n" +
            "            \"created_at\": \"2018-09-20 08:45:21\",\n" +
            "            \"updated_at\": \"2018-09-20 08:45:21\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"78\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"savad\",\n" +
            "            \"name_malayalam\": \"സവാദ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9544167088\",\n" +
            "            \"created_at\": \"2018-09-20 09:52:22\",\n" +
            "            \"updated_at\": \"2018-09-20 09:52:22\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"4\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Shajees\",\n" +
            "            \"name_malayalam\": \"ഷാജിസ്\",\n" +
            "            \"place\": \"മുണ്ടക്കയം\",\n" +
            "            \"phone\": \"8086399950\",\n" +
            "            \"created_at\": \"2018-06-29 18:06:17\",\n" +
            "            \"updated_at\": \"2018-06-29 18:06:17\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"98\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"Shameer\",\n" +
            "            \"name_malayalam\": \"   ഷെർബിൻ  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9333475188\",\n" +
            "            \"created_at\": \"2018-09-24 21:37:14\",\n" +
            "            \"updated_at\": \"2018-09-24 21:37:14\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"99\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"shamer  \",\n" +
            "            \"name_malayalam\": \"ബെല്ലാരി (ഷെമീർ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656505612\",\n" +
            "            \"created_at\": \"2018-09-24 21:37:54\",\n" +
            "            \"updated_at\": \"2018-09-24 21:37:54\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"63\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"shamsudheen\",\n" +
            "            \"name_malayalam\": \"ഷംസുദ്ധീൻ(മസ്താ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9567522272\",\n" +
            "            \"created_at\": \"2018-09-20 08:53:14\",\n" +
            "            \"updated_at\": \"2018-09-20 08:53:14\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"61\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"shanas\",\n" +
            "            \"name_malayalam\": \"ഷഹനാസ്(909) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656888061\",\n" +
            "            \"created_at\": \"2018-09-20 08:48:54\",\n" +
            "            \"updated_at\": \"2018-09-20 08:48:54\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Sheheer\",\n" +
            "            \"name_malayalam\": \"ഷെഹിർ \",\n" +
            "            \"place\": \"ഇളപ്പുങ്കൽ, ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656562417\",\n" +
            "            \"created_at\": \"2018-09-02 07:51:47\",\n" +
            "            \"updated_at\": \"2018-09-02 07:52:40\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"11\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Shehir\",\n" +
            "            \"name_malayalam\": \"ഷെഹിർ (17 സീറ്റ്)\",\n" +
            "            \"place\": \"ഇളപ്പുങ്കൽ  , ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656562417\",\n" +
            "            \"created_at\": \"2018-09-02 07:48:39\",\n" +
            "            \"updated_at\": \"2018-09-02 07:48:39\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"64\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"shereef\",\n" +
            "            \"name_malayalam\": \"ഷെരിഫ്(മസ്താ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447120627\",\n" +
            "            \"created_at\": \"2018-09-20 08:54:04\",\n" +
            "            \"updated_at\": \"2018-09-20 08:54:04\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"109\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Shereef\",\n" +
            "            \"name_malayalam\": \"ഷെരീഫ് \",\n" +
            "            \"place\": \"മുട്ടം ജംക്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9497091562\",\n" +
            "            \"created_at\": \"2018-10-09 14:01:02\",\n" +
            "            \"updated_at\": \"2018-10-09 14:05:06\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"104\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"SHibu\",\n" +
            "            \"name_malayalam\": \"ഷിബു   (xylo)\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9747115676\",\n" +
            "            \"created_at\": \"2018-09-27 13:58:03\",\n" +
            "            \"updated_at\": \"2018-09-27 13:58:03\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"66\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"shihab\",\n" +
            "            \"name_malayalam\": \"ഷിഹാബ് (1109) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847202053\",\n" +
            "            \"created_at\": \"2018-09-20 08:56:29\",\n" +
            "            \"updated_at\": \"2018-09-20 08:56:29\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"110\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Shihabudeen (Sanchari) \",\n" +
            "            \"name_malayalam\": \"ശിഹാബുദ്ധീൻ ( സഞ്ചാരി ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847982972\",\n" +
            "            \"created_at\": \"2018-11-03 09:26:03\",\n" +
            "            \"updated_at\": \"2018-11-03 09:26:03\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"115\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"Shihabudeen (Sanchari) \",\n" +
            "            \"name_malayalam\": \"ശിഹാബുദ്ധീൻ (സഞ്ചാരി) -7 സീറ്റ്  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9744612972\",\n" +
            "            \"created_at\": \"2018-11-03 09:31:39\",\n" +
            "            \"updated_at\": \"2018-11-03 09:31:39\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"117\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Shihabudeen(Sanchari) \",\n" +
            "            \"name_malayalam\": \"ശിഹാബുദ്ധീൻ (17 സീറ്റ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847982972\",\n" +
            "            \"created_at\": \"2018-11-03 09:42:04\",\n" +
            "            \"updated_at\": \"2018-11-03 09:42:04\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"122\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"4\",\n" +
            "            \"name_english\": \"Shihabudeen(SANCHARI) \",\n" +
            "            \"name_malayalam\": \"ശിഹാബുദ്ധീൻ (സീറ്റ്-26) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847982972\",\n" +
            "            \"created_at\": \"2018-11-03 09:55:07\",\n" +
            "            \"updated_at\": \"2018-11-03 09:55:07\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"125\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Shihabudeen(SANCHARI) \",\n" +
            "            \"name_malayalam\": \"ശിഹാബുദ്ധീൻ (34  മിനി ബസ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847982972\",\n" +
            "            \"created_at\": \"2018-11-03 09:58:03\",\n" +
            "            \"updated_at\": \"2018-11-03 09:58:03\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"129\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"5\",\n" +
            "            \"name_english\": \"Shihabudeen(SANCHARI) \",\n" +
            "            \"name_malayalam\": \"ശിഹാബുദ്ധീൻ ( സഞ്ചാരി 49 സീറ്റ് ബസ് ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847982972\",\n" +
            "            \"created_at\": \"2018-11-03 10:03:35\",\n" +
            "            \"updated_at\": \"2018-11-03 10:03:35\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"97\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"shiya kutana\",\n" +
            "            \"name_malayalam\": \"ഷിയാകുട്ടൻ  \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9847515729\",\n" +
            "            \"created_at\": \"2018-09-24 21:36:14\",\n" +
            "            \"updated_at\": \"2018-09-24 21:36:14\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"59\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"shukoor\",\n" +
            "            \"name_malayalam\": \"ഷൂക്കൂർ(407) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9961361205 \",\n" +
            "            \"created_at\": \"2018-09-20 08:47:48\",\n" +
            "            \"updated_at\": \"2018-09-20 08:47:48\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"107\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Siraj C.M \",\n" +
            "            \"name_malayalam\": \"സിറാജ്  \",\n" +
            "            \"place\": \"കാരക്കാട് , ഈരാറ്റുപേട്ട \",\n" +
            "            \"phone\": \"9947095895\",\n" +
            "            \"created_at\": \"2018-10-02 14:43:36\",\n" +
            "            \"updated_at\": \"2018-10-02 14:43:36\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"95\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"11\",\n" +
            "            \"name_english\": \"siyad\",\n" +
            "            \"name_malayalam\": \"അറേബ്യൻ ദിയ (സിയാദ്) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9526643621\",\n" +
            "            \"created_at\": \"2018-09-24 21:33:22\",\n" +
            "            \"updated_at\": \"2018-09-24 21:33:22\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"91\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"siyd\",\n" +
            "            \"name_malayalam\": \"സുഷീലൻ (സിയാദ്)\",\n" +
            "            \"place\": \"തെക്കേക്കര   \",\n" +
            "            \"phone\": \"9947889846 \",\n" +
            "            \"created_at\": \"2018-09-22 10:40:26\",\n" +
            "            \"updated_at\": \"2018-09-22 10:40:26\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"86\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"Thadathil\",\n" +
            "            \"name_malayalam\": \"തടത്തിൽ\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656464893\",\n" +
            "            \"created_at\": \"2018-09-20 23:08:36\",\n" +
            "            \"updated_at\": \"2018-09-20 23:08:36\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"87\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"Thadathil\",\n" +
            "            \"name_malayalam\": \"തടത്തിൽ 1\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"8714949680\",\n" +
            "            \"created_at\": \"2018-09-20 23:09:27\",\n" +
            "            \"updated_at\": \"2018-09-20 23:09:27\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"88\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"11\",\n" +
            "            \"name_english\": \"Thadathil \",\n" +
            "            \"name_malayalam\": \"തടത്തിൽ \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9207209064\",\n" +
            "            \"created_at\": \"2018-09-20 23:10:01\",\n" +
            "            \"updated_at\": \"2018-09-20 23:10:01\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"90\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"9\",\n" +
            "            \"name_english\": \"Thadathil \",\n" +
            "            \"name_malayalam\": \"തടത്തിൽ ടിപ്പർ (ബാദുഷ സുൽത്താൻ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"8714949680\",\n" +
            "            \"created_at\": \"2018-09-21 08:53:24\",\n" +
            "            \"updated_at\": \"2018-09-21 08:53:24\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"89\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"11\",\n" +
            "            \"name_english\": \"Thadaththil\",\n" +
            "            \"name_malayalam\": \"തടത്തിൽ (ബാദുഷ സുൽത്താൻ )\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9656464893\",\n" +
            "            \"created_at\": \"2018-09-21 08:52:17\",\n" +
            "            \"updated_at\": \"2018-09-21 08:52:17\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"10\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Transporter Tata 407 6 wheel Truck Muttom \",\n" +
            "            \"name_malayalam\": \"ടാറ്റാ 407 \",\n" +
            "            \"place\": \"മുട്ടം ജംഗ്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9961401258\",\n" +
            "            \"created_at\": \"2018-08-08 15:25:33\",\n" +
            "            \"updated_at\": \"2018-08-08 15:25:33\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"54\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"7\",\n" +
            "            \"name_english\": \"ubais\",\n" +
            "            \"name_malayalam\": \"ഉബൈസ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9495313471\",\n" +
            "            \"created_at\": \"2018-09-19 18:14:34\",\n" +
            "            \"updated_at\": \"2018-09-19 18:14:34\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"106\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"11\",\n" +
            "            \"name_english\": \"Ukkash\",\n" +
            "            \"name_malayalam\": \"ഉക്കാശ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947272244\",\n" +
            "            \"created_at\": \"2018-10-01 21:09:33\",\n" +
            "            \"updated_at\": \"2018-10-01 21:09:33\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"65\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"10\",\n" +
            "            \"name_english\": \"yasar\",\n" +
            "            \"name_malayalam\": \"യാസർ ഖാൻ(മസ്താ) \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"8113938664\",\n" +
            "            \"created_at\": \"2018-09-20 08:54:35\",\n" +
            "            \"updated_at\": \"2018-09-20 08:54:35\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"132\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"YOU&ME TRAVELS \",\n" +
            "            \"name_malayalam\": \"യു ആൻഡ് മീ ട്രാവൽസ് ( INNOVA ,CRYSTA,INDIGO) റഫീക്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947669979\",\n" +
            "            \"created_at\": \"2018-11-09 08:43:26\",\n" +
            "            \"updated_at\": \"2018-11-09 11:15:52\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"133\",\n" +
            "            \"category_id\": \"1\",\n" +
            "            \"type_id\": \"3\",\n" +
            "            \"name_english\": \"YOU&ME TRAVELS \",\n" +
            "            \"name_malayalam\": \"യു ആൻഡ് മീ ട്രാവൽസ് ( INNOVA ,CRYSTA,INDIGO) റഫീക്\",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9447922446\",\n" +
            "            \"created_at\": \"2018-11-09 08:44:38\",\n" +
            "            \"updated_at\": \"2018-11-09 11:15:39\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"22\",\n" +
            "            \"category_id\": \"2\",\n" +
            "            \"type_id\": \"8\",\n" +
            "            \"name_english\": \"Yusuf\",\n" +
            "            \"name_malayalam\": \"യൂസഫ് \",\n" +
            "            \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "            \"phone\": \"9947198181\",\n" +
            "            \"created_at\": \"2018-09-12 15:05:55\",\n" +
            "            \"updated_at\": \"2018-09-12 15:05:55\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vahanangal_taxi_detail);
        getTravellarData();
        getBusData();


        travellar_recycler = findViewById(R.id.travellar_recycler);
        travellar_recycler.setNestedScrollingEnabled(false);
        travellar_recycler.setHasFixedSize(false);
        travellar_recycler.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration2 = new ItemOffsetDecoration(VahanangalTaxiDetail.this, R.dimen.item_offset);
        travellar_recycler.addItemDecoration(itemDecoration2);
        travellar_recycler.setItemAnimator(new DefaultItemAnimator());

        bus_recycler = findViewById(R.id.bus_recycler);
        bus_recycler.setNestedScrollingEnabled(false);
        bus_recycler.setHasFixedSize(false);
        bus_recycler.setLayoutManager(new LinearLayoutManager(this)) ;
        ItemOffsetDecoration itemDecoration3 = new ItemOffsetDecoration(VahanangalTaxiDetail.this, R.dimen.item_offset);
        bus_recycler.addItemDecoration(itemDecoration3);
        bus_recycler.setItemAnimator(new DefaultItemAnimator());
        setUpFlipView();
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
    private void getTravellarData() {

        Async async = new Async();
        async.execute(data);
    }

    public void car1(View view) {
        Uri number = Uri.parse("tel:" + "9495162522");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car2(View view) {
        Uri number = Uri.parse("tel:" + "9605544369");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car3(View view) {
        Uri number = Uri.parse("tel:" + "9947133418");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car4(View view) {
        Uri number = Uri.parse("tel:" + "9744032158");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void card5(View view) {
        Uri number = Uri.parse("tel:" + "9447952333");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void card6(View view) {
        Uri number = Uri.parse("tel:" + "9388895301");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void card7(View view) {
        Uri number = Uri.parse("tel:" + "8606396371");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car8(View view) {
        Uri number = Uri.parse("tel:" + "9447204424");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car9(View view) {
        Uri number = Uri.parse("tel:" + "9447156109");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car10(View view) {
        Uri number = Uri.parse("tel:" + "9747115676");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car11(View view) {
        Uri number = Uri.parse("tel:" + "9847982972");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car12(View view) {
        Uri number = Uri.parse("tel:" + "9744612972");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car13(View view) {
        Uri number = Uri.parse("tel:" + "9847982972");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void car14(View view) {
        Uri number = Uri.parse("tel:" + "9947669979");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void goods1(View view) {
    }

    public class Async extends AsyncTask<String,String,ArrayList<Data>>{

        @Override
        protected ArrayList<Data> doInBackground(String... strings) {
            ArrayList<Data> list = new ArrayList<>();
            String data = strings[0];
            try {
                JSONObject jsonObj = new JSONObject(data);
                JSONArray vehicles = jsonObj.getJSONArray("vehicles");

                // looping through All Contacts
                for (int i = 0; i < vehicles.length(); i++) {

                        JSONObject c = vehicles.getJSONObject(i);
                    if (c.getString("type_id").equals("4")) {
                        Log.d("name", c.getString("name_malayalam"));
                        Data data1 = new Data();
                        data1.setTaxi_name(c.getString("name_malayalam"));
                        data1.setTaxi_location(c.getString("place"));
                        data1.setTaxi_mobile(c.getString("phone"));
                        list.add(data1);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> list) {

                travellaradapter = new Adapter(list);
                travellar_recycler.setAdapter(travellaradapter);

        }
    }
    public class Async2 extends AsyncTask<String,String,ArrayList<Data>>{

        @Override
        protected ArrayList<Data> doInBackground(String... strings) {
            ArrayList<Data> list = new ArrayList<>();
            String data = strings[0];
            try {
                JSONObject jsonObj = new JSONObject(data);
                JSONArray vehicles = jsonObj.getJSONArray("vehicles");

                // looping through All Contacts
                for (int i = 0; i < vehicles.length(); i++) {
                    JSONObject c = vehicles.getJSONObject(i);
                    if (c.getString("type_id").equals("5")) {
                        Log.d("name", c.getString("name_malayalam"));
                        Data data1 = new Data();
                        data1.setTaxi_name(c.getString("name_malayalam"));
                        data1.setTaxi_location(c.getString("place"));
                        data1.setTaxi_mobile(c.getString("phone"));
                        list.add(data1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> list) {

                busadapter = new Adapter(list);
                bus_recycler.setAdapter(busadapter);

        }
    }
    private void getBusData() {
        Async2 async = new Async2();
        async.execute(data);
    }




    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        private List<Data> list;
        public Adapter(List<Data> list){
            this.list = list;
            Log.d("size",String.valueOf(list.size()));
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_emergency, viewGroup, false);
            return  new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_name.setText(data.getTaxi_name());
            myViewHolder.txt_emergency_mobile.setText(data.getTaxi_mobile());
            myViewHolder.txt_location.setText(data.getTaxi_location());
            myViewHolder.img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri number = Uri.parse("tel:" + data.getTaxi_mobile());
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
            private TextView txt_name,txt_location,txt_emergency_mobile;
            private ImageView img_call;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_name = itemView.findViewById(R.id.txt_name5);
                txt_location = itemView.findViewById(R.id.txt_location4);
                txt_emergency_mobile = itemView.findViewById(R.id.txt_emergency_mobile);
                img_call = itemView.findViewById(R.id.img_call);

            }
        }
    }

}
