package com.mind.simplelogin.Benachrichtigung;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Beanchrichtigung extends AppCompatActivity {

    private RecyclerView friendlist;
    private FirebaseFirestore mFirestore;
    private List<Object> usersList ;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private BenachrichtigungAdapter benachrichtigungAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benachrichtigung);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        friendlist = findViewById(R.id.friendlist);
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        benachrichtigungAdapter = new BenachrichtigungAdapter(getApplicationContext(), usersList);
        final String usid = fAuth.getCurrentUser().getUid();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Benachrichtigungen");


        friendlist.setHasFixedSize(true);
        friendlist.setLayoutManager(new LinearLayoutManager((this)));
        friendlist.setAdapter(benachrichtigungAdapter);




        mFirestore.collection("users").document(usid).collection("request").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                                                     @Override
                                                                                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                                                                        System.out.println("Start ON Event");
                                                                                                        if (e != null) {
                                                                                                        }
                                                                                                        System.out.println("MAP GROEESSE : " + queryDocumentSnapshots.getDocumentChanges().size());
                                                                                                         for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                                                                                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                                                                                                String type = (String) doc.getDocument().get("Type");
                                                                                                                String otherid = (String) doc.getDocument().get("otherid");

                                                                                                                System.out.println("type:"+type);
                                                                                                                System.out.println("otherid:"+ otherid);

                                                                                                                final String otherID = (String) doc.getDocument().get("otherid");
                                                                                                                if (type.equals("received")) {
                                                                                                                    mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                                                                        @Override
                                                                                                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                                                                                            if (e != null) {

                                                                                                                            }
                                                                                                                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                                                                                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                                                                                                    String id = doc.getDocument().getId();
                                                                                                                                    if (otherID.equals(id)) {
                                                                                                                                        Users users = doc.getDocument().toObject(Users.class).withId(otherID);
                                                                                                                                        usersList.add(users);
                                                                                                                                        benachrichtigungAdapter.notifyDataSetChanged();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                });

        mFirestore.collection("users").document(usid).collection("eventeinladung").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                System.out.println("Start ON Event");
                if (e != null) {
                }
                System.out.println("MAP GROEESSE : " + queryDocumentSnapshots.getDocumentChanges().size());
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        //String type = (String) doc.getDocument().get("Type");
                        final String eventid = (String) doc.getDocument().get("eventid");

                        //System.out.println("type:"+type);
                        System.out.println("eventid:"+ eventid);

                        //final String otherID = (String) doc.getDocument().get("otherid");
                            mFirestore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {

                                    }
                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                            String id = doc.getDocument().getId();
                                            if (eventid.equals(id)) {
                                                Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                                                usersList.add(event);
                                                benachrichtigungAdapter.notifyDataSetChanged();
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
