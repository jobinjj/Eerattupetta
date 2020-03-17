package com.techpakka.eerattupetta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class AddSecondsActivity extends AppCompatActivity {
    private int PICK_IMAGE=1;
    private StorageReference mStorageRef;
    private Uri selectedImage;
    private Bitmap bitmap;
    private byte[] data2;
    Random random = new Random();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView imageView4;
    private String downloadurl;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private Button submit;
    private int id;
    private String selected;
    private int randomnum;
    private String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seconds);
        preferences = this.getSharedPreferences("erattupetta",MODE_PRIVATE);
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(6);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        output = sb.toString();
        getId();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        progressBar = findViewById(R.id.progressBar);
        EditText ed_price = findViewById(R.id.ed_price);
        EditText ed_mobile = findViewById(R.id.ed_mobile);
        EditText ed_description = findViewById(R.id.ed_description);
        ed_mobile.setText(preferences.getString("mobile","Enter mobile"));

        imageView4 = findViewById(R.id.imageView4);
        getSpinnerData();
        findViewById(R.id.btn_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(preferences.getString("username","null"),ed_mobile.getText().toString(),ed_description.getText().toString(),ed_price.getText().toString());
            }
        });

    }
    private void getSpinnerData() {
        Spinner spinner = findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.features_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getId() {
        DocumentReference docRef = db.collection("newsId").document("x3nXW3tiK7Gbx2xSvVxV");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    JSONObject jsonObj = new JSONObject(document.getData());
                    try {
                        id = Integer.parseInt(jsonObj.getString("id"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (document.exists()) {
                        Log.d("data", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("tag", "No such document");
                    }
                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });
    }

    private void sendData(String username, String mobile, String description, String price) {
        Toast.makeText(this, "Uploading....", Toast.LENGTH_LONG).show();
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("description", description);
        user.put("mobile", mobile);
        user.put("image", downloadurl);
        user.put("price", price);
        user.put("document", output);
        user.put("category", selected);
        user.put("id",String.valueOf(id+1));
        user.put("date",getDate());
        db.collection(selected).document(output)
                .set(user).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference ref = db.collection("newsId").document("ftC0wHiz1guGwYxieEWG");
                        // Set the "isCapital" field of the city 'DC'
                        ref
                                .update("id", String.valueOf(id + 1))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


//        db.collection(selected)
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(AddSecondsActivity.this, "succesfully posted", Toast.LENGTH_SHORT).show();
//
//                        DocumentReference ref = db.collection("newsId").document("x3nXW3tiK7Gbx2xSvVxV");
//
//                        // Set the "isCapital" field of the city 'DC'
//                        ref
//                                .update("id", String.valueOf(id + 1))
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                    }
//                                });
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("firestore", "Error adding document", e);
//                    }
//                });
    }

    private String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            submit = findViewById(R.id.btn_submit);
            submit.setText("please wait");
            selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            ImageView img_preview = findViewById(R.id.imageView4);
            img_preview.setImageBitmap(bitmap);
            data2 = baos.toByteArray();
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference newsRef = mStorageRef.child("Etpa/classifieds/"+selectedImage.getLastPathSegment());
            UploadTask uploadTask = newsRef.putBytes(data2);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("progress",String.valueOf(taskSnapshot.getBytesTransferred()));
                }
            });
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(AddSecondsActivity.this, "failed", Toast.LENGTH_SHORT).show();

                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                    newsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadurl = uri.toString();
                            progressBar.setVisibility(View.GONE);
                            findViewById(R.id.btn_submit).setEnabled(true);
                            submit.setText("Submit");
                        }
                    });
                }
            });


        }
    }
}
