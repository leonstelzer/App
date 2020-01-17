package com.mind.simplelogin;

import android.app.Notification;
import android.app.Person;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import io.grpc.Compressor;

public class ProfilBearbeiten extends AppCompatActivity  {




    Button bestätigen;
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;
    TextView fullName,email;
    EditText ort,beschreibung, telefonummer, interessen;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;



    String userId;
    ImageView user;
    Uri imageurl;
    StorageReference mStorageRef;
    Button upload;
    private StorageTask uploadtask;
    private Bitmap compressor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilbearbeiten);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference().child("Images");
        DatabaseReference reference = database.getReference();

        userId = fAuth.getCurrentUser().getUid();
        fullName = findViewById(R.id.tv_name);
        email    = findViewById(R.id.tvEmail);
        bestätigen = findViewById(R.id.btbestätigen);
        ort = findViewById(R.id.tv_address);
        telefonummer = findViewById(R.id.tvTel);
        interessen = findViewById(R.id.tvInt);
        beschreibung = findViewById(R.id.tvBesc);
        user = findViewById(R.id.User);
        upload = findViewById(R.id.upload);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadtask != null && uploadtask.isInProgress()){
                    Toast.makeText(ProfilBearbeiten.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else{
                    fileuploader();

                }

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        filechooser();

                                    }
                                });


                bestätigen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // get Information from Edit Text or fileuploader()
                        final String eemail = email.getText().toString().trim();
                        final String efullname = fullName.getText().toString();
                        final String eort = ort.getText().toString();
                        final String einteresssen = interessen.getText().toString();
                        final String ebeschreibung = beschreibung.getText().toString();
                        final String etelefonnummer = telefonummer.getText().toString();
                        final String image = imageurl != null ? imageurl.toString() : null;


                        userId = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userId);

                        //save edited Information in Database

                            Map<String, Object> user = new HashMap<>();
                            user.put("Benutername", efullname);
                            user.put("EMail", eemail);
                            user.put("Ort", eort);
                            user.put("Interessen", einteresssen);
                            user.put("Beschreibung", ebeschreibung);
                            user.put("Telefonnummer", etelefonnummer);
                            user.put("Image", image);
                            documentReference.set(user);








                        Intent intent = new Intent(ProfilBearbeiten.this, Profile.class);
                        startActivity(intent);
                    }
                });
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                fullName.setText(documentSnapshot.getString("Benutername"));
                email.setText(documentSnapshot.getString("EMail"));
                ort.setText(documentSnapshot.getString("Ort"));
                telefonummer.setText(documentSnapshot.getString("Telefonnummer"));
                interessen.setText(documentSnapshot.getString("Interessen"));
                beschreibung.setText(documentSnapshot.getString("Beschreibung"));


            }
        });





    }
// uploads and open gallery on device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1 && resultCode==RESULT_OK && data != null && data.getData() != null){
            imageurl=data.getData();
            user.setImageURI(imageurl);

        }
    }

    private void filechooser(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);


    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void fileuploader () {
            StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+","+getExtension(imageurl));
        uploadtask = Ref.putFile(imageurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                        Toast.makeText(ProfilBearbeiten.this, "Image Uploaded", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }


}
