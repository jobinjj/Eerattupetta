package com.techpakka.eerattupetta;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

public class SthapanangalDetail extends AppCompatActivity {
ArrayList<Data> list = new ArrayList<>();
    private RecyclerView recyclerView;
    int index = 0;
    private ViewFlipper view_flipper;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String[] ad_images = new String[3];
    private Adapter adapter;
    String data = "{\n" +
            "  \"institution\": [\n" +
            "    {\n" +
            "      \"id\": \"46\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"A. muhidheen\",\n" +
            "      \"name_malayalam\": \"മുഹിയിദ്ദീൻ ജുമാമസ്ജിദ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822275080\",\n" +
            "      \"created_at\": \"2018-08-23 16:55:33\",\n" +
            "      \"updated_at\": \"2018-09-07 12:04:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"52\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Adhithiya\",\n" +
            "      \"name_malayalam\": \"ആദിത്യ ഗുരുവായൂർ പപ്പടം \",\n" +
            "      \"place\": \"മാർക്കറ്റ് റോഡ്, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9747145926\",\n" +
            "      \"created_at\": \"2018-09-08 16:02:59\",\n" +
            "      \"updated_at\": \"2018-09-08 16:02:59\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"52\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Subiksham food products\",\n" +
            "      \"name_malayalam\": \"സുഭിക്ഷം ഫുഡ് പ്രോഡക്റ്റ്സ് \",\n" +
            "      \"place\": \"മാർക്കറ്റ് റോഡ്, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"94970 31028\",\n" +
            "      \"created_at\": \"2018-09-08 16:02:59\",\n" +
            "      \"updated_at\": \"2018-09-08 16:02:59\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"31\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"AEo\",\n" +
            "      \"name_malayalam\": \"എ ഇ ഒ ഓഫീസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822277475\",\n" +
            "      \"created_at\": \"2018-08-13 18:51:46\",\n" +
            "      \"updated_at\": \"2018-08-13 18:51:46\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"76\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Ajmi foods\",\n" +
            "      \"name_malayalam\": \"അജ്മി ഫുഡ് പ്രൊഡക്ട്\u200Cസ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"1800 425 2161\",\n" +
            "      \"created_at\": \"2018-09-23 22:22:44\",\n" +
            "      \"updated_at\": \"2018-09-23 22:22:44\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"79\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"al fithra\",\n" +
            "      \"name_malayalam\": \"അൽ ഫിത്ത്റ ഇസ്ലാമിക് പ്രീ സ്\u200Cകൂൾ \",\n" +
            "      \"place\": \"നെല്ലിക്കച്ചാൽ പൂഞ്ഞാർ പി.ഒ  \",\n" +
            "      \"phone\": \"8606429400\",\n" +
            "      \"created_at\": \"2018-09-24 08:22:30\",\n" +
            "      \"updated_at\": \"2018-09-24 08:22:30\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"8\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Al Manar Senior Secondary School\",\n" +
            "      \"name_malayalam\": \"അൽ മനാർ സീനിയർ സെക്കണ്ടറി സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822273024\",\n" +
            "      \"created_at\": \"2018-07-28 13:17:33\",\n" +
            "      \"updated_at\": \"2018-07-28 13:17:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"86\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Amro \",\n" +
            "      \"name_malayalam\": \"അംറോ സ്റ്റീൽസ് \",\n" +
            "      \"place\": \"മുട്ടം ജങ്ഷൻ\",\n" +
            "      \"phone\": \"9744873887\",\n" +
            "      \"created_at\": \"2018-09-28 20:08:06\",\n" +
            "      \"updated_at\": \"2018-09-28 20:08:06\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"48\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"ANkalamman\",\n" +
            "      \"name_malayalam\": \"അങ്കാളമ്മൻ കോവിൽ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447356548\",\n" +
            "      \"created_at\": \"2018-08-23 16:58:48\",\n" +
            "      \"updated_at\": \"2018-09-07 12:05:36\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"65\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"bismi fd\",\n" +
            "      \"name_malayalam\": \"ബിസ്മി ഫുഡ് പ്രൊഡക്ട്\",\n" +
            "      \"place\": \"നടയ്ക്കൽ, ഈരാറ്റുപേട്ട.  \",\n" +
            "      \"phone\": \"9747330064\",\n" +
            "      \"created_at\": \"2018-09-15 22:12:42\",\n" +
            "      \"updated_at\": \"2018-09-15 22:12:42\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"33\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Bsnl\",\n" +
            "      \"name_malayalam\": \"ബി എസ് എൻ എൽ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822273053\",\n" +
            "      \"created_at\": \"2018-08-13 18:57:00\",\n" +
            "      \"updated_at\": \"2018-08-13 18:57:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"54\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Bull\",\n" +
            "      \"name_malayalam\": \"ബുൾ ബുൾ സോഡാ\",\n" +
            "      \"place\": \"നടക്കൽ,ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9947198359\",\n" +
            "      \"created_at\": \"2018-09-08 16:05:09\",\n" +
            "      \"updated_at\": \"2018-09-08 16:05:09\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"56\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Ccm\",\n" +
            "      \"name_malayalam\": \"സിസിഎം ഓയിൽസ് & കോഫി വർക്\u200Cസ്\",\n" +
            "      \"place\": \"നിയർ കെ എസ്. ആർ. ടി സി സ്റ്റൻഡ്\",\n" +
            "      \"phone\": \"04822272120\",\n" +
            "      \"created_at\": \"2018-09-08 16:09:20\",\n" +
            "      \"updated_at\": \"2018-09-08 16:09:20\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"34\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Civil\",\n" +
            "      \"name_malayalam\": \"സിവിൽ സപ്ലൈസ് കോർപറേഷൻ\",\n" +
            "      \"place\": \"പാലാ\",\n" +
            "      \"phone\": \"04822212445\",\n" +
            "      \"created_at\": \"2018-08-13 18:58:36\",\n" +
            "      \"updated_at\": \"2018-08-13 18:58:36\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"24\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"College of Engineering Poonjar\",\n" +
            "      \"name_malayalam\": \"കോളേജ് ഓഫ് എൻജിനീയറിങ് പൂഞ്ഞാർ\",\n" +
            "      \"place\": \"പൂഞ്ഞാർ  \",\n" +
            "      \"phone\": \"04822 271737\",\n" +
            "      \"created_at\": \"2018-07-28 13:49:37\",\n" +
            "      \"updated_at\": \"2018-07-28 13:49:37\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"73\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Darussalam women's arabic college\",\n" +
            "      \"name_malayalam\": \"ദാറുസ്സലാം വിമൺസ് അറബിക് കോളേജ്  \",\n" +
            "      \"place\": \"കാഞ്ഞിരപ്പള്ളി  \",\n" +
            "      \"phone\": \"04828203371\",\n" +
            "      \"created_at\": \"2018-09-19 17:55:00\",\n" +
            "      \"updated_at\": \"2018-09-23 22:26:34\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"32\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Deo\",\n" +
            "      \"name_malayalam\": \"ഡി ഇ ഒ കോട്ടയം\",\n" +
            "      \"place\": \"കോട്ടയം\",\n" +
            "      \"phone\": \"04812583095\",\n" +
            "      \"created_at\": \"2018-08-13 18:54:54\",\n" +
            "      \"updated_at\": \"2018-08-13 18:54:54\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"80\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"dharul quran\",\n" +
            "      \"name_malayalam\": \"ദാറുൽ ഖുർആൻ ഹിഫ്ള് കോളേജ് \",\n" +
            "      \"place\": \"നെല്ലിക്കച്ചാൽ പൂഞ്ഞാർ പി.ഒ \",\n" +
            "      \"phone\": \"9544l91572\",\n" +
            "      \"created_at\": \"2018-09-24 08:23:23\",\n" +
            "      \"updated_at\": \"2018-09-24 08:23:23\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"35\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"ERAtt\",\n" +
            "      \"name_malayalam\": \"മുൻസിപ്പാലിറ്റി\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272063\",\n" +
            "      \"created_at\": \"2018-08-13 19:19:21\",\n" +
            "      \"updated_at\": \"2018-08-13 19:19:21\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"26\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"Erattupetta service \",\n" +
            "      \"name_malayalam\": \"ഈരാറ്റുപേട്ട സർവീസ് കോ ഓപറേറ്റീവ് ബാങ്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272447\",\n" +
            "      \"created_at\": \"2018-08-13 18:33:30\",\n" +
            "      \"updated_at\": \"2018-09-16 09:35:36\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"62\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"exact uniforms\",\n" +
            "      \"name_malayalam\": \"എക്സാക്റ്റ് യൂനിഫോംസ്\u200C\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"8547098345\",\n" +
            "      \"created_at\": \"2018-09-15 18:50:41\",\n" +
            "      \"updated_at\": \"2018-09-23 22:24:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"75\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"exice range office\",\n" +
            "      \"name_malayalam\": \"എക്സൈസ് റെയിഞ്ച് ആഫീസ് \",\n" +
            "      \"place\": \"കോളേജ റോഡ്  ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822277999\",\n" +
            "      \"created_at\": \"2018-09-20 13:07:14\",\n" +
            "      \"updated_at\": \"2018-09-20 13:07:14\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"federal bank\",\n" +
            "      \"name_malayalam\": \"ഫെഡറൽ ബാങ്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272241\",\n" +
            "      \"created_at\": \"2018-07-28 11:14:00\",\n" +
            "      \"updated_at\": \"2018-07-28 11:14:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"57\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"fousiya college of arts and islamic studies \",\n" +
            "      \"name_malayalam\": \"ഫൗസിയ കോളേജ് ഓഫ് ആർട്സ് & ഇസ്ലാമിക് സ്റ്റഡീസ്  \",\n" +
            "      \"place\": \"നടക്കൽ \",\n" +
            "      \"phone\": \"94957 09854\",\n" +
            "      \"created_at\": \"2018-09-15 07:43:37\",\n" +
            "      \"updated_at\": \"2018-09-15 07:43:37\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"66\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"fries food\",\n" +
            "      \"name_malayalam\": \"ഫ്രൈസ് ഫുഡ് പ്രൊഡക്ട്സ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9447339888\",\n" +
            "      \"created_at\": \"2018-09-16 07:17:55\",\n" +
            "      \"updated_at\": \"2018-09-16 07:17:55\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"16\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Government Higher Secondary School\",\n" +
            "      \"name_malayalam\": \"ഗവണ്മെന്റ് ഹയർ സെക്കൻഡറി സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822274258\",\n" +
            "      \"created_at\": \"2018-07-28 13:26:50\",\n" +
            "      \"updated_at\": \"2018-07-28 13:26:50\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"14\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Government Muslim LP School, Erattupetta\",\n" +
            "      \"name_malayalam\": \"ഗവണ്മെന്റ് മുസ്ലിം എൽ.പി സ്കൂൾ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822276414\",\n" +
            "      \"created_at\": \"2018-07-28 13:23:57\",\n" +
            "      \"updated_at\": \"2018-07-28 13:23:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"7\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"gramin\",\n" +
            "      \"name_malayalam\": \"ഗ്രാമീണ് ബാങ്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822277263\",\n" +
            "      \"created_at\": \"2018-07-28 11:29:08\",\n" +
            "      \"updated_at\": \"2018-09-16 09:39:12\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"61\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"green tech\",\n" +
            "      \"name_malayalam\": \"ഗ്രീൻ ടെക് പോളിമേഴ്\u200Cസ്  \",\n" +
            "      \"place\": \"നടക്കൽ \",\n" +
            "      \"phone\": \"98951547 22\",\n" +
            "      \"created_at\": \"2018-09-15 15:41:49\",\n" +
            "      \"updated_at\": \"2018-09-15 15:41:49\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"15\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Guidance public School\",\n" +
            "      \"name_malayalam\": \"ഗൈഡൻസ് പബ്ലിക് സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822275669\",\n" +
            "      \"created_at\": \"2018-07-28 13:25:57\",\n" +
            "      \"updated_at\": \"2018-07-28 13:25:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"17\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Hayathudeen HS School\",\n" +
            "      \"name_malayalam\": \"ഹയാതുദ്ദീൻ  ഹൈസ്കൂൾ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822277206\",\n" +
            "      \"created_at\": \"2018-07-28 13:28:06\",\n" +
            "      \"updated_at\": \"2018-09-16 10:18:31\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"3\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"IOB\",\n" +
            "      \"name_malayalam\": \"ഇൻഡ്യൻ ഓവർസീസ് ബാങ്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"0484 2376211\",\n" +
            "      \"created_at\": \"2018-07-28 11:16:58\",\n" +
            "      \"updated_at\": \"2018-09-22 09:50:55\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"30\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Kerala\",\n" +
            "      \"name_malayalam\": \"കേരള യൂണിവേഴ്\u200Cസിറ്റി\",\n" +
            "      \"place\": \"തിരുവനന്തപുരം\",\n" +
            "      \"phone\": \"04712306422\",\n" +
            "      \"created_at\": \"2018-08-13 18:48:49\",\n" +
            "      \"updated_at\": \"2018-08-13 18:48:49\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"92\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"kseb\",\n" +
            "      \"name_malayalam\": \"കെ എസ് ഇ ബി - mob \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9496008236\",\n" +
            "      \"created_at\": \"2018-11-24 21:09:46\",\n" +
            "      \"updated_at\": \"2018-11-24 21:09:46\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"90\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"kseb\",\n" +
            "      \"name_malayalam\": \"കെ എസ് ഇ ബി \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272448\",\n" +
            "      \"created_at\": \"2018-11-24 21:06:05\",\n" +
            "      \"updated_at\": \"2018-11-24 21:06:05\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"91\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"kseb\",\n" +
            "      \"name_malayalam\": \"കെ എസ് ഇ ബി \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272448\",\n" +
            "      \"created_at\": \"2018-11-24 21:06:17\",\n" +
            "      \"updated_at\": \"2018-11-24 21:06:17\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"28\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Ksfe\",\n" +
            "      \"name_malayalam\": \"കെ എസ് എഫ് ഇ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822273035\",\n" +
            "      \"created_at\": \"2018-08-13 18:36:27\",\n" +
            "      \"updated_at\": \"2018-08-13 18:36:27\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"20\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"M.E.S. College, Erattupetta\",\n" +
            "      \"name_malayalam\": \"M.E.S. കോളേജ്, ഈരാറ്റുപേട്ട\",\n" +
            "      \"place\": \"തിടനാട് \",\n" +
            "      \"phone\": \"04822272990\",\n" +
            "      \"created_at\": \"2018-07-28 13:35:04\",\n" +
            "      \"updated_at\": \"2018-07-28 13:35:04\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"89\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Maheen\",\n" +
            "      \"name_malayalam\": \"മാഹിൻ \",\n" +
            "      \"place\": \"തെക്കേക്കര, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9562206578\",\n" +
            "      \"created_at\": \"2018-10-01 21:16:31\",\n" +
            "      \"updated_at\": \"2018-10-01 21:16:31\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"81\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"malabar chips and mixer\",\n" +
            "      \"name_malayalam\": \"മലബാർ ചിപ്\u200Cസ് ആൻഡ് മിക്സ്ചർ    \",\n" +
            "      \"place\": \"നടക്കൽ , ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447 809 581 \",\n" +
            "      \"created_at\": \"2018-09-24 21:47:19\",\n" +
            "      \"updated_at\": \"2018-09-24 21:47:19\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"25\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"Mambahul \",\n" +
            "      \"name_malayalam\": \"മൻബഉൽ ഖൈറാത്ത് അറബിക്\u200C കോളേജ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട, \",\n" +
            "      \"phone\": \"9037879286\",\n" +
            "      \"created_at\": \"2018-08-05 19:28:53\",\n" +
            "      \"updated_at\": \"2018-08-05 19:28:53\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"64\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"masjidh \",\n" +
            "      \"name_malayalam\": \"മസ്ജിദുൽ ഫതഹ് (സുന്നി മസ്ജിദ് ).\",\n" +
            "      \"place\": \"മുട്ടം ജംഗ്ഷൻ\",\n" +
            "      \"phone\": \"9048044786\",\n" +
            "      \"created_at\": \"2018-09-15 22:00:41\",\n" +
            "      \"updated_at\": \"2018-09-15 22:00:41\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"78\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"masjidh thauhid\",\n" +
            "      \"name_malayalam\": \"മസ്ജിദുതൗഹീദ്  \",\n" +
            "      \"place\": \"ഗൈഡൻസ് കാമ്പസ് നെല്ലിക്കച്ചാൽ പൂഞ്ഞാർ\",\n" +
            "      \"phone\": \"9048663000\",\n" +
            "      \"created_at\": \"2018-09-24 08:17:45\",\n" +
            "      \"updated_at\": \"2018-09-24 08:17:45\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"70\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"masjidhul islam\",\n" +
            "      \"name_malayalam\": \"മസ്ജിദുൽ ഇസ്ലാം \",\n" +
            "      \"place\": \"കടുവാമുഴി \",\n" +
            "      \"phone\": \"9947657519\",\n" +
            "      \"created_at\": \"2018-09-17 17:09:13\",\n" +
            "      \"updated_at\": \"2018-09-17 17:09:13\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"82\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"masjidhul salam\",\n" +
            "      \"name_malayalam\": \"മസ്ജിദുസ്സലാം സലഫി മസ്ജിദ് \",\n" +
            "      \"place\": \"മാർകറ്റ് റോഡ് ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"94479 10959\",\n" +
            "      \"created_at\": \"2018-09-25 17:11:41\",\n" +
            "      \"updated_at\": \"2018-09-25 17:11:41\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"67\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"meenachi east urban\",\n" +
            "      \"name_malayalam\": \"മീനച്ചിൽ ഈസ്റ്റ് അർബൻ കോ.  ഓപ്പറേറ്റീവ് ബാങ്ക് ലിമ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട എം & ഇ ബ്രാഞ്ച്\",\n" +
            "      \"phone\": \" 0482227 3269\",\n" +
            "      \"created_at\": \"2018-09-16 11:16:21\",\n" +
            "      \"updated_at\": \"2018-09-16 11:16:21\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"4\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"meenachil east urban \",\n" +
            "      \"name_malayalam\": \"മീനച്ചിൽ ഈസ്റ്റ് അർബൻ കോ ഓപ്പറേറ്റീവ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822227 \",\n" +
            "      \"created_at\": \"2018-07-28 11:26:22\",\n" +
            "      \"updated_at\": \"2018-07-28 11:26:22\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"68\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"meuvb ar\",\n" +
            "      \"name_malayalam\": \"മീനച്ചിൽ ഈസ്റ്റ് അർബൻ കോ ഓപ്പറേറ്റീവ് ബാങ്ക് \",\n" +
            "      \"place\": \"അരുവിത്തുറ ബ്രാഞ്ച്\",\n" +
            "      \"phone\": \"04822 272351\",\n" +
            "      \"created_at\": \"2018-09-16 11:17:04\",\n" +
            "      \"updated_at\": \"2018-09-23 22:25:40\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"29\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Mg\",\n" +
            "      \"name_malayalam\": \"എം ജി യൂണിവേഴ്\u200Cസിറ്റി\",\n" +
            "      \"place\": \"കോട്ടയം\",\n" +
            "      \"phone\": \"04812731050\",\n" +
            "      \"created_at\": \"2018-08-13 18:45:37\",\n" +
            "      \"updated_at\": \"2018-08-13 18:45:37\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"84\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"MM drinks holdings  \",\n" +
            "      \"name_malayalam\": \"എം എം ഡ്രിങ്ക്സ് ഹോൾഡിങ്\u200Cസ് (സിപ് അപ് ) \",\n" +
            "      \"place\": \"സഫാ നഗർ , നടക്കൽ  \",\n" +
            "      \"phone\": \"9746696751\",\n" +
            "      \"created_at\": \"2018-09-27 08:01:12\",\n" +
            "      \"updated_at\": \"2018-09-27 08:01:12\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"58\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"mm mu m up school karakkad\",\n" +
            "      \"name_malayalam\": \"എം.എം.എം.യൂ.എം. യു.പി സ്കൂൾ \",\n" +
            "      \"place\": \"കാരക്കാട് \",\n" +
            "      \"phone\": \"04822276185\",\n" +
            "      \"created_at\": \"2018-09-15 08:37:58\",\n" +
            "      \"updated_at\": \"2018-09-15 08:37:58\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"9\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Muslim Girls Higher Secondary School\",\n" +
            "      \"name_malayalam\": \"മുസ്ലിം   ഗേൾസ്  ഹയർ സെക്കൻഡറി സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822277736\",\n" +
            "      \"created_at\": \"2018-07-28 13:18:48\",\n" +
            "      \"updated_at\": \"2018-07-28 13:18:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"49\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"NAdakkal\",\n" +
            "      \"name_malayalam\": \"നടക്കൽ കാവ് ദേവി ക്ഷേത്രം\",\n" +
            "      \"place\": \"നടക്കൽ,ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822277388\",\n" +
            "      \"created_at\": \"2018-08-23 17:00:09\",\n" +
            "      \"updated_at\": \"2018-08-23 17:00:09\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"45\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"Nainar\",\n" +
            "      \"name_malayalam\": \"നൈനാർ ജുമാമസ്ജിദ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822274039\",\n" +
            "      \"created_at\": \"2018-08-23 16:54:03\",\n" +
            "      \"updated_at\": \"2018-08-23 16:54:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"60\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"nalco pips\",\n" +
            "      \"name_malayalam\": \"നാൽകോ പൈപ്പ്സ് \",\n" +
            "      \"place\": \"നടക്കൽ \",\n" +
            "      \"phone\": \"94479 10935\",\n" +
            "      \"created_at\": \"2018-09-15 15:40:57\",\n" +
            "      \"updated_at\": \"2018-09-15 15:40:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"69\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"nice \",\n" +
            "      \"name_malayalam\": \"നൈസ്  ഫുഡ് പ്രോഡക്റ്റ്  \",\n" +
            "      \"place\": \" ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9847867691\",\n" +
            "      \"created_at\": \"2018-09-16 13:08:00\",\n" +
            "      \"updated_at\": \"2018-09-16 13:08:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"63\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"nice sweets\",\n" +
            "      \"name_malayalam\": \"നൈസ് സ്വീറ്റ്\u200Cസ്\u200C\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447507593\",\n" +
            "      \"created_at\": \"2018-09-15 19:34:49\",\n" +
            "      \"updated_at\": \"2018-09-23 22:23:51\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"59\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Nowreen\",\n" +
            "      \"name_malayalam\": \"നൗറീൻ പർദ്ദ ആൻഡ് ഹിജാബ്\",\n" +
            "      \"place\": \"നടക്കൽ, ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9846597259\",\n" +
            "      \"created_at\": \"2018-09-15 15:36:48\",\n" +
            "      \"updated_at\": \"2018-09-15 15:36:48\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"36\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"PAla\",\n" +
            "      \"name_malayalam\": \"മുൻസിപ്പാലിറ്റി\",\n" +
            "      \"place\": \"പാലാ\",\n" +
            "      \"phone\": \"04822212328\",\n" +
            "      \"created_at\": \"2018-08-13 19:20:30\",\n" +
            "      \"updated_at\": \"2018-08-13 19:20:30\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"39\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ഗ്രാമ പഞ്ചായത്ത്\u200C\",\n" +
            "      \"place\": \"പൂഞ്ഞാർ\",\n" +
            "      \"phone\": \"04822272184\",\n" +
            "      \"created_at\": \"2018-08-13 19:28:03\",\n" +
            "      \"updated_at\": \"2018-08-13 19:28:03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"43\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ഗ്രാമ പഞ്ചായത്ത്\u200C\",\n" +
            "      \"place\": \"തീക്കോയി\",\n" +
            "      \"phone\": \"04822281029\",\n" +
            "      \"created_at\": \"2018-08-13 19:35:57\",\n" +
            "      \"updated_at\": \"2018-08-13 19:35:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"42\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ഗ്രാമ പഞ്ചായത്ത്\u200C \",\n" +
            "      \"place\": \"തിടനാട്\",\n" +
            "      \"phone\": \"04822272068\",\n" +
            "      \"created_at\": \"2018-08-13 19:34:58\",\n" +
            "      \"updated_at\": \"2018-08-13 19:34:58\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"38\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ബ്ലോക്ക് പഞ്ചായത്ത് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272356\",\n" +
            "      \"created_at\": \"2018-08-13 19:23:47\",\n" +
            "      \"updated_at\": \"2018-08-13 19:23:47\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"41\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ഗ്രാമ പഞ്ചായത്ത്\u200C \",\n" +
            "      \"place\": \"തലപ്പാലം\",\n" +
            "      \"phone\": \"04822272195\",\n" +
            "      \"created_at\": \"2018-08-13 19:33:57\",\n" +
            "      \"updated_at\": \"2018-08-13 19:33:57\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"37\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ഡിസ്ട്രിക്ട് പഞ്ചായത്ത്\",\n" +
            "      \"place\": \"കോട്ടയം\",\n" +
            "      \"phone\": \"04812565966\",\n" +
            "      \"created_at\": \"2018-08-13 19:22:05\",\n" +
            "      \"updated_at\": \"2018-08-13 19:22:05\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"40\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Panchayath\",\n" +
            "      \"name_malayalam\": \"ഗ്രാമ പഞ്ചായത്ത്\u200C \",\n" +
            "      \"place\": \"പൂഞ്ഞാർ \",\n" +
            "      \"phone\": \"04822272171\",\n" +
            "      \"created_at\": \"2018-08-13 19:31:39\",\n" +
            "      \"updated_at\": \"2018-08-13 19:31:39\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"50\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Parvin \",\n" +
            "      \"name_malayalam\": \"പർവിൻ പർദ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822 275770\",\n" +
            "      \"created_at\": \"2018-08-26 16:32:03\",\n" +
            "      \"updated_at\": \"2018-09-19 15:30:45\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"83\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"psa pookoya thangal lp school\",\n" +
            "      \"name_malayalam\": \"പി.എം.എസ്.എ പൂക്കോയ തങ്ങൾ എൽ.പി സ്കൂൾ \",\n" +
            "      \"place\": \"കടുവാമുഴി \",\n" +
            "      \"phone\": \"9744221106\",\n" +
            "      \"created_at\": \"2018-09-26 16:43:32\",\n" +
            "      \"updated_at\": \"2018-09-26 16:43:32\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"44\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"Puthan palli\",\n" +
            "      \"name_malayalam\": \"പുത്തൻ പള്ളി ജുമാമസ്ജിദ് \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822273003\",\n" +
            "      \"created_at\": \"2018-08-23 16:52:49\",\n" +
            "      \"updated_at\": \"2018-08-23 16:52:49\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"53\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Raveendra\",\n" +
            "      \"name_malayalam\": \"രവീന്ദ്രസ് പപ്പടം\",\n" +
            "      \"place\": \"മാർക്കറ്റ് റോഡ്, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9747031360\",\n" +
            "      \"created_at\": \"2018-09-08 16:04:20\",\n" +
            "      \"updated_at\": \"2018-09-08 16:04:20\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"18\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"S.M.V Higher Secondary school\",\n" +
            "      \"name_malayalam\": \" എസ്.എം.വി. ഹയർ സെക്കൻഡറി സ്കൂൾ\",\n" +
            "      \"place\": \"പൂഞ്ഞാർ\",\n" +
            "      \"phone\": \"04822 276 386\",\n" +
            "      \"created_at\": \"2018-07-28 13:31:04\",\n" +
            "      \"updated_at\": \"2018-07-28 13:31:04\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"77\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"salafi masjidh\",\n" +
            "      \"name_malayalam\": \"സലഫി മസ്ജിദ് .  \",\n" +
            "      \"place\": \"തെക്കേക്കര ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"9447847056\",\n" +
            "      \"created_at\": \"2018-09-24 08:16:00\",\n" +
            "      \"updated_at\": \"2018-09-24 08:16:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"74\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"sangamam\",\n" +
            "      \"name_malayalam\": \"സംഗമം മൾട്ടി സ്റ്റേറ്റ് കോ -ഓപ്പറേറ്റീവ് ക്രെഡിറ്റ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822276928\",\n" +
            "      \"created_at\": \"2018-09-20 13:06:13\",\n" +
            "      \"updated_at\": \"2018-09-23 22:25:20\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"5\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"sbi\",\n" +
            "      \"name_malayalam\": \"എസ് ബി ഐ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272408\",\n" +
            "      \"created_at\": \"2018-07-28 11:27:19\",\n" +
            "      \"updated_at\": \"2018-07-28 11:27:19\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"1\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"services co - operative Bank\",\n" +
            "      \"name_malayalam\": \"സർവീസ് കോ - ഒപ്പറേറ്റീവ് ബാങ്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272421\",\n" +
            "      \"created_at\": \"2018-07-28 11:12:31\",\n" +
            "      \"updated_at\": \"2018-07-28 11:12:31\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"72\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"sprim\",\n" +
            "      \"name_malayalam\": \"സുപ്രീം ഫുഡ്പ്രൊഡക്ട്  \",\n" +
            "      \"place\": \"കാരക്കാട്\",\n" +
            "      \"phone\": \"8848701078\",\n" +
            "      \"created_at\": \"2018-09-17 18:00:20\",\n" +
            "      \"updated_at\": \"2018-09-17 18:00:20\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"55\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Srk\",\n" +
            "      \"name_malayalam\": \"എസ് ആർ കെ സോഡ\",\n" +
            "      \"place\": \"മറ്റക്കാട്\",\n" +
            "      \"phone\": \"9961898279\",\n" +
            "      \"created_at\": \"2018-09-08 16:06:58\",\n" +
            "      \"updated_at\": \"2018-09-08 16:06:58\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"23\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St Mary's High School\",\n" +
            "      \"name_malayalam\": \"സെന്റ് മേരീസ് ഹൈസ്കൂൾ\",\n" +
            "      \"place\": \"തീക്കോയി\",\n" +
            "      \"phone\": \"04822281049\",\n" +
            "      \"created_at\": \"2018-07-28 13:42:42\",\n" +
            "      \"updated_at\": \"2018-07-28 13:42:42\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"11\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St Mary's LP School\",\n" +
            "      \"name_malayalam\": \"സെന്റ് മേരീസ് എൽ.പി. സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822274258\",\n" +
            "      \"created_at\": \"2018-07-28 13:21:12\",\n" +
            "      \"updated_at\": \"2018-07-28 13:21:12\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"10\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St. Alphonsa Public School and Junior College\",\n" +
            "      \"name_malayalam\": \"സെന്റ് അൽഫോൻസ പബ്ലിക് സ്കൂൾ ആൻഡ് ജൂനിയർ കോളേജ്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822273620\",\n" +
            "      \"created_at\": \"2018-07-28 13:19:55\",\n" +
            "      \"updated_at\": \"2018-07-28 13:19:55\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"21\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St. Antony's Higher Secondary School\",\n" +
            "      \"name_malayalam\": \"സെന്റ് ആന്റണീസ് ഹയർ സെക്കൻഡറി സ്കൂൾ\",\n" +
            "      \"place\": \"പ്ലാശനാൽ\",\n" +
            "      \"phone\": \"04822273578\",\n" +
            "      \"created_at\": \"2018-07-28 13:36:33\",\n" +
            "      \"updated_at\": \"2018-07-28 13:36:33\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"47\",\n" +
            "      \"category_id\": \"3\",\n" +
            "      \"name_english\": \"St. George\",\n" +
            "      \"name_malayalam\": \"സെന്റ് ജോർജ് ഫെറോന ചർച് \",\n" +
            "      \"place\": \"അരുവിത്തുറ, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822274900\",\n" +
            "      \"created_at\": \"2018-08-23 16:57:26\",\n" +
            "      \"updated_at\": \"2018-08-23 16:57:26\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"13\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St. George Higher Secondary School\",\n" +
            "      \"name_malayalam\": \"സെന്റ് ജോർജ് ഹയർ സെക്കണ്ടറി സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822 273 533\",\n" +
            "      \"created_at\": \"2018-07-28 13:22:55\",\n" +
            "      \"updated_at\": \"2018-07-28 13:22:55\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"19\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St. George's College Aruvithura\",\n" +
            "      \"name_malayalam\": \"സെന്റ് ജോർജ്ജസ് കോളേജ് അരുവിത്തുറ \",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"04822272220\",\n" +
            "      \"created_at\": \"2018-07-28 13:33:40\",\n" +
            "      \"updated_at\": \"2018-07-28 13:33:40\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"22\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"St. Mary's Higher Secondary School\",\n" +
            "      \"name_malayalam\": \" സെന്റ് മേരീസ് ഹയർ സെക്കൻഡറി സ്കൂൾ\",\n" +
            "      \"place\": \"തീക്കോയി\",\n" +
            "      \"phone\": \"04822280989\",\n" +
            "      \"created_at\": \"2018-07-28 13:40:38\",\n" +
            "      \"updated_at\": \"2018-07-28 13:40:38\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"51\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Thais\",\n" +
            "      \"name_malayalam\": \"തായ്\u200Cസ് ഫുഡ് പ്രൊഡക്ട്\",\n" +
            "      \"place\": \"വടക്കേക്കര, ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"9447571458\",\n" +
            "      \"created_at\": \"2018-09-08 16:01:28\",\n" +
            "      \"updated_at\": \"2018-09-08 16:01:28\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12\",\n" +
            "      \"category_id\": \"1\",\n" +
            "      \"name_english\": \"Thanmiya School\",\n" +
            "      \"name_malayalam\": \"തന്മിയ സ്കൂൾ\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട \",\n" +
            "      \"phone\": \"098478 77323\",\n" +
            "      \"created_at\": \"2018-07-28 13:22:05\",\n" +
            "      \"updated_at\": \"2018-07-28 13:22:05\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"27\",\n" +
            "      \"category_id\": \"2\",\n" +
            "      \"name_english\": \"Treasury\",\n" +
            "      \"name_malayalam\": \"ട്രഷറി\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272402\",\n" +
            "      \"created_at\": \"2018-08-13 18:35:24\",\n" +
            "      \"updated_at\": \"2018-08-13 18:35:24\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6\",\n" +
            "      \"category_id\": \"4\",\n" +
            "      \"name_english\": \"union bank\",\n" +
            "      \"name_malayalam\": \"യൂണിയൻ ബാങ്ക്\",\n" +
            "      \"place\": \"ഈരാറ്റുപേട്ട\",\n" +
            "      \"phone\": \"04822272330\",\n" +
            "      \"created_at\": \"2018-07-28 11:28:22\",\n" +
            "      \"updated_at\": \"2018-07-28 11:28:22\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"87\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Vadayar Aluminium company \",\n" +
            "      \"name_malayalam\": \"വടയാർ അലുമിനിയം കമ്പനി \",\n" +
            "      \"place\": \"നടക്കൽ  \",\n" +
            "      \"phone\": \"9447175659\",\n" +
            "      \"created_at\": \"2018-10-01 13:14:16\",\n" +
            "      \"updated_at\": \"2018-10-01 13:14:16\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"88\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Vadayar Hollow Bricks \",\n" +
            "      \"name_malayalam\": \"വടയാർ ഹോളോ ബ്രിക്സ്  \",\n" +
            "      \"place\": \"നടക്കൽ  \",\n" +
            "      \"phone\": \"9447767379\",\n" +
            "      \"created_at\": \"2018-10-01 13:15:13\",\n" +
            "      \"updated_at\": \"2018-10-01 13:15:13\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"85\",\n" +
            "      \"category_id\": \"5\",\n" +
            "      \"name_english\": \"Vibgyor Polymers (Pipe factory) \",\n" +
            "      \"name_malayalam\": \"Vibgyor Polymers (Pipe factory) \",\n" +
            "      \"place\": \"നടക്കല്\u200D \",\n" +
            "      \"phone\": \"9847905074\",\n" +
            "      \"created_at\": \"2018-09-28 11:27:25\",\n" +
            "      \"updated_at\": \"2018-09-28 11:27:25\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sthapanangal_detail);

