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

public class SevanamDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Data> list = new ArrayList<>();
    private Adapter adapter;
    private String title;
    private String intent_id;
    String data = "{\n" +
            "  \"services\": [\n" +
            "    {\n" +
            "      \"intent_id\": \"71\",\n" +
            "      \"category_id\": \"10\",\n" +
            "      \"name_english\": \" shibily\",\n" +
            "      \"name_malayalam\": \"(ക്രിയേറ്റിവ്) ഷിബിലി\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447104780\",\n" +
            "      \"created_at\": \"2018-09-17 13:39:15\",\n" +
            "      \"updated_at\": \"2018-09-17 13:39:15\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"52\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"A one catering  Nadackal\",\n" +
            "      \"name_malayalam\": \"എ വണ്\u200D കാറ്ററിങ്  \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447396348\",\n" +
            "      \"created_at\": \"2018-09-15 17:37:27\",\n" +
            "      \"updated_at\": \"2018-09-15 17:37:27\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"116\",\n" +
            "      \"category_id\": \"29\",\n" +
            "      \"name_english\": \"A one Samad\",\n" +
            "      \"name_malayalam\": \"എ വൺ മിൽക്ക് ( സമദ് വെട്ടിക്കൽ )\",\n" +
            "      \"place\": \"നടക്കൽ,ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9961115320\",\n" +
            "      \"created_at\": \"2018-10-01 20:57:00\",\n" +
            "      \"updated_at\": \"2018-10-01 20:57:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"16\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Aami \",\n" +
            "      \"name_malayalam\": \"ആമി ഡെക്കറേഷൻ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447525518\",\n" +
            "      \"created_at\": \"2018-07-21 14:35:00\",\n" +
            "      \"updated_at\": \"2018-07-21 14:35:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"15\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Aami catring\",\n" +
            "      \"name_malayalam\": \"ആമി കാറ്ററിങ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947555080\",\n" +
            "      \"created_at\": \"2018-07-21 13:28:43\",\n" +
            "      \"updated_at\": \"2018-07-21 13:28:43\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"34\",\n" +
            "      \"category_id\": \"12\",\n" +
            "      \"name_english\": \"Acumen\",\n" +
            "      \"name_malayalam\": \"അക്യൂമെൻ സ്റ്റോക്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9446197552\",\n" +
            "      \"created_at\": \"2018-09-02 22:43:06\",\n" +
            "      \"updated_at\": \"2018-09-02 22:43:06\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"61\",\n" +
            "      \"category_id\": \"16\",\n" +
            "      \"name_english\": \"Adv p. B haris parayil  \",\n" +
            "      \"name_malayalam\": \"അഡ്വ. പി ബി ഹാരിസ് പാറയില്\u200D \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9744886688\",\n" +
            "      \"created_at\": \"2018-09-16 09:45:03\",\n" +
            "      \"updated_at\": \"2018-09-16 21:03:54\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"75\",\n" +
            "      \"category_id\": \"19\",\n" +
            "      \"name_english\": \"Afsal\",\n" +
            "      \"name_malayalam\": \"അഫ്സൽ.\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9847232765\",\n" +
            "      \"created_at\": \"2018-09-18 17:01:48\",\n" +
            "      \"updated_at\": \"2018-09-18 17:01:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"88\",\n" +
            "      \"category_id\": \"13\",\n" +
            "      \"name_english\": \"aj\",\n" +
            "      \"name_malayalam\": \"എ.ജെ ഇവെന്റ്സ്  \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9605445050\",\n" +
            "      \"created_at\": \"2018-09-22 09:48:52\",\n" +
            "      \"updated_at\": \"2018-09-22 09:48:52\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"93\",\n" +
            "      \"category_id\": \"14\",\n" +
            "      \"name_english\": \"aj\",\n" +
            "      \"name_malayalam\": \"എ ജെ ലൈറ്റ്സ് & ഓഡിയോസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9605445050\",\n" +
            "      \"created_at\": \"2018-09-22 10:45:24\",\n" +
            "      \"updated_at\": \"2018-09-22 10:45:24\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"59\",\n" +
            "      \"category_id\": \"15\",\n" +
            "      \"name_english\": \"akshaya  center -market road\",\n" +
            "      \"name_malayalam\": \"അക്ഷയ കേന്ദ്രം - മാര്\u200Dക്കറ്റ്\u200C റോഡ്\u200C \",\n" +
            "      \"place\": \"മാർക്കറ്റ് റോഡ് ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847683049\",\n" +
            "      \"created_at\": \"2018-09-15 20:12:35\",\n" +
            "      \"updated_at\": \"2018-09-15 20:12:35\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"63\",\n" +
            "      \"category_id\": \"15\",\n" +
            "      \"name_english\": \"akshaya cennter\",\n" +
            "      \"name_malayalam\": \"അക്ഷയ സെന്റർ- ചേന്നാട് കവല \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9961985088\",\n" +
            "      \"created_at\": \"2018-09-16 11:35:58\",\n" +
            "      \"updated_at\": \"2018-09-16 21:03:23\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"64\",\n" +
            "      \"category_id\": \"15\",\n" +
            "      \"name_english\": \"akshaya center nnadakkal\",\n" +
            "      \"name_malayalam\": \"അക്ഷയ സെന്റർ - നടക്കല്\u200D \",\n" +
            "      \"place\": \"നടക്കല്\u200D  \",\n" +
            "      \"phone\": \"9495339460\",\n" +
            "      \"created_at\": \"2018-09-16 11:37:03\",\n" +
            "      \"updated_at\": \"2018-09-16 21:03:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"91\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Ameenudheen A\",\n" +
            "      \"name_malayalam\": \"അമീനുദ്ദീൻ എ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9809656163\",\n" +
            "      \"created_at\": \"2018-09-22 10:07:35\",\n" +
            "      \"updated_at\": \"2018-09-22 10:07:35\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"79\",\n" +
            "      \"category_id\": \"20\",\n" +
            "      \"name_english\": \"amees holidays\",\n" +
            "      \"name_malayalam\": \"ആമീസ് ഹോളിഡേയ്\u200Cസ്  \",\n" +
            "      \"place\": \"പിണ്ണാക്കനാട് \",\n" +
            "      \"phone\": \"9947133418\",\n" +
            "      \"created_at\": \"2018-09-20 10:03:35\",\n" +
            "      \"updated_at\": \"2018-09-20 10:03:35\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"28\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"AMI\",\n" +
            "      \"name_malayalam\": \"ആമി കേറ്ററിംഗ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447525518\",\n" +
            "      \"created_at\": \"2018-08-06 17:14:33\",\n" +
            "      \"updated_at\": \"2018-08-06 17:14:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"135\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Ar. Ajmal zain \",\n" +
            "      \"name_malayalam\": \"D'Zain Architecture\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9495078754\",\n" +
            "      \"created_at\": \"2018-12-12 16:26:09\",\n" +
            "      \"updated_at\": \"2018-12-12 16:26:09\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"69\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"Ashraf O.U\",\n" +
            "      \"name_malayalam\": \"അഷ്\u200Cറഫ് ഒ.യു \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9645529680\",\n" +
            "      \"created_at\": \"2018-09-16 19:44:56\",\n" +
            "      \"updated_at\": \"2018-09-16 21:02:14\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"92\",\n" +
            "      \"category_id\": \"22\",\n" +
            "      \"name_english\": \"Ashraf PS  Erattupetta \",\n" +
            "      \"name_malayalam\": \"അഷ്\u200Cറഫ് പി എസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"7012624328 \",\n" +
            "      \"created_at\": \"2018-09-22 10:36:48\",\n" +
            "      \"updated_at\": \"2018-09-22 10:36:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"56\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"ayan\",\n" +
            "      \"name_malayalam\": \"അയാന്\u200D\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847324884\",\n" +
            "      \"created_at\": \"2018-09-15 19:45:33\",\n" +
            "      \"updated_at\": \"2018-09-15 19:45:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"17\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Balloon decoration & balloon arch and printing\",\n" +
            "      \"name_malayalam\": \"ബലൂണ് വർക്ക്, ആർച്ച് വർക്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9037552221\",\n" +
            "      \"created_at\": \"2018-07-21 15:03:45\",\n" +
            "      \"updated_at\": \"2018-07-21 15:03:45\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"101\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"BAsith\",\n" +
            "      \"name_malayalam\": \"ഡെയ്\u200Cലി സർവിസ് - ബാസിത്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9961514697\",\n" +
            "      \"created_at\": \"2018-09-22 19:52:06\",\n" +
            "      \"updated_at\": \"2018-09-22 19:52:06\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"89\",\n" +
            "      \"category_id\": \"23\",\n" +
            "      \"name_english\": \"bimilah\",\n" +
            "      \"name_malayalam\": \"ബിസ്മില്ലാ മെറ്റൽ ക്രഷർ \",\n" +
            "      \"place\": \"തേവരുപറ \",\n" +
            "      \"phone\": \"9847549058\",\n" +
            "      \"created_at\": \"2018-09-22 09:53:38\",\n" +
            "      \"updated_at\": \"2018-09-22 09:53:38\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"136\",\n" +
            "      \"category_id\": \"32\",\n" +
            "      \"name_english\": \"blooming daycare\",\n" +
            "      \"name_malayalam\": \"'ബ്ളൂമിംഗ് ബഡ്\u200Cസ് '' ഡേ  കെയർ സെന്റർ.  \",\n" +
            "      \"place\": \"പാലാ കട്ടക്കയം റോഡിൽ , കല്ലറക്കൽ ബിൽഡിങ്ങിൽ , ഫെഡറ\",\n" +
            "      \"phone\": \"9539154988 \",\n" +
            "      \"created_at\": \"2018-12-12 16:29:35\",\n" +
            "      \"updated_at\": \"2018-12-12 16:29:35\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"82\",\n" +
            "      \"category_id\": \"14\",\n" +
            "      \"name_english\": \"Dennis\",\n" +
            "      \"name_malayalam\": \"ഡെന്നിസ്\u200C\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9446600062\",\n" +
            "      \"created_at\": \"2018-09-20 22:34:09\",\n" +
            "      \"updated_at\": \"2018-09-20 22:34:09\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"11\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"Dr. Fone\",\n" +
            "      \"name_malayalam\": \"ഡോ. ഫോൺ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9496322257\",\n" +
            "      \"created_at\": \"2018-07-18 13:01:49\",\n" +
            "      \"updated_at\": \"2018-09-16 21:06:13\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"99\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"Dtdc\",\n" +
            "      \"name_malayalam\": \"ഡി റ്റി ഡി സി\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9446861610\",\n" +
            "      \"created_at\": \"2018-09-22 19:46:29\",\n" +
            "      \"updated_at\": \"2018-09-22 19:46:29\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"81\",\n" +
            "      \"category_id\": \"13\",\n" +
            "      \"name_english\": \"evar green\",\n" +
            "      \"name_malayalam\": \"എവർ ഗ്രീൻ ഡെക്കർ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8606091667\",\n" +
            "      \"created_at\": \"2018-09-20 10:10:18\",\n" +
            "      \"updated_at\": \"2018-09-20 10:10:18\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"133\",\n" +
            "      \"category_id\": \"31\",\n" +
            "      \"name_english\": \"faredais bore wells \",\n" +
            "      \"name_malayalam\": \"ഫാരഡൈസ് ബോർവേൽസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947867046\",\n" +
            "      \"created_at\": \"2018-11-17 10:24:16\",\n" +
            "      \"updated_at\": \"2018-11-17 10:24:16\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"90\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Favaz Muhammed Rafi\",\n" +
            "      \"name_malayalam\": \"ഫവാസ് മുഹമ്മദ് റാഫി\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9744329245\",\n" +
            "      \"created_at\": \"2018-09-22 10:06:44\",\n" +
            "      \"updated_at\": \"2018-09-22 10:06:44\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"1\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Fazil fareed\",\n" +
            "      \"name_malayalam\": \"ഫാസിൽ ഫരീദ് (ആർകിടെക്)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"7907868748\",\n" +
            "      \"created_at\": \"2018-07-17 19:06:15\",\n" +
            "      \"updated_at\": \"2018-09-17 17:11:44\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"35\",\n" +
            "      \"category_id\": \"12\",\n" +
            "      \"name_english\": \"Fornas\",\n" +
            "      \"name_malayalam\": \"ഫോർനാസ് ഫോറെക്സ് സർവീസസ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447006353\",\n" +
            "      \"created_at\": \"2018-09-02 22:44:14\",\n" +
            "      \"updated_at\": \"2018-09-02 22:44:14\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"114\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"green leef \",\n" +
            "      \"name_malayalam\": \"ഗ്രീൻ ലീഫ് കാറ്റേഴ്\u200Cസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947400991\",\n" +
            "      \"created_at\": \"2018-09-28 11:09:11\",\n" +
            "      \"updated_at\": \"2018-09-28 11:09:11\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"5\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Haris Abdulkhadar\",\n" +
            "      \"name_malayalam\": \"ഹാരിസ് അബ്ദുൽഖാദർ (അൽസഫാ)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847272466\",\n" +
            "      \"created_at\": \"2018-07-17 19:10:44\",\n" +
            "      \"updated_at\": \"2018-07-17 19:10:44\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"65\",\n" +
            "      \"category_id\": \"17\",\n" +
            "      \"name_english\": \"haris parayil\",\n" +
            "      \"name_malayalam\": \"ഹാരിസ് പാറയില്\u200D \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9744886688\",\n" +
            "      \"created_at\": \"2018-09-16 13:55:38\",\n" +
            "      \"updated_at\": \"2018-09-16 13:55:38\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"132\",\n" +
            "      \"category_id\": \"8\",\n" +
            "      \"name_english\": \"HashTech lt solutions \",\n" +
            "      \"name_malayalam\": \"ഹാഷ്ടെക്  ഐടി സൊല്യൂഷൻ  (വെബ്സൈറ്റ് ഡെവലപ്പ്മെന്റ്\",\n" +
            "      \"place\": \"Hashir CT\",\n" +
            "      \"phone\": \"90 2006 6789\",\n" +
            "      \"created_at\": \"2018-11-17 10:22:12\",\n" +
            "      \"updated_at\": \"2018-11-26 16:41:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"117\",\n" +
            "      \"category_id\": \"29\",\n" +
            "      \"name_english\": \"hima milk\",\n" +
            "      \"name_malayalam\": \"ഹിമാ മിൽക്ക് - സെയ്ദ്മുഹമ്മദ് (പലുമാമ)\",\n" +
            "      \"place\": \"നടക്കൽ , ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9048803608\",\n" +
            "      \"created_at\": \"2018-10-02 14:52:43\",\n" +
            "      \"updated_at\": \"2018-10-02 14:52:43\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"74\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"ishfaq noushad\",\n" +
            "      \"name_malayalam\": \"ഇഷ്ഫാക് നൗഷാദ്\u200C(യൂണിവേഴ്\u200Cസല്\u200D)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9847566054\",\n" +
            "      \"created_at\": \"2018-09-18 13:48:14\",\n" +
            "      \"updated_at\": \"2018-09-18 15:09:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"105\",\n" +
            "      \"category_id\": \"26\",\n" +
            "      \"name_english\": \"ism\",\n" +
            "      \"name_malayalam\": \"ഐ.എസ്.എം മെഡിക്കൽ എയ്ഡ് സെന്റർ \",\n" +
            "      \"place\": \"എം.ഇ.എസ് ജംഗ്ഷൻ ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8921418702\",\n" +
            "      \"created_at\": \"2018-09-24 08:30:00\",\n" +
            "      \"updated_at\": \"2018-09-24 08:30:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"118\",\n" +
            "      \"category_id\": \"29\",\n" +
            "      \"name_english\": \"Ismail \",\n" +
            "      \"name_malayalam\": \"ഇസ്മായിൽ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9207890825\",\n" +
            "      \"created_at\": \"2018-10-02 14:55:38\",\n" +
            "      \"updated_at\": \"2018-10-02 14:55:38\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"23\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Ismail Methar \",\n" +
            "      \"name_malayalam\": \"ഇസ്മയിൽ മേത്തർ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9544073736\",\n" +
            "      \"created_at\": \"2018-08-06 17:00:09\",\n" +
            "      \"updated_at\": \"2018-08-06 17:00:09\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"73\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"IT CARE\",\n" +
            "      \"name_malayalam\": \"ഐ.ടി. കെയര്\u200D\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9995706719\",\n" +
            "      \"created_at\": \"2018-09-17 17:30:39\",\n" +
            "      \"updated_at\": \"2018-09-17 17:30:39\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"29\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Jabi's catering\",\n" +
            "      \"name_malayalam\": \"ജാബിസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8547632006\",\n" +
            "      \"created_at\": \"2018-08-06 17:15:48\",\n" +
            "      \"updated_at\": \"2018-08-06 17:15:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"22\",\n" +
            "      \"category_id\": \"9\",\n" +
            "      \"name_english\": \"Joice Venadan\",\n" +
            "      \"name_malayalam\": \"ജോയ്\u200Cസ് വേണാടൻ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9446302114\",\n" +
            "      \"created_at\": \"2018-08-05 14:42:31\",\n" +
            "      \"updated_at\": \"2018-08-05 14:42:31\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"26\",\n" +
            "      \"category_id\": \"11\",\n" +
            "      \"name_english\": \"Junaid\",\n" +
            "      \"name_malayalam\": \"ജുനൈദ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9605364406\",\n" +
            "      \"created_at\": \"2018-08-06 17:08:06\",\n" +
            "      \"updated_at\": \"2018-08-06 17:08:06\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"53\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"JWF events &catorig\",\n" +
            "      \"name_malayalam\": \"JWF ഇവന്റ്സ് &  കേറ്ററിങ്ങ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947846706\",\n" +
            "      \"created_at\": \"2018-09-15 17:54:08\",\n" +
            "      \"updated_at\": \"2018-09-15 17:54:08\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"7\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"K A mahin\",\n" +
            "      \"name_malayalam\": \"കെ.എ മാഹിൻ (ആൽഫാ പ്ലാനേഴ്സ്)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447136438\",\n" +
            "      \"created_at\": \"2018-07-17 19:13:36\",\n" +
            "      \"updated_at\": \"2018-07-17 19:13:36\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"66\",\n" +
            "      \"category_id\": \"17\",\n" +
            "      \"name_english\": \"kabeer\",\n" +
            "      \"name_malayalam\": \"കബീര്\u200D സെയ്തുപറമ്പില്\u200D \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447274116\",\n" +
            "      \"created_at\": \"2018-09-16 13:56:42\",\n" +
            "      \"updated_at\": \"2018-09-16 21:04:27\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"77\",\n" +
            "      \"category_id\": \"21\",\n" +
            "      \"name_english\": \"Kadanthottu finance vadakkekkara \",\n" +
            "      \"name_malayalam\": \"കടനത്തോട്ടു ഫിനാൻസ്  \",\n" +
            "      \"place\": \"വടക്കേക്കര ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9744009704\",\n" +
            "      \"created_at\": \"2018-09-19 16:26:33\",\n" +
            "      \"updated_at\": \"2018-09-19 16:28:25\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"78\",\n" +
            "      \"category_id\": \"20\",\n" +
            "      \"name_english\": \"Kandathil travels. \",\n" +
            "      \"name_malayalam\": \"കണ്ടത്തിൽ ട്രാവൽസ്  \",\n" +
            "      \"place\": \"വടക്കേക്കര ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \" 9744009704\",\n" +
            "      \"created_at\": \"2018-09-19 16:27:26\",\n" +
            "      \"updated_at\": \"2018-09-19 16:28:34\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"96\",\n" +
            "      \"category_id\": \"24\",\n" +
            "      \"name_english\": \"KISCO DIAGNOSTIC CENTRE      \",\n" +
            "      \"name_malayalam\": \"കിസ്കോ  \",\n" +
            "      \"place\": \"അരുവിത്തുറ, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822-272733\",\n" +
            "      \"created_at\": \"2018-09-22 18:50:37\",\n" +
            "      \"updated_at\": \"2018-09-22 18:50:37\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"95\",\n" +
            "      \"category_id\": \"24\",\n" +
            "      \"name_english\": \"KISCO DIAGNOSTIC CENTRE            Erattupetta    \",\n" +
            "      \"name_malayalam\": \"കിസ്കോ \",\n" +
            "      \"place\": \"അരുവിത്തുറ, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"8606272733\",\n" +
            "      \"created_at\": \"2018-09-22 18:49:24\",\n" +
            "      \"updated_at\": \"2018-09-22 18:49:24\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"27\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Kottaas catering & services \",\n" +
            "      \"name_malayalam\": \"കൊട്ടാസ്  കാറ്ററിംഗ് സർവിസ് ( അഷ്റഫ് )\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"+919447029696\",\n" +
            "      \"created_at\": \"2018-08-06 17:11:33\",\n" +
            "      \"updated_at\": \"2018-09-15 19:44:42\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"68\",\n" +
            "      \"category_id\": \"9\",\n" +
            "      \"name_english\": \"koyippalli\",\n" +
            "      \"name_malayalam\": \"കോയിപ്പള്ളി \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"96567 30218\",\n" +
            "      \"created_at\": \"2018-09-16 15:56:28\",\n" +
            "      \"updated_at\": \"2018-09-16 15:56:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"110\",\n" +
            "      \"category_id\": \"21\",\n" +
            "      \"name_english\": \"kpb nidhi Ltd\",\n" +
            "      \"name_malayalam\": \" കെ പി ബി നിധി  \",\n" +
            "      \"place\": \"നിയർ പോലീസ് സ്റ്റേഷൻ  \",\n" +
            "      \"phone\": \"9539236727\",\n" +
            "      \"created_at\": \"2018-09-25 17:30:56\",\n" +
            "      \"updated_at\": \"2018-09-25 17:30:56\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"103\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"KRS parcel service \",\n" +
            "      \"name_malayalam\": \"KRS പാർസൽ സർവീസ്\u200C\",\n" +
            "      \"place\": \"മുനിസിപ്പൽ ഓഫീസ് റോഡ് (മക്കാ മസ്ജിദിന് സമീപം)\",\n" +
            "      \"phone\": \"9207715825\",\n" +
            "      \"created_at\": \"2018-09-23 17:46:46\",\n" +
            "      \"updated_at\": \"2018-09-23 17:46:46\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"42\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Ksm\",\n" +
            "      \"name_malayalam\": \"കെ എസ് എം \",\n" +
            "      \"place\": \"മുട്ടം ജങ്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9048665000\",\n" +
            "      \"created_at\": \"2018-09-11 11:50:13\",\n" +
            "      \"updated_at\": \"2018-09-11 11:50:13\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"47\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Lavender\",\n" +
            "      \"name_malayalam\": \"ലാവണ്ടർ ഡെക്കറേഷൻ  \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"8086483493\",\n" +
            "      \"created_at\": \"2018-09-14 21:48:47\",\n" +
            "      \"updated_at\": \"2018-09-14 21:48:47\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"48\",\n" +
            "      \"category_id\": \"13\",\n" +
            "      \"name_english\": \"lavender\",\n" +
            "      \"name_malayalam\": \"ലാവണ്ടർ ഇവെന്റ്സ്  \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"8086483493\",\n" +
            "      \"created_at\": \"2018-09-14 21:51:21\",\n" +
            "      \"updated_at\": \"2018-09-14 21:51:21\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"12\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"Maheen PS\",\n" +
            "      \"name_malayalam\": \"മാഹീന്\u200D പി എസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9946483443\",\n" +
            "      \"created_at\": \"2018-07-18 13:14:48\",\n" +
            "      \"updated_at\": \"2018-09-27 17:51:27\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"43\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Mahima\",\n" +
            "      \"name_malayalam\": \"മഹിമ\",\n" +
            "      \"place\": \"മുട്ടം ജങ്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9446965279\",\n" +
            "      \"created_at\": \"2018-09-11 11:51:34\",\n" +
            "      \"updated_at\": \"2018-09-11 11:51:34\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"115\",\n" +
            "      \"category_id\": \"28\",\n" +
            "      \"name_english\": \"Mammutti\",\n" +
            "      \"name_malayalam\": \"മുഹമ്മദ് കുട്ടി (മമ്മുട്ടി)\",\n" +
            "      \"place\": \"കാരക്കാട്, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8943110605\",\n" +
            "      \"created_at\": \"2018-10-01 20:46:28\",\n" +
            "      \"updated_at\": \"2018-10-01 20:46:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"102\",\n" +
            "      \"category_id\": \"20\",\n" +
            "      \"name_english\": \"MAss\",\n" +
            "      \"name_malayalam\": \"മാസ്സ് \",\n" +
            "      \"place\": \"മുട്ടം ജങ്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9446861610\",\n" +
            "      \"created_at\": \"2018-09-22 19:53:36\",\n" +
            "      \"updated_at\": \"2018-09-22 19:53:36\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"104\",\n" +
            "      \"category_id\": \"20\",\n" +
            "      \"name_english\": \"max well\",\n" +
            "      \"name_malayalam\": \"ട്യൂർ ആൻഡ് ട്രാവൽസ് മാക്സ് വെൽ  \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447846470\",\n" +
            "      \"created_at\": \"2018-09-24 07:53:45\",\n" +
            "      \"updated_at\": \"2018-09-24 07:53:45\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"40\",\n" +
            "      \"category_id\": \"13\",\n" +
            "      \"name_english\": \"MOD\",\n" +
            "      \"name_malayalam\": \"മോഡ് ഇവന്റ്സ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947244466\",\n" +
            "      \"created_at\": \"2018-09-03 22:46:49\",\n" +
            "      \"updated_at\": \"2018-09-03 22:46:49\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"58\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"MOD\",\n" +
            "      \"name_malayalam\": \"മോഡ് ഇവന്റ്സ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947244466\",\n" +
            "      \"created_at\": \"2018-09-15 19:48:08\",\n" +
            "      \"updated_at\": \"2018-09-15 19:48:08\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"25\",\n" +
            "      \"category_id\": \"10\",\n" +
            "      \"name_english\": \"naufal vm \",\n" +
            "      \"name_malayalam\": \"നൗഫൽ വി എം\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9995375938  \",\n" +
            "      \"created_at\": \"2018-08-06 17:05:11\",\n" +
            "      \"updated_at\": \"2018-08-06 17:05:11\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"24\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"naufal vm vellapalliparambil\",\n" +
            "      \"name_malayalam\": \"നൗഫൽ വി എം\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9995375938  \",\n" +
            "      \"created_at\": \"2018-08-06 17:01:58\",\n" +
            "      \"updated_at\": \"2018-08-06 17:01:58\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"70\",\n" +
            "      \"category_id\": \"11\",\n" +
            "      \"name_english\": \"navas:\",\n" +
            "      \"name_malayalam\": \"നവാസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447600250\",\n" +
            "      \"created_at\": \"2018-09-17 11:58:47\",\n" +
            "      \"updated_at\": \"2018-09-17 11:58:47\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"100\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"NAzeer\",\n" +
            "      \"name_malayalam\": \"ഡെയ്\u200Cലി സർവിസ് - നസീർ കാടപുരം \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447396023\",\n" +
            "      \"created_at\": \"2018-09-22 19:50:05\",\n" +
            "      \"updated_at\": \"2018-09-22 19:50:05\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"10\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"NEONICS Tenchnologies\",\n" +
            "      \"name_malayalam\": \"നിയോനിക്\u200Cസ് \",\n" +
            "      \"place\": \"മുട്ടം ജംക്ഷൻ , ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447860088\",\n" +
            "      \"created_at\": \"2018-07-18 12:58:57\",\n" +
            "      \"updated_at\": \"2018-07-18 12:58:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"38\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"NIce\",\n" +
            "      \"name_malayalam\": \"നൈസ് കാറ്ററിങ്\",\n" +
            "      \"place\": \"തെക്കേക്കര, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9847561210\",\n" +
            "      \"created_at\": \"2018-09-03 14:27:59\",\n" +
            "      \"updated_at\": \"2018-09-03 14:27:59\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"51\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"Nisar \",\n" +
            "      \"name_malayalam\": \"നിസാർ\",\n" +
            "      \"place\": \"നടയ്ക്കൽ \",\n" +
            "      \"phone\": \"9946317089\",\n" +
            "      \"created_at\": \"2018-09-15 15:17:58\",\n" +
            "      \"updated_at\": \"2018-09-15 15:17:58\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"76\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"nizar\",\n" +
            "      \"name_malayalam\": \"നിസാർ \",\n" +
            "      \"place\": \"നടക്കല്\u200D \",\n" +
            "      \"phone\": \"9946317089\",\n" +
            "      \"created_at\": \"2018-09-19 16:04:28\",\n" +
            "      \"updated_at\": \"2018-09-19 16:04:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"113\",\n" +
            "      \"category_id\": \"13\",\n" +
            "      \"name_english\": \"NObil events\",\n" +
            "      \"name_malayalam\": \"നോബിൾ ഇവന്റസ്\u200C   \",\n" +
            "      \"place\": \"ചെമ്മലമറ്റം\",\n" +
            "      \"phone\": \"9605801342\",\n" +
            "      \"created_at\": \"2018-09-27 19:03:46\",\n" +
            "      \"updated_at\": \"2018-09-27 19:03:46\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"54\",\n" +
            "      \"category_id\": \"11\",\n" +
            "      \"name_english\": \"noushad kunnapallil\",\n" +
            "      \"name_malayalam\": \"നൗഷാദ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847946243\",\n" +
            "      \"created_at\": \"2018-09-15 19:01:25\",\n" +
            "      \"updated_at\": \"2018-09-15 19:01:25\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"97\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"P\",\n" +
            "      \"name_malayalam\": \"പ്രഫഷനൽ കൊറിയർ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9544723593\",\n" +
            "      \"created_at\": \"2018-09-22 19:40:09\",\n" +
            "      \"updated_at\": \"2018-09-22 19:40:09\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"108\",\n" +
            "      \"category_id\": \"27\",\n" +
            "      \"name_english\": \"P.A. Agencies \",\n" +
            "      \"name_malayalam\": \"പി. എ. ഏജൻസീസ്, പത്താഴപ്പടി\",\n" +
            "      \"place\": \"20, 5, 2, 1 ലിറ്റർ, 500, 300 ml\",\n" +
            "      \"phone\": \"9847232765\",\n" +
            "      \"created_at\": \"2018-09-25 14:41:23\",\n" +
            "      \"updated_at\": \"2018-09-25 14:41:23\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"109\",\n" +
            "      \"category_id\": \"27\",\n" +
            "      \"name_english\": \"palazhi\",\n" +
            "      \"name_malayalam\": \"പാലാഴി ( ബോട്ടില്\u200D കുടിവെള്ളം )\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9961699215\",\n" +
            "      \"created_at\": \"2018-09-25 17:14:19\",\n" +
            "      \"updated_at\": \"2018-09-25 17:14:19\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"41\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Pepper leaf\",\n" +
            "      \"name_malayalam\": \"പെപ്പർ ലീഫ്\",\n" +
            "      \"place\": \"കോട്ടയം\",\n" +
            "      \"phone\": \"8447994444\",\n" +
            "      \"created_at\": \"2018-09-04 12:08:21\",\n" +
            "      \"updated_at\": \"2018-09-04 12:08:21\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"30\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Positive Catering \",\n" +
            "      \"name_malayalam\": \"പോസിറ്റീവ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847002774\",\n" +
            "      \"created_at\": \"2018-08-07 22:19:45\",\n" +
            "      \"updated_at\": \"2018-08-07 22:19:45\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"131\",\n" +
            "      \"category_id\": \"23\",\n" +
            "      \"name_english\": \"Pv granites \",\n" +
            "      \"name_malayalam\": \"പി വി ഗ്രാനൈറ്സ്  \",\n" +
            "      \"place\": \"മങ്കൊബ്  \",\n" +
            "      \"phone\": \"9947133418\",\n" +
            "      \"created_at\": \"2018-10-25 09:03:34\",\n" +
            "      \"updated_at\": \"2018-10-25 09:03:34\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"18\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"rahmaniya\",\n" +
            "      \"name_malayalam\": \"റഹ്മാനിയ ഹയറിങ് & ഡെക്കറേഷൻ  ഡെക്കറേഷൻ\",\n" +
            "      \"place\": \"തെക്കേക്കര \",\n" +
            "      \"phone\": \"8943474845\",\n" +
            "      \"created_at\": \"2018-07-21 15:05:47\",\n" +
            "      \"updated_at\": \"2018-09-14 09:30:42\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"85\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"rahmaniya\",\n" +
            "      \"name_malayalam\": \" റഹ്മാനിയ ഹയറിംഗ്സ് & പന്തൽ വർക്കുകൾ\",\n" +
            "      \"place\": \"എം ഇ സ് ജം. ,ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9947804649\",\n" +
            "      \"created_at\": \"2018-09-21 21:09:48\",\n" +
            "      \"updated_at\": \"2018-09-21 21:09:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"3\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Rashid Parikkutti\",\n" +
            "      \"name_malayalam\": \"റാഷിദ് പരിക്കുട്ടി (ഡ്രീം ഹോം)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8281809026\",\n" +
            "      \"created_at\": \"2018-07-17 19:08:28\",\n" +
            "      \"updated_at\": \"2018-07-17 19:08:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"36\",\n" +
            "      \"category_id\": \"12\",\n" +
            "      \"name_english\": \"RBG\",\n" +
            "      \"name_malayalam\": \"ആർ.ബി.ജി കമ്മോഡിറ്റീസ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447213027\",\n" +
            "      \"created_at\": \"2018-09-02 22:45:24\",\n" +
            "      \"updated_at\": \"2018-09-02 22:45:24\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"45\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Rhm\",\n" +
            "      \"name_malayalam\": \"ആർ. എച് . എം \",\n" +
            "      \"place\": \"എം ഈ എസ് ജങ്ഷൻ \",\n" +
            "      \"phone\": \"04822276648\",\n" +
            "      \"created_at\": \"2018-09-11 11:53:51\",\n" +
            "      \"updated_at\": \"2018-09-11 11:53:51\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"86\",\n" +
            "      \"category_id\": \"14\",\n" +
            "      \"name_english\": \"RHM\",\n" +
            "      \"name_malayalam\": \"RHM സൗണ്ട് സ്\",\n" +
            "      \"place\": \"എം ഇ സ് ജം. ,ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9947804649\",\n" +
            "      \"created_at\": \"2018-09-21 21:11:06\",\n" +
            "      \"updated_at\": \"2018-09-21 21:11:06\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"31\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Riyas\",\n" +
            "      \"name_malayalam\": \"റിയാസ് (പടിപ്പുര  ഡെക്കറേഷൻസ്) \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9747735735     \",\n" +
            "      \"created_at\": \"2018-08-08 13:18:05\",\n" +
            "      \"updated_at\": \"2018-08-08 13:18:05\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"32\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Riyas 1\",\n" +
            "      \"name_malayalam\": \"റിയാസ് ( പടിപ്പുര )\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8606735735\",\n" +
            "      \"created_at\": \"2018-08-08 13:19:31\",\n" +
            "      \"updated_at\": \"2018-08-08 13:19:31\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"39\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Royal\",\n" +
            "      \"name_malayalam\": \"റോയൽ കാറ്റേഴ്\u200Cസ്\",\n" +
            "      \"place\": \"സെൻട്രൽ ജങ്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9747552259\",\n" +
            "      \"created_at\": \"2018-09-03 14:29:00\",\n" +
            "      \"updated_at\": \"2018-09-03 14:29:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"46\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Royal\",\n" +
            "      \"name_malayalam\": \"റോയൽ ഡെക്കറേഷൻ\",\n" +
            "      \"place\": \"സെൻട്രൽ ജങ്ഷൻ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447571644\",\n" +
            "      \"created_at\": \"2018-09-11 11:54:46\",\n" +
            "      \"updated_at\": \"2018-09-11 11:54:46\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"84\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Safa\",\n" +
            "      \"name_malayalam\": \"സഫാ കേറ്ററിംഗ് \",\n" +
            "      \"place\": \"നടക്കൽ,ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947340378\",\n" +
            "      \"created_at\": \"2018-09-21 08:49:38\",\n" +
            "      \"updated_at\": \"2018-09-21 08:49:38\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"87\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"safa\",\n" +
            "      \"name_malayalam\": \"സഫാ കാറ്ററിംഗ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9544073736\",\n" +
            "      \"created_at\": \"2018-09-22 09:40:03\",\n" +
            "      \"updated_at\": \"2018-09-22 09:40:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"13\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"savad\",\n" +
            "      \"name_malayalam\": \"സവാദ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"97442 21186\",\n" +
            "      \"created_at\": \"2018-07-18 13:15:38\",\n" +
            "      \"updated_at\": \"2018-07-18 13:15:38\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"72\",\n" +
            "      \"category_id\": \"18\",\n" +
            "      \"name_english\": \"SHABEEB V.A.\",\n" +
            "      \"name_malayalam\": \"ഷബീബ് വി.എ.\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"7012013090\",\n" +
            "      \"created_at\": \"2018-09-17 17:26:49\",\n" +
            "      \"updated_at\": \"2018-09-17 17:26:49\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"134\",\n" +
            "      \"category_id\": \"27\",\n" +
            "      \"name_english\": \"Shafeek\\t??????????  \",\n" +
            "      \"name_malayalam\": \"ഷെഫീഖ് -3000 ലിറ്റർ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"99614012 10\",\n" +
            "      \"created_at\": \"2018-11-29 09:53:03\",\n" +
            "      \"updated_at\": \"2018-11-29 09:53:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"60\",\n" +
            "      \"category_id\": \"11\",\n" +
            "      \"name_english\": \"shafeek\",\n" +
            "      \"name_malayalam\": \"ഷഫീക് \",\n" +
            "      \"place\": \"നടക്കല്\u200D \",\n" +
            "      \"phone\": \"9447571842\",\n" +
            "      \"created_at\": \"2018-09-15 21:12:21\",\n" +
            "      \"updated_at\": \"2018-09-15 21:12:21\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"119\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"Shameer PU \",\n" +
            "      \"name_malayalam\": \"ഷെമീർ പി യു  \",\n" +
            "      \"place\": \"ആനിയിളപ്പ്, ഈരാറ്റുപേട്ട  \",\n" +
            "      \"phone\": \"9495111266\",\n" +
            "      \"created_at\": \"2018-10-02 15:08:33\",\n" +
            "      \"updated_at\": \"2018-10-02 15:08:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"130\",\n" +
            "      \"category_id\": \"19\",\n" +
            "      \"name_english\": \"shamon\",\n" +
            "      \"name_malayalam\": \"ഷമോൻ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447981222 \",\n" +
            "      \"created_at\": \"2018-10-25 08:37:45\",\n" +
            "      \"updated_at\": \"2018-10-25 08:37:45\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"49\",\n" +
            "      \"category_id\": \"9\",\n" +
            "      \"name_english\": \"Shaneer \",\n" +
            "      \"name_malayalam\": \"ഷനീർ മഠത്തിൽ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"96331 18228\",\n" +
            "      \"created_at\": \"2018-09-15 08:18:42\",\n" +
            "      \"updated_at\": \"2018-09-15 08:18:42\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"106\",\n" +
            "      \"category_id\": \"18\",\n" +
            "      \"name_english\": \"shebeeb va \",\n" +
            "      \"name_malayalam\": \"ഷെബീബ് വി എ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"7012013090\",\n" +
            "      \"created_at\": \"2018-09-24 21:31:48\",\n" +
            "      \"updated_at\": \"2018-09-24 21:31:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"4\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Shibili\",\n" +
            "      \"name_malayalam\": \"ശിബിലി ചോച്ചു (ക്രിയേറ്റീവ്)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447104780\",\n" +
            "      \"created_at\": \"2018-07-17 19:09:34\",\n" +
            "      \"updated_at\": \"2018-07-17 19:09:34\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"2\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Siyad Rahim\",\n" +
            "      \"name_malayalam\": \"സിയാദ് റഹീം (സ്കൈലൈൻ)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447395253\",\n" +
            "      \"created_at\": \"2018-07-17 19:07:16\",\n" +
            "      \"updated_at\": \"2018-07-17 19:07:16\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"129\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"sogo solution\",\n" +
            "      \"name_malayalam\": \"സോഗോ സൊലൂഷൻ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847018148\",\n" +
            "      \"created_at\": \"2018-10-09 17:55:07\",\n" +
            "      \"updated_at\": \"2018-10-09 17:55:07\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"57\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"SOlution\",\n" +
            "      \"name_malayalam\": \"സൊലൂഷൻ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9605205505\",\n" +
            "      \"created_at\": \"2018-09-15 19:46:46\",\n" +
            "      \"updated_at\": \"2018-09-15 19:46:46\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"98\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"Speed and safe\",\n" +
            "      \"name_malayalam\": \"സ്പീഡ് ആൻഡ് സേഫ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822276412\",\n" +
            "      \"created_at\": \"2018-09-22 19:43:32\",\n" +
            "      \"updated_at\": \"2018-09-22 19:43:32\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"107\",\n" +
            "      \"category_id\": \"25\",\n" +
            "      \"name_english\": \"St courier &Franch express. Overnite Express.\",\n" +
            "      \"name_malayalam\": \"കൊറിയർ സർവീസ്. (സാലിം) \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8891919256\",\n" +
            "      \"created_at\": \"2018-09-24 21:39:53\",\n" +
            "      \"updated_at\": \"2018-09-24 21:39:53\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"111\",\n" +
            "      \"category_id\": \"14\",\n" +
            "      \"name_english\": \"Star\",\n" +
            "      \"name_malayalam\": \"സ്റ്റാർ സൗണ്ട്സ്\",\n" +
            "      \"place\": \"പൂഞ്ഞാർ \",\n" +
            "      \"phone\": \"9447139648\",\n" +
            "      \"created_at\": \"2018-09-27 19:00:08\",\n" +
            "      \"updated_at\": \"2018-09-27 19:00:08\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"112\",\n" +
            "      \"category_id\": \"6\",\n" +
            "      \"name_english\": \"Star\",\n" +
            "      \"name_malayalam\": \"സ്റ്റാർ സൗണ്ട് \",\n" +
            "      \"place\": \"പൂഞ്ഞാർ\",\n" +
            "      \"phone\": \"964560 1990\",\n" +
            "      \"created_at\": \"2018-09-27 19:02:17\",\n" +
            "      \"updated_at\": \"2018-09-27 19:02:17\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"6\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Suhail VK\",\n" +
            "      \"name_malayalam\": \"സുഹൈൽ വി.കെ(അസോസിയേറ്റ്)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947320019\",\n" +
            "      \"created_at\": \"2018-07-17 19:11:49\",\n" +
            "      \"updated_at\": \"2018-07-17 19:11:49\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"8\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Sulaiman TA\",\n" +
            "      \"name_malayalam\": \"സുലൈമാൻ ടി.എ(ആർകിടെക്)\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847701420\",\n" +
            "      \"created_at\": \"2018-07-17 19:14:45\",\n" +
            "      \"updated_at\": \"2018-09-27 08:48:44\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"94\",\n" +
            "      \"category_id\": \"19\",\n" +
            "      \"name_english\": \"suraj\",\n" +
            "      \"name_malayalam\": \"സൂരജ് മോട്ടോർ ഡ്രൈവിംഗ് സ്കൂൾ.\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട,തിടനാട്, പാലാ\",\n" +
            "      \"phone\": \"9446396721\",\n" +
            "      \"created_at\": \"2018-09-22 17:56:05\",\n" +
            "      \"updated_at\": \"2018-09-22 17:56:05\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"21\",\n" +
            "      \"category_id\": \"8\",\n" +
            "      \"name_english\": \"Synyne Enterprise\",\n" +
            "      \"name_malayalam\": \"സിനീൻ എന്റർപ്രൈസ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947238283\",\n" +
            "      \"created_at\": \"2018-07-25 15:46:35\",\n" +
            "      \"updated_at\": \"2018-09-08 21:18:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"83\",\n" +
            "      \"category_id\": \"23\",\n" +
            "      \"name_english\": \"Thadathil\",\n" +
            "      \"name_malayalam\": \"തടത്തിൽ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9656464893\",\n" +
            "      \"created_at\": \"2018-09-20 23:39:01\",\n" +
            "      \"updated_at\": \"2018-09-20 23:39:01\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"80\",\n" +
            "      \"category_id\": \"22\",\n" +
            "      \"name_english\": \"TS AbdulSalam quasimi\",\n" +
            "      \"name_malayalam\": \"ടി എസ് അബ്\u200Dദുൾ സലാം ഖസ്സിമി \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9446186946 \",\n" +
            "      \"created_at\": \"2018-09-20 10:08:57\",\n" +
            "      \"updated_at\": \"2018-09-20 10:08:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"33\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"U m S catering \",\n" +
            "      \"name_malayalam\": \"യു  എം എസ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847190947\",\n" +
            "      \"created_at\": \"2018-08-09 06:47:16\",\n" +
            "      \"updated_at\": \"2018-09-15 19:44:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"62\",\n" +
            "      \"category_id\": \"16\",\n" +
            "      \"name_english\": \"unais kkhan\",\n" +
            "      \"name_malayalam\": \"അഡ്വ. ഉനൈസ് ഖാന്\u200D \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9495111170\",\n" +
            "      \"created_at\": \"2018-09-16 11:32:15\",\n" +
            "      \"updated_at\": \"2018-09-16 11:32:15\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"67\",\n" +
            "      \"category_id\": \"16\",\n" +
            "      \"name_english\": \"vp nasar\",\n" +
            "      \"name_malayalam\": \"അഡ്വ. വി.പി നാസര്\u200D \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9744221106\",\n" +
            "      \"created_at\": \"2018-09-16 15:07:54\",\n" +
            "      \"updated_at\": \"2018-09-16 15:07:54\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"20\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"wafa\",\n" +
            "      \"name_malayalam\": \"വഫാ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847504803\",\n" +
            "      \"created_at\": \"2018-07-24 20:11:20\",\n" +
            "      \"updated_at\": \"2018-07-24 20:11:20\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"55\",\n" +
            "      \"category_id\": \"8\",\n" +
            "      \"name_english\": \"Winfix\",\n" +
            "      \"name_malayalam\": \"വിൻഫിക്സ്\",\n" +
            "      \"place\": \"ഹാരിസ് മൗലവി \",\n" +
            "      \"phone\": \"9747650176\",\n" +
            "      \"created_at\": \"2018-09-15 19:06:28\",\n" +
            "      \"updated_at\": \"2018-09-15 19:06:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"intent_id\": \"14\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"ZAMZAM CATERING, SHUHAIB\",\n" +
            "      \"name_malayalam\": \"സംസം കാറ്ററിങ്, \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9645540046\",\n" +
            "      \"created_at\": \"2018-07-21 13:26:44\",\n" +
            "      \"updated_at\": \"2018-09-15 19:44:23\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevanam_detail);

        setUpFlipView();
        title = getIntent().getStringExtra("title");
        intent_id = getIntent().getStringExtra("id");

        adapter = new Adapter(getSevanamData());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(SevanamDetail.this, R.dimen.item_offset);
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
    private List<Data> getSevanamData() {

        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray contacts = jsonObj.getJSONArray("services");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject sevanam = contacts.getJSONObject(i);
                String id = sevanam.getString("category_id");
                if (id.equals(intent_id)){
                    String name = sevanam.getString("name_malayalam");
                    String location = sevanam.getString("place");
                    Data data = new Data();
                    data.setSevanam_detail_title(name);
                    data.setSevanam_detail_location(location);
                    data.setSevanam_detail_mobile(sevanam.getString("phone"));
                    list.add(data);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
        List<Data> list;
        public Adapter(List<Data> list){
            this.list = list;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_detail_sevanam, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_sevanam_detail_title.setText(data.getSevanam_detail_title());
            myViewHolder.txt_sevanam_detail_location.setText(data.getSevanam_detail_location());
            myViewHolder.txt_sevanam_detail_mobile.setText(data.getSevanam_detail_mobile());
            myViewHolder.imageView11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri number = Uri.parse("tel:" + data.getSevanam_detail_mobile());
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
            TextView txt_sevanam_detail_title,txt_sevanam_detail_location,txt_sevanam_detail_mobile;
            ImageView imageView11;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_sevanam_detail_title = itemView.findViewById(R.id.txt_sevanam_detail_title);
                txt_sevanam_detail_location = itemView.findViewById(R.id.txt_sevanam_detail_location);
                txt_sevanam_detail_mobile = itemView.findViewById(R.id.txt_sevanam_detail_mobile);
                imageView11 = itemView.findViewById(R.id.imageView11);


            }
        }
    }
}
