package com.mind.simplelogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
                //ColorDrawable buttonColor = (ColorDrawable) button.getColorFilter();
                //int colorId = buttonColor.getColor();
                //if(colorId == Color.BLACK){
                //// hier immer einzeln prüfen, ob Farbe auf Schwarz steht.
            if(clicked){
             //   running.getColorFilter().equals(new ColorFilter(buttonColor.BLACK, PorterDuff.Mode.SRC_ATOP))) {
                interessen.add("joggen");
                running.setColorFilter(ContextCompat.getColor(this,R.color.address));
                ;;
            }
            else {
                interessen.remove("joggen");
                running.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            }
            break;
            case R.id.btbadminton:
                if(clicked) {
                    interessen.add("Badminton");
                    badminton.setColorFilter(ContextCompat.getColor(this,R.color.address));
                }
                else {
                    interessen.remove("Badminton");
                    badminton.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;
            case R.id.btfussball:
                if(clicked) {
                    interessen.add("Fußball");
                    fussball.setColorFilter(ContextCompat.getColor(this,R.color.address));

                }
                else {
                    interessen.remove("Fußball");
                    fussball.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;
            case R.id.btpoker:
                if(clicked) {
                    interessen.add("Pokern");
                    pokern.setColorFilter(ContextCompat.getColor(this,R.color.address));

                }
                else {
                    interessen.remove("Pokern");
                    pokern.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;
            case R.id.btfifa:
                if(clicked) {
                    interessen.add("Fifa");
                    fifa.setColorFilter(ContextCompat.getColor(this,R.color.address));

                }
                else {
                    interessen.remove("Fifa");
                    fifa.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;
            case R.id.btkino:
                if(clicked) {
                    interessen.add("Kino");
                    kino.setColorFilter(ContextCompat.getColor(this,R.color.address));

                }
                else {
                    interessen.remove("Kino");
                    kino.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;
            case R.id.btbar:
                if(clicked) {
                    interessen.add("trinken");
                    bar.setColorFilter(ContextCompat.getColor(this,R.color.address));

                }
                else {
                    interessen.remove("trinken");
                    bar.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;
            case R.id.btfood:
                if(clicked) {
                    interessen.add("Essen");
                    food.setColorFilter(ContextCompat.getColor(this,R.color.address));

                }
                else {
                    interessen.remove("Essen");
                    food.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

                }
                break;



        }
        System.out.println(interessen);

    }


};