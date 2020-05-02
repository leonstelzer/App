package com.mind.simplelogin.events.Freundeeinladen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.neuerstellen.Kategorie.allevent;
import com.mind.simplelogin.events.neuerstellen.Kategorie.meinevent;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class EventEinladen extends AppCompatActivity {

    private RecyclerView friendlist;
    private FirebaseFirestore mFirestore;
    private List<Users> usersList ;
    private List<Event> eventList;
    private EinladenListAdapter einladenListAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button bestätigen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventeinladen);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        final String eventid = getIntent().getStringExtra("eventid");
        final String herkunft = getIntent().getStringExtra("herkunft");
        final String kategorie = getIntent().getStringExtra("kategorie");


        friendlist = findViewById(R.id.friendlist);
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        eventList = new ArrayList<>();
        einladenListAdapter = new EinladenListAdapter(getApplicationContext(), usersList, eventList);
        String usid = fAuth.getCurrentUser().getUid();


        friendlist.setHasFixedSize(true);
        friendlist.setLayoutManager(new LinearLayoutManager((this)));
        friendlist.setAdapter(einladenListAdapter);
        bestätigen = findViewById(R.id.bestätigen);

        if(herkunft.equals("1")) {
            bestätigen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventEinladen.this, meinevent.class);
                    Toast.makeText(EventEinladen.this, "Einladungen verschickt", Toast.LENGTH_SHORT).show();
                    intent.putExtra("eventid", eventid);
                    intent.putExtra("kategorie", kategorie);

                    startActivity(intent);
                }
            });
        }
        if(herkunft.equals("2")) {
            bestätigen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventEinladen.this, allevent.class);
                    Toast.makeText(EventEinladen.this, "Einladungen verschickt", Toast.LENGTH_SHORT).show();
                    intent.putExtra("eventid", eventid);
                    startActivity(intent);
                }
            });
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Meine Freunde");

        mFirestore.collection("users").document(usid).collection("friends").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String user_id = doc.getDocument().getId();
                        //Toast.makeText(yourFriends.this, fAuth.getUid(), Toast.LENGTH_SHORT).show();


                        mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                if (e != null) {

                                }
                                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED){

                                        String id = doc.getDocument().getId();
                                        if(user_id.equals(id)) {
                                            Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                                            Event event = doc.getDocument().toObject(Event.class).withId(eventid);

                                            usersList.add(users);
                                            eventList.add(event);

                                            einladenListAdapter.notifyDataSetChanged();

                                        }
                                    }
                                }
                            }
                        });

                    }
                }
            }
        });
    }


}