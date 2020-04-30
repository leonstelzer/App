package com.mind.simplelogin.Profil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.Einstellungen;
import com.mind.simplelogin.Login.MainActivity;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Login.RegisterActivity;
import com.mind.simplelogin.Startseite;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.findevents.EventListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    private ImageView bearbeiten;
    private ImageView back;
    ImageView user;
    TextView fullName,email, ort, beschreibung, telefonummer, interessen,eevent,tevent;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference= firebaseDatabase.getReference();
    private DatabaseReference firs = reference.child("Image");
    private List<Event> eventList ;
    private List<Event> eventList2 ;
    private EventListAdapter eventListAdapter;


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
        eevent= findViewById(R.id.eevent);
        tevent = findViewById(R.id.tevent);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        fAuth=FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener fAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


            }
        };

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
                Intent intent = new Intent(Profile.this, Startseite.class);
                startActivity(intent);
            }
        });
        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString("Benutername"));
                //email.setText(documentSnapshot.getString("EMail"));
                ort.setText(documentSnapshot.getString("Ort"));
                //telefonummer.setText(documentSnapshot.getString("Telefonnummer"));
                interessen.setText(lstToString((List)documentSnapshot.get("Interessen")));

                //beschreibung.setText(documentSnapshot.getString("Beschreibung"));
                Picasso.get().load(documentSnapshot.getString("Image")).into(user);


            }
        });

        eventList = new ArrayList<>();
        eventListAdapter = new EventListAdapter(getApplicationContext(), eventList);
        eventList2 = new ArrayList<>();

        fStore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String eventid = doc.getDocument().getId();
                        final String usid = fAuth.getCurrentUser().getUid();
                        String event1 = doc.getDocument().toObject(Event.class).getId();
                        if(usid.equals(event1)) {
                            Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                            eventList.add(event);
                            eevent.setText(String.valueOf(eventList.size()));

                            eventListAdapter.notifyDataSetChanged();

                            //Toast.makeText(yourFriends.this, fAuth.getUid(), Toast.LENGTH_SHORT).show();





                        }
                    }

                }
            }
        });



        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                }
                else{
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        String id = doc.getDocument().getId();

                        if (id.equals(userId)) {
                            final String username = doc.getDocument().toObject(Users.class).getBenutername();

                            fStore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                    if (e != null) {

                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                            List<String> teilnehmern = new ArrayList<>();
                                            final String eventid = doc.getDocument().getId();

                                            teilnehmern = ((Event) doc.getDocument().toObject(Event.class)).getTeilnehmer();
                                            String id = doc.getDocument().getId();
                                            if (teilnehmern.contains(username)) {
                                                Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                                                eventList2.add(event);
                                                eventListAdapter.notifyDataSetChanged();
                                                tevent.setText(String.valueOf(eventList2.size()));


                                            }
                                        }
                                    }
                                }
                            });

                        }
                        }
                    }
                }}
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