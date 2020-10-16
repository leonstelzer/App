package com.mind.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.mind.simplelogin.Benachrichtigung.Beanchrichtigung;
import com.mind.simplelogin.Benachrichtigung.BenachrichtigungAdapter;
import com.mind.simplelogin.Login.MainActivity;
import com.mind.simplelogin.Profil.Profile;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.Userliste.findFriends;
import com.mind.simplelogin.events.neuerstellen.Kategorie.Interessen;

import java.util.ArrayList;
import java.util.List;

public class Startseite extends AppCompatActivity {


    private ImageView benachrichtigung,exit;
    private CardView numbercontainer;
    SpaceNavigationView navigationView;
    private TextView number;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private int benachCount;
    private Users myUser;

    private View  profil, freunde, erstellen, events;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        profil      = findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, Profile.class);
                startActivity(intent);
            }
        });

        erstellen = findViewById(R.id.erstellen);
        erstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, Interessen.class);
                startActivity(intent);

            }
        });

        freunde = findViewById(R.id.finden);
        freunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, findFriends.class);
                startActivity(intent);

            }
        });
        events = findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, com.mind.simplelogin.events.findevents.findevents.class);
                startActivity(intent);

            }
        });
        benachrichtigung = findViewById(R.id.benach);
        benachrichtigung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, Beanchrichtigung.class);
                startActivity(intent);
            }
        });
        exit = findViewById(R.id.exit);
        number = findViewById(R.id.numberbenach);
        numbercontainer =findViewById(R.id.numbercontainer);



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(Startseite.this, MainActivity.class));
                finish();
            }
        });
        final String usid = fAuth.getUid();

        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                if(queryDocumentSnapshots != null) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            final String user_id = doc.getDocument().getId();
                            if (fAuth.getUid().equals(user_id)) {
                                myUser = doc.getDocument().toObject(Users.class).withId(user_id);
                            }
                        }
                    }
                }

                benachrichtigungsCount();
            }

            private void benachrichtigungsCount() {
                fAuth = FirebaseAuth.getInstance();
                final String usid = fAuth.getUid();

                final DocumentReference counterDoc;
                if(usid != "" || usid != null) {
                    CollectionReference request;
                    try{
                       request =  fStore.collection("users").document(usid).collection("request");

                    }
                    catch (NullPointerException n){
                        request = null;
                    }
                    if(request!=null) {


                        request.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                int receivecount = 0;

                                if (queryDocumentSnapshots != null) {

                                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                            String type = (String) doc.getDocument().get("Type");
                                            final String otherID = (String) doc.getDocument().get("otherid");
                                            if (type.equals("received")) {
                                                receivecount++;
                                            }
                                        }
                                    }
                                }
                                myUser.setBenachrichtigungCount(receivecount);
                            }

                        });
                    }

                    else{
                        myUser.setBenachrichtigungCount(0);
                    }
                }

                CollectionReference request;
                try{
                    request =  fStore.collection("users").document(usid).collection("eventeinladung");

                }
                catch (NullPointerException n){
                    request = null;
                }
                if(request!=null) {


                    request.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            int receivecount = myUser.getBenachrichtigungCount();
                            if (queryDocumentSnapshots != null) {

                                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        receivecount++;
                                    }
                                }
                            }
                            myUser.setBenachrichtigungCount(receivecount);
                            fStore.collection("users").document(usid).update("BenachrichtigungsCount", receivecount);
                            if (receivecount == 0) {
                                number.setVisibility(View.INVISIBLE);
                                numbercontainer.setVisibility(View.INVISIBLE);
                            } else {
                                number.setText(String.valueOf(receivecount));
                            }

                        }
                    });
                }
            }
        });

    }

}







