package com.techpakka.eerattupetta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
//TODO create bottom ads
    private ViewPager viewPager;
    Timer timer;
    int currentPage = 0;
    ArrayList<Data> list = new ArrayList<>();
    LinearLayout dotsLayout;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final long Delay_ms = 1000;
    final long PERIOD_MS = 4000;
    MyViewPagerAdapter myViewPagerAdapter;
    private RecyclerView recyclerView;
    HomeAdapter adapter;
    private RecyclerView newsrecyclerView;
    private HomeActivity.newsAdapter newsAdapter;
    private ArrayList<Data> list1;
    private ProgressBar progressBar3;
    private ArrayList<Data> adlist;
    private boolean hasads = false;
    private int[] colorsInactive;
    private int[] colorsActive;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dots = new TextView[7];
        System.out.print(dots.length);
        NestedScrollView scrollView = findViewById(R.id.scroll);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        getAdImages();
        adapter  = new HomeAdapter(getData(),this,adlist);
        progressBar3 = findViewById(R.id.progressBar3);
        newsAdapter = new newsAdapter(getNewsData());
        recyclerView = findViewById(R.id.recyclerView);
        newsrecyclerView = findViewById(R.id.newsrecyclerView);
        newsrecyclerView.setHasFixedSize(false);
        newsrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration1 = new ItemOffsetDecoration(HomeActivity.this, R.dimen.item_offset);
        newsrecyclerView.addItemDecoration(itemDecoration1);
        newsrecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsrecyclerView.setNestedScrollingEnabled(false);
        newsrecyclerView.setAdapter(newsAdapter);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(HomeActivity.this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        viewPager = findViewById(R.id.view_pager);

        dotsLayout = findViewById(R.id.layoutDots);
       //addBottomDots(0);
        myViewPagerAdapter = new MyViewPagerAdapter();

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == 1) {
                currentPage = 2;
            } else if (currentPage == 0) {
                currentPage = 1;
            } else if (currentPage == 2) {
                currentPage = 3;
            } else if (currentPage == 3) {
                currentPage = 4;
            }else if (currentPage == 4) {
                currentPage = 5;
            }else if (currentPage == 5) {
                currentPage = 6;
            }else if (currentPage == 6) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, Delay_ms, PERIOD_MS);
        viewPager.setAdapter(myViewPagerAdapter); //viewpager
    }

    private void getAdImages() {
        adlist = new ArrayList<>();
        db.collection("ad")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        hasads = true;
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data = new Data();
                            data.setAd_url(document.getString("image"));
                            data.setLarge_adurl(document.getString("imagelarge"));
                            adlist.add(data);
                            myViewPagerAdapter.notifyDataSetChanged();
                        }

                        adapter  = new HomeAdapter(getData(),this,adlist);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d("data", "Error getting documents.", task.getException());
                    }
                });

    }

    private List<Data> getNewsData() {
        list1 = new ArrayList<>();
        db.collection("Featurenews").orderBy("date", Query.Direction.ASCENDING)
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
                        progressBar3.setVisibility(View.GONE);
                        for (DocumentSnapshot document : task.getResult()) {
                            Data data2 = new Data();
                            data2.setFeature_news_title(document.getString("title"));
                            data2.setFeature_description(document.getString("description"));
                            data2.setFeature_news_image(document.getString("image"));
                            data2.setFeature_news_date(document.getString("date"));
                            list1.add(data2);
                        }
                        newsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                });

        return list1;
    }

    private List<Data> getData() {
        list.clear();
        Data data = new Data(R.drawable.ic_journalist,"വാർത്ത");
        list.add(data);
        data = new Data(R.drawable.ic_shopping_bag,"ക്ലാസിഫൈഡ്സ്");
        list.add(data);
        data = new Data(R.drawable.ic_employee,"ജോലികൾ");
        list.add(data);
        data = new Data(R.drawable.ic_feather,"ഫീച്ചർസ്");
        list.add(data);
        data = new Data(R.drawable.funny,"ട്രോൾ");
        list.add(data);
        data = new Data(R.drawable.ic_promotion,"അറിയിപ്പുകൾ");
        list.add(data);
        data = new Data(R.drawable.ic_bus2,"ബസ് സമയം");
        list.add(data);
        data = new Data(R.drawable.ic_alarm,"എമർജൻസി");
        list.add(data);
        data = new Data(R.drawable.ic_video_camera,"വീഡിയോസ്");
        list.add(data);
        data = new Data(R.drawable.ic_rickshaw,"ഓട്ടോ");
        list.add(data);
        data = new Data(R.drawable.ic_truck,"വാഹനങ്ങൾ");
        list.add(data);
        data = new Data(R.drawable.ic_cart,"ഷോപ്പുകൾ");
        list.add(data);
        data = new Data(R.drawable.ic_capitol,"സ്ഥാപനങ്ങൾ");
        list.add(data);
        data = new Data(R.drawable.ic_charity,"സേവനങ്ങൾ");
        list.add(data);
        data = new Data(R.drawable.ic_engineer,"തൊഴിലാളികൾ");
        list.add(data);
        data = new Data(R.drawable.ic_heartbeat,"ബ്ലഡ് ബാങ്ക്");
        list.add(data);
        data = new Data(R.drawable.ic_ambulance,"ആശുപത്രി");
        list.add(data);
        data = new Data(R.drawable.ic_politician,"ജനപ്രതിനിധികൾ");
        list.add(data);
        data = new Data(R.drawable.ic_male_reporter,"മീഡിയ");
        list.add(data);
        data = new Data(R.drawable.ic_relax,"ടൂറിസം");
        list.add(data);
        data = new Data(R.drawable.ic_enterprise,"മറ്റുള്ളവ");
        list.add(data);
        data = new Data(R.drawable.ic_resume,"ഫീഡ്ബാക്ക്");
        list.add(data);
        data = new Data(R.drawable.ic_parents,"മാരേജ് ബ്യൂറോ");
        list.add(data);
        data = new Data(R.drawable.ic_businessman,"ഞങ്ങൾ");
        list.add(data);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.techpakka.whatsappstickerspack")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.techpakka.whatsappstickerspack")));
                }
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: " );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        TextView title;
        ImageView img_banner;
        private Data data;

        MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            String[] url = {"www.facebook.com", "www.youtube.com", "www.github.com"};

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater != null ? layoutInflater.inflate(R.layout.slide1, container, false) : null;
            img_banner = view.findViewById(R.id.ad);

            if (hasads){

                data = adlist.get(position);
                Glide.with(getApplicationContext()).load(data.getAd_url())
                        .thumbnail(0.5f)
                        .into(img_banner);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialogClass cd=new CustomDialogClass(HomeActivity.this,position,data.getLarge_adurl());
                    cd.show();
                }
            });
            container.addView(view);

            return view;
        }

        private int getLayout(int position) {
            int[] layout = {R.layout.slide1,R.layout.slide2,R.layout.slide3};
            return layout[position];
        }

        @Override
        public int getCount() {

            return adlist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    private void addBottomDots(int currentPage) {


        colorsActive = getResources().getIntArray(R.array.array_dot_active);
        colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 1)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == 2 - 1) {
                // last page. make button text to GOT IT

            } else {
                // still pages are left
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }; //menu
    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }

    public class newsAdapter extends RecyclerView.Adapter<newsAdapter.MyViewHolder>{

        private List<Data> list;
        public newsAdapter(List<Data> list){
            this.list = list;
        }

        @NonNull
        @Override
        public newsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.content_feature_news, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull newsAdapter.MyViewHolder myViewHolder, int i) {
            Data data = list.get(i);
            myViewHolder.txt_title.setText(data.getFeature_news_title());
            myViewHolder.txt_date.setText(data.getFeature_news_date());
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,FeautureNewsDetail.class);
                    intent.putExtra("title",data.getFeature_news_title());
                    intent.putExtra("description",data.getFeature_description());
                    intent.putExtra("image",data.getFeature_news_image());
                    intent.putExtra("date",data.getFeature_news_date());
                    startActivity(intent);
                }
            });
            Glide.with(getApplicationContext()).load(data.getFeature_news_image()).into(myViewHolder.imageView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView txt_title,txt_date;
            private CardView container;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                txt_title = itemView.findViewById(R.id.textView2);
                txt_date = itemView.findViewById(R.id.textView3);
                container = itemView.findViewById(R.id.container);

            }
        }
    }
}
