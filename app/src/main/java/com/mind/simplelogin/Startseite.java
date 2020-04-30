package com.mind.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.mind.simplelogin.Benachrichtigung.Beanchrichtigung;
import com.mind.simplelogin.Benachrichtigung.BenachrichtigungAdapter;
import com.mind.simplelogin.Login.MainActivity;
import com.mind.simplelogin.Profil.Profile;
import com.mind.simplelogin.Userliste.findFriends;
import com.mind.simplelogin.events.neuerstellen.Kategorie.Interessen;

import java.util.ArrayList;
import java.util.List;

public class Startseite extends AppCompatActivity{
    private LinearLayout profil;
    private LinearLayout findevents;
    private LinearLayout erstellen;
    private LinearLayout friends;
    private ImageView benachrichtigung,exit;
    private CardView numbercontainer;
    SpaceNavigationView navigationView;
    private TextView number;
    private FirebaseFirestore mFirestore;
    FirebaseAuth fAuth;
    private List<Object> usersList ;
    private BenachrichtigungAdapter benachrichtigungAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        fAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


        final String usid = fAuth.getCurrentUser().getUid();


        final int[] receivecount = {0};

        usersList = new ArrayList<>();
        benachrichtigungAdapter = new BenachrichtigungAdapter(getApplicationContext(), usersList);


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

        friends = findViewById(R.id.friends);
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, findFriends.class);
                startActivity(intent);

            }
        });
        findevents = findViewById(R.id.findevevents);
        findevents.setOnClickListener(new View.OnClickListener() {
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

        fAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(Startseite.this, MainActivity.class));
                finish();
            }
        });


    }

}







