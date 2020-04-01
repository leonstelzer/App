package com.mind.simplelogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Beanchrichtigung extends AppCompatActivity {

    private RecyclerView friendlist;
    private FirebaseFirestore mFirestore;
    private List<Users> usersList ;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private BenachrichtigungAdapter BenachrichtigungAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benachrichtigung);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        friendlist = findViewById(R.id.friendlist);
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        BenachrichtigungAdapter = new BenachrichtigungAdapter(getApplicationContext(), usersList);
        String usid = fAuth.getCurrentUser().getUid();


        friendlist.setHasFixedSize(true);
        friendlist.setLayoutManager(new LinearLayoutManager((this)));
        friendlist.setAdapter(BenachrichtigungAdapter);


        mFirestore.collection("users").document(usid).collection("request").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                            usersList.add(users);
                                            (BenachrichtigungAdapter).notifyDataSetChanged();
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
