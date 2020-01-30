package com.mind.simplelogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;


public class myEvent extends AppCompatActivity {

    TextView name, ort, zeit, datum, teilnehmer;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, eventid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myevent);

        name = findViewById(R.id.name);
        ort = findViewById(R.id.einort);
        zeit = findViewById(R.id.einzeit);
        datum = findViewById(R.id.eindatum);
        teilnehmer = findViewById(R.id.einteilnehmer);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        eventid = getIntent().getStringExtra("eventid_id");

        // DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("event").child(userId);




        final DocumentReference documentReference = fStore.collection("event").document(eventid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("Eventname"));
                zeit.setText(documentSnapshot.getString("Zeit"));
                ort.setText(documentSnapshot.getString("Ort"));
                datum.setText(documentSnapshot.getString("Datum"));
                teilnehmer.setText(documentSnapshot.getString("Teilnehmer"));




            }
        });

    }

}
