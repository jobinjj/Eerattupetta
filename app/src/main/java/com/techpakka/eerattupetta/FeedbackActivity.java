package com.techpakka.eerattupetta;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText edmobile;
    private EditText edmessage;
    private String name;
    private String message;
    private String mobile;
    private Button button;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        preferences = this.getSharedPreferences("erattupetta",MODE_PRIVATE);
        editor = preferences.edit();

        ed_name = findViewById(R.id.ed_name);
        edmobile = findViewById(R.id.ed_phone);
        edmessage = findViewById(R.id.ed_message);

        ed_name.setText(preferences.getString("username",""));
        edmobile.setText(preferences.getString("mobile",""));

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = ed_name.getText().toString();
                mobile = edmobile.getText().toString();
                message = edmessage.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("name", name);
                user.put("mobile", mobile);
                user.put("message", message);

                db.collection("Feedback")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(FeedbackActivity.this, "Thanks for the feedback", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("firestore", "Error adding document", e);
                            }
                        });

            }
        });
    }
}
