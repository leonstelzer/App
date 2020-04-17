package com.mind.simplelogin.events.findevents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class otherEvent extends AppCompatActivity {

    TextView name, ort, zeit, datum, teilnehmer, kategorie, teilnehmer2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, eventid;

    Button teilnehmen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherevent);

        name = findViewById(R.id.name);
        ort = findViewById(R.id.einort);
        zeit = findViewById(R.id.einzeit);
        datum = findViewById(R.id.eindatum);
        teilnehmer = findViewById(R.id.einteilnehmer);
        kategorie = findViewById(R.id.einKategorie);
        teilnehmen = findViewById(R.id.teilnehmen);

        eventid = getIntent().getStringExtra("eventid");
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fStore.collection("event").document(eventid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("Eventname"));
                zeit.setText(documentSnapshot.getString("Zeit"));
                ort.setText(documentSnapshot.getString("Ort"));
                datum.setText(documentSnapshot.getString("Datum"));
                teilnehmer.setText(documentSnapshot.getString("Teilnehmer"));
                kategorie.setText(documentSnapshot.getString("Kategorie"));






            }
        });








    }

}