        setUpFlipView();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(SthapanangalDetail.this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        id = getIntent().getStringExtra("id");
        getInstitutionData();

        adapter = new Adapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void getInstitutionData() {
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray contacts = jsonObj.getJSONArray("institution");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                if (c.getString("category_id").equals(id)){
                    String name = c.getString("name_malayalam");
                    String location = c.getString("place");
                    Data data = new Data();
                    data.setSthapanam_detail_title(name);
                    data.setSthapanam_detail_location(location);
                    data.setSthapanam_detail_phone(c.getString("phone"));
                    list.add(data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Data data = new Data();
//        data.setSthapanam_detail_title("Institution");
//        data.setSthapanam_detail_location("Eerattupetta");
//        data.setSthapanam_detail_phone("9896987980");
//        list2.add(data);
//        Data data2 = new Data();
//        data2.setSthapanam_detail_title("Institution2");
//        data2.setSthapanam_detail_location("Eerattupetta2");
//        data2.setSthapanam_detail_phone("9896987980");
//        list2.add(data2);
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
    public class Adapter extends  RecyclerView.Adapter<Adapter.MyViewHolder>{
        List<Data> list;
        public Adapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
             View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_detail_sthapanangal, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_sthapanangal_detail_name.setText(data.getSthapanam_detail_title());
            myViewHolder.txt_sthapanangal_detail_location.setText(data.getSthapanam_detail_location());
            myViewHolder.txt_sthapanangal_detail_mobile.setText(data.getSthapanam_detail_phone());
            myViewHolder.imageView11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri number = Uri.parse("tel:" + data.getSthapanam_detail_phone());
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
            private TextView txt_sthapanangal_detail_name,txt_sthapanangal_detail_location,txt_sthapanangal_detail_mobile;
            ImageView imageView11;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_sthapanangal_detail_name = itemView.findViewById(R.id.txt_sthapanangal_detail_name);
                txt_sthapanangal_detail_location = itemView.findViewById(R.id.txt_sthapanangal_detail_location);
                txt_sthapanangal_detail_mobile = itemView.findViewById(R.id.txt_sthapanangal_detail_mobile);
                imageView11 = itemView.findViewById(R.id.imageView11);


            }
        }
    }
}
