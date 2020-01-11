package com.mind.simplelogin;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class ProfilBearbeiten extends AppCompatActivity  {



    AlertDialog ortein;
    EditText ortaus;
    AlertDialog telein;
    EditText telaus;
    AlertDialog inteein;
    EditText inteaus;
    AlertDialog besein;
    EditText besaus;
    Button bestätigen;
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;
    TextView fullName,email,ort,beschreibung, telefonummer, interessen;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilbearbeiten);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        userId = fAuth.getCurrentUser().getUid();
        fullName = findViewById(R.id.tv_name);
        email    = findViewById(R.id.tvEmail);

        bestätigen = findViewById(R.id.btbestätigen);

        ort = findViewById(R.id.tv_address);
        ortein = new AlertDialog.Builder(this).create();
        ortaus = new EditText(this);
        ortein.setTitle(" Bearbeiten ");
        ortein.setView(ortaus);

        telefonummer = findViewById(R.id.tvTel);
        telein = new AlertDialog.Builder(this).create();
        telaus = new EditText(this);
        telein.setTitle(" Bearbeiten ");
        telein.setView(telaus);
        interessen = findViewById(R.id.tvInt);
        inteein = new AlertDialog.Builder(this).create();
        inteaus = new EditText(this);
        inteein.setTitle(" Bearbeiten ");
        inteein.setView(inteaus);
        beschreibung = findViewById(R.id.tvBesc);
        besein = new AlertDialog.Builder(this).create();
        besaus = new EditText(this);
        besein.setTitle(" Bearbeiten ");
        besein.setView(besaus);



        ortein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ort.setText(ortaus.getText());

            }
        });
        ort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ortaus.setText(ort.getText());
                ortein.show();
            }
        });

        telein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                telefonummer.setText(telaus.getText());
            }
        });
        telefonummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaus.setText(telefonummer.getText());
                telein.show();
            }
        });
        inteein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                interessen.setText(inteaus.getText());
            }
        });
        interessen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inteaus.setText(interessen.getText());
                inteein.show();
            }
        });
        besein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                beschreibung.setText(besaus.getText());
            }
        });
        beschreibung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                besaus.setText(beschreibung.getText());
                besein.show();
            }
        });

        bestätigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;



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
