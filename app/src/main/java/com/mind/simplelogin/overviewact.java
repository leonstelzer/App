package com.mind.simplelogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.Profil.ProfilBearbeiten;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class overviewact extends AppCompatActivity implements View.OnClickListener {

    private ImageButton fussball;
    private Button weiter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private ImageButton badminton;
    private ImageButton running;
    private ImageButton pokern;
    private ImageButton fifa;
    private ImageButton kino;
    private ImageButton bar;
    private ImageButton food;
    boolean clicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testoverview);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        weiter = (Button) findViewById(R.id.btweiter);
        fussball = (ImageButton) findViewById(R.id.btfussball);
        running = (ImageButton) findViewById(R.id.btrunning);
        pokern = (ImageButton) findViewById(R.id.btpoker);
        badminton = (ImageButton) findViewById(R.id.btbadminton);
        fifa = (ImageButton) findViewById(R.id.btfifa);
        kino = (ImageButton) findViewById(R.id.btkino);
        bar = (ImageButton) findViewById(R.id.btbar);
        food = (ImageButton) findViewById(R.id.btfood);


        running.setOnClickListener(this);
        badminton.setOnClickListener(this);
        fussball.setOnClickListener(this);
        pokern.setOnClickListener(this);
        fifa.setOnClickListener(this);
        kino.setOnClickListener(this);
        bar.setOnClickListener(this);
        food.setOnClickListener(this);
        String delim = " ,";

        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference doc = fStore.collection("users").document(userId);
                doc.update("Interessen", interessen);

                Intent intent = new Intent(overviewact.this, ProfilBearbeiten.class);
                startActivity(intent);

            }
        });
    }
    List<String>interessen=new ArrayList<>();





    @Override
    public void onClick(View view) {
        clicked = !clicked;
        switch (view.getId()) {
            case R.id.btrunning:
            if(clicked) {
                running.setBackgroundResource(R.color.colorBlue);
                interessen.add("joggen");
            }
            else {
                running.setBackgroundResource(R.color.colorWhite);
                interessen.remove("joggen");

            }
            break;
            case R.id.btbadminton:
                if(clicked) {
                    badminton.setBackgroundResource(R.color.colorBlue);
                    interessen.add("Badminton");
                }
                else {
                    badminton.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("Badminton");

                }
                break;
            case R.id.btfussball:
                if(clicked) {
                    fussball.setBackgroundResource(R.color.colorBlue);
                    interessen.add("Fußball");

                }
                else {
                    fussball.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("Fußball");

                }
                break;
            case R.id.btpoker:
                if(clicked) {
                    pokern.setBackgroundResource(R.color.colorBlue);
                    interessen.add("Pokern");

                }
                else {
                    pokern.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("Pokern");

                }
                break;
            case R.id.btfifa:
                if(clicked) {
                    fifa.setBackgroundResource(R.color.colorBlue);

                    interessen.add("Fifa");

                }
                else {
                    fifa.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("Fifa");

                }
                break;
            case R.id.btkino:
                if(clicked) {
                    kino.setBackgroundResource(R.color.colorBlue);
                    interessen.add("Kino");

                }
                else {
                    kino.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("Kino");

                }
                break;
            case R.id.btbar:
                if(clicked) {
                    bar.setBackgroundResource(R.color.colorBlue);
                    interessen.add("trinken");

                }
                else {
                    bar.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("trinken");

                }
                break;
            case R.id.btfood:
                if(clicked) {
                    food.setBackgroundResource(R.color.colorBlue);
                    interessen.add("Essen");

                }
                else {
                    food.setBackgroundResource(R.color.colorWhite);
                    interessen.remove("Essen");

                }
                break;



        }
        System.out.println(interessen);

    }


};