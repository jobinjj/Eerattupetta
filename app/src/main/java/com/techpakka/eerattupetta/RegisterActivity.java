package com.techpakka.eerattupetta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText ed_username;
    private ImageView btn_proceed;
    private EditText ed_mobile;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferences = this.getSharedPreferences("erattupetta",MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.getBoolean("regisered",false)){
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

        ed_username = findViewById(R.id.ed_username);
        ed_mobile = findViewById(R.id.ed_mobile);
        btn_proceed = findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser(ed_username,ed_mobile);

            }
        });
        ed_username.requestFocus();
    }

    private void RegisterUser(EditText ed_username, EditText editText) {
        Context context;
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Registering");
        progressDialog.show();
        editor.putString("username",ed_username.getText().toString());
        editor.putString("mobile",ed_mobile.getText().toString());
        editor.apply();
        Map<String, Object> user = new HashMap<>();
        user.put("username", ed_username.getText().toString());
        user.put("mobile", ed_mobile.getText().toString());
        db.collection("EratupettaRegister")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "succesfully regisered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                        editor.putBoolean("regisered",true).apply();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("firestore", "Error adding document", e);
                    }
                });
    }
}
