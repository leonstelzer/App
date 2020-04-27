package com.mind.simplelogin.events.findevents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
                teilnehmer.setText(lstToString((List)documentSnapshot.get("Teilnehmer")));
                kategorie.setText(documentSnapshot.getString("Kategorie"));
               }
        });
        final String currenstate;


          fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(final DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                        String id = doc.getDocument().getId();

                        if(id.equals(userId)) {
                            final String username = doc.getDocument().toObject(Users.class).getBenutername();
                            System.out.println(username);

                            DocumentReference documentReference = fStore.collection("event").document(eventid);

                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    List<String>teilnehmern=new ArrayList<>();

                                    Event event = (Event) documentSnapshot.toObject(Event.class);
                                    teilnehmern = event.getTeilnehmer();

                                    if (teilnehmern.contains(username)) {


                                        teilnehmen.setEnabled(true);
                                        teilnehmen.setText("Teilnahme zurückziehen");


                                    } else {
                                        teilnehmen.setEnabled(true);
                                        teilnehmen.setText("Teilnehmen");


                                    }

                                }
                            });

                        }
                    }
                }
            });

        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                    String id = doc.getDocument().getId();


                    if(id.equals(userId)) {
                        final String username = doc.getDocument().toObject(Users.class).getBenutername();
                        teilnehmen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final DocumentReference documentReference = fStore.collection("event").document(eventid);

                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        List<String>teilnehmern=new ArrayList<>();

                                        Event event = (Event) documentSnapshot.toObject(Event.class);
                                        teilnehmern = event.getTeilnehmer();

                                        if (teilnehmern.contains(username)) {
                                              teilnehmern.remove(username);
                                              documentReference.update("Teilnehmer", teilnehmern);


                                            teilnehmen.setEnabled(true);
                                            teilnehmen.setText("Teilnehmen");


                                        } else {

                                            teilnehmern.add(username);
                                              documentReference.update("Teilnehmer", teilnehmern);

                                            teilnehmen.setEnabled(true);
                                            teilnehmen.setText("Teilnahme zurückziehen");


                                        }

                                    }
                                });

                            }
                        });



                    }
                }
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
