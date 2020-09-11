package com.mind.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.Freundesliste.yourFriends;
import com.mind.simplelogin.Login.MainActivity;
import com.mind.simplelogin.Login.RegisterActivity;
import com.mind.simplelogin.Profil.ProfilBearbeiten;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.Userliste.UsersListAdapter;
import com.mind.simplelogin.events.neuerstellen.Kategorie.InteressenProfil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class Einstellungen extends AppCompatActivity {

    private TextView profilbearbeiten,interessenAendern,freundezahl;
    private FirebaseFirestore mFirestore;
    private List<Users> usersList ;
    private UsersListAdapter usersListAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    LinearLayout allfriends;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellungen);
        profilbearbeiten      = findViewById(R.id.profilbearbeiten);
        interessenAendern = findViewById(R.id.interessenWaehlen);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        freundezahl = findViewById(R.id.freundezahl);
        allfriends = findViewById(R.id.allfriends);

        allfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Einstellungen.this, yourFriends.class);
                startActivity(intent);
            }
        });


        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        usersListAdapter = new UsersListAdapter(getApplicationContext(), usersList);
        String usid = fAuth.getCurrentUser().getUid();


        profilbearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Einstellungen.this, ProfilBearbeiten.class);
                startActivity(intent);
            }
        });

        interessenAendern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Einstellungen.this, InteressenProfil.class);
                startActivity(intent);
            }
        });


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
                                            usersList.add(users);
                                            usersListAdapter.notifyDataSetChanged();

                                            freundezahl.setText(String.valueOf(usersList.size()));

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
