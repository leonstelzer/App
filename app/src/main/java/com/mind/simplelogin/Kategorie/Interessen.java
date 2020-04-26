package com.mind.simplelogin.Kategorie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mind.simplelogin.R;

import java.util.ArrayList;
import java.util.Collections;

public class Interessen extends AppCompatActivity{
    RecyclerView interessenview;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interessen);


        ArrayList<Interessenitem> interessen = new ArrayList<>();


        interessen.add(new Interessenitem("Fussball", R.drawable.ic_fussballsvg));
        interessen.add(new Interessenitem("Pokern", R.drawable.ic_pokernsvg));
        interessen.add(new Interessenitem("Playstation", R.drawable.ic_playstation_4));
        interessenview = findViewById(R.id.interessenlist);
        interessenview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new InteressenAdapter(getApplicationContext(),interessen);

        interessenview.setLayoutManager(layoutManager);
        interessenview.setAdapter(mAdapter);
        Collections.sort(interessen, Interessenitem.myname);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Bitte wählen sie eine Kategorie aus");






    }



}
