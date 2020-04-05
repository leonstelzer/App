package com.mind.simplelogin.Profil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mind.simplelogin.Einstellungen;
import com.mind.simplelogin.R;
import com.mind.simplelogin.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    private ImageView bearbeiten;
    private ImageView back;
    ImageView user;
    TextView fullName,email, ort, beschreibung, telefonummer, interessen;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference= firebaseDatabase.getReference();
    private DatabaseReference firs = reference.child("Image");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bearbeiten      = findViewById(R.id.bearbeiten);
        fullName = findViewById(R.id.tv_name);
        email    = findViewById(R.id.tvEmail);
        ort    = findViewById(R.id.tv_address);
        beschreibung = findViewById(R.id.beschreibung);
        telefonummer = findViewById(R.id.telefonummer);
        interessen   = findViewById(R.id.interessen);
        user = findViewById(R.id.User);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();




        back            = findViewById(R.id.back);
        bearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Einstellungen.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, RegisterActivity.Startseite.class);
                startActivity(intent);
            }
        });






        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString("Benutername"));
                email.setText(documentSnapshot.getString("EMail"));
                ort.setText(documentSnapshot.getString("Ort"));
                telefonummer.setText(documentSnapshot.getString("Telefonnummer"));
                interessen.setText(lstToString((List)documentSnapshot.get("Interessen")));

                beschreibung.setText(documentSnapshot.getString("Beschreibung"));
                Picasso.get().load(documentSnapshot.getString("Image")).into(user);


            }
        });



    }
    private String lstToString(List<String> lst) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lst.size()-1; i++) {
            sb.append(lst.get(i));
            sb.append(", ");
        }
        if (lst.size()>0){
            sb.append(lst.get(lst.size()-1));}
        return sb.toString();
    }
}