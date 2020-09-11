package com.mind.simplelogin.Profil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mind.simplelogin.events.neuerstellen.Kategorie.InteressenProfilItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    private ImageView bearbeiten;
    private ImageView back;
    ImageView user;
    TextView fullName,email, ort, beschreibung, telefonummer, interessen,eevent,tevent,gestartet, teilgenommen;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference= firebaseDatabase.getReference();
    private DatabaseReference firs = reference.child("Image");
    private List<Event> eventList ;
    private List<Event> eventList2 ;
    private EventListAdapter eventListAdapter;
    int [] array;

    private RecyclerView RecyclerProfile;
    ProfilAdapter RecyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
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
        gestartet = findViewById(R.id.start);
        teilgenommen = findViewById(R.id.teil);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                List<String> interessen = (List)documentSnapshot.get("Interessen");
                ArrayList<Integer> bilder = new ArrayList<>();
                if(
                    interessen == null
                ){
                    System.out.println("interessen = null");
                    array = new int[0];
                }
                else
                {

                for(String as: interessen)
                {
                    switch (as) {
                        case "Fussball":bilder.add(R.drawable.fussballneu);
                        break;
                        case"Pokern":bilder.add(R.drawable.ic_pokernsvg);
                            break;
                        case"Badminton":bilder.add(R.drawable.badminton);
                            break;
                            case"Bar besuch":bilder.add(R.drawable.ic_barsvg);
                            break;
                            case"Essen":bilder.add(R.drawable.essenneu);
                            break;
                            case"Joggen":bilder.add(R.drawable.laufenneu);
                            break;
                            case"Kino":bilder.add(R.drawable.ic_kinosvg);
                            break;
                            case"Tischtennis":bilder.add(R.drawable.tischtennis);
                            break;
                            case"Zocken":bilder.add(R.drawable.ic_playstation_4);
                            break;
                            case"Darts":bilder.add(R.drawable.darts);
                            break;
                            case"Schwimmbad":bilder.add(R.drawable.schwimmbad);
                            break;
                            case"Handball":bilder.add(R.drawable.handball);
                            break;
                            case"Spazieren":bilder.add(R.drawable.laufen);
                            break;
                            case"Fitnessstudio":bilder.add(R.drawable.fitness);
                            break;
                            case"Konzert":bilder.add(R.drawable.konzert);
                            break;
                            case"Billiard":bilder.add(R.drawable.pool);
                            break;
                            case"Shoppen":bilder.add(R.drawable.shoppen);
                            break;
                            case"Volleyball":bilder.add(R.drawable.volleyball);
                            break;
                            case"Zoo/Tierpark":bilder.add(R.drawable.zoo);break;
                            case"Casino":bilder.add(R.drawable.casino);
                            break;case"Sonstiges":bilder.add(R.drawable.fragezeichen);
                    }
                }

                array = new int[bilder.size()];
                for (int i = 0;
                     i < bilder.size();
                     i++)
                {
            array [i] = bilder.get(i);
                }

                    RecyclerProfile = findViewById(R.id.RecyclerProfile);
                    RecyclerProfile.setLayoutManager(layoutManager);
                    RecyclerViewAdapter = new ProfilAdapter(array);
                    RecyclerProfile.setAdapter(RecyclerViewAdapter);
                    RecyclerProfile.setHasFixedSize(true);

            }}
        });

        layoutManager = new GridLayoutManager(this,2);


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
        final DocumentReference documentReference2 = fStore.collection("users").document(userId);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString("Benutername"));
                ort.setText(documentSnapshot.getString("Ort"));
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

                            eventListAdapter.notifyDataSetChanged();
                            gestartet.setText(String.valueOf(eventList.size()));

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
                                                teilgenommen.setText(String.valueOf(eventList2.size()));
                                                //tevent.setText(String.valueOf(eventList2.size()));


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