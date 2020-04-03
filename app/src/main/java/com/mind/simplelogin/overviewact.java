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

public class overviewact extends AppCompatActivity implements View.OnClickListener {

    private ImageButton fussball;
    private Button weiter;
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

        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(overviewact.this, RegisterActivity.Startseite.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View view) {
        clicked = !clicked;
        switch (view.getId()) {
            case R.id.btrunning:
            if(clicked) {
                running.setBackgroundResource(R.color.colorBlue);
            }
            else {
                running.setBackgroundResource(R.color.colorWhite);
            }
            break;
            case R.id.btbadminton:
                if(clicked) {
                    badminton.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    badminton.setBackgroundResource(R.color.colorWhite);
                }
                break;
            case R.id.btfussball:
                if(clicked) {
                    fussball.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    fussball.setBackgroundResource(R.color.colorWhite);
                }
                break;
            case R.id.btpoker:
                if(clicked) {
                    pokern.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    pokern.setBackgroundResource(R.color.colorWhite);
                }
                break;
            case R.id.btfifa:
                if(clicked) {
                    fifa.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    fifa.setBackgroundResource(R.color.colorWhite);
                }
                break;
            case R.id.btkino:
                if(clicked) {
                    kino.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    kino.setBackgroundResource(R.color.colorWhite);
                }
                break;
            case R.id.btbar:
                if(clicked) {
                    bar.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    bar.setBackgroundResource(R.color.colorWhite);
                }
                break;
            case R.id.btfood:
                if(clicked) {
                    food.setBackgroundResource(R.color.colorBlue);
                }
                else {
                    food.setBackgroundResource(R.color.colorWhite);
                }
                break;

        }
    }
};