package com.techpakka.eerattupetta;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.techpakka.eerattupetta.Model.Data;

import java.util.ArrayList;
import java.util.Random;

public class Constants {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String stringtoint(int integer_value){
        return  String.valueOf(integer_value);
    }
    public Constants(){

    }

    public static void showLargeAd(Activity activity,String ad_url) {
        if (!ad_url.equals("noads")){
            int random = new Random().nextInt(3);
            if (random == 2){
                CustomDialogClass cd=new CustomDialogClass(activity,0,ad_url);
                cd.show();
            }
        }
    }

    public ArrayList<String> getBannerImages(){

        ArrayList<String> images = new ArrayList<>();
        db.collection("bottomads")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                           images.add(document.getString("image"));
                        }
                    } else {
                        Log.d("error", "Error getting documents.", task.getException());
                    }
                });
        return images;
    }
    public static RequestOptions getThumbnail(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(android.R.drawable.ic_menu_report_image);
        return requestOptions;
    }
}
