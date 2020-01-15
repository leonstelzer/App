package com.mind.simplelogin;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfilBearbeiten extends AppCompatActivity  {




    Button bestätigen;
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;
    TextView fullName,email;
    EditText ort,beschreibung, telefonummer, interessen;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilbearbeiten);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        userId = fAuth.getCurrentUser().getUid();
        fullName = findViewById(R.id.tv_name);
        email    = findViewById(R.id.tvEmail);
        bestätigen = findViewById(R.id.btbestätigen);
        ort = findViewById(R.id.tv_address);
        telefonummer = findViewById(R.id.tvTel);
        interessen = findViewById(R.id.tvInt);
        beschreibung = findViewById(R.id.tvBesc);



        bestätigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eemail = email.getText().toString().trim();
                final String efullname = fullName.getText().toString();
                final String eort = ort.getText().toString();
                final String einteresssen = interessen.getText().toString();
                final String ebeschreibung = beschreibung.getText().toString();
                final String etelefonnummer = telefonummer.getText().toString();

                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("Benutername", efullname);
                user.put("EMail", eemail);
                user.put("Ort", eort);
                user.put("Interessen", einteresssen);
                user.put("Beschreibung", ebeschreibung);
                user.put("Telefonnummer", etelefonnummer);
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

}
