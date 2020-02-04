package com.mind.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    Button share;

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
        eventid = getIntent().getStringExtra("event_id");
        share  = findViewById(R.id.btnShare);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("event").child(userId);




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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("Text/plain");
                String shareBody = "file:///C:/Users/AMD/Desktop/App/Anfrage.html";
                String shareSubject = "jtzdfuz";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                startActivity(Intent.createChooser(intent, "Share"));

            }
        });


    }

}
