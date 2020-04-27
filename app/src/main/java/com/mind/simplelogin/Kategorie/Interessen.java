package com.mind.simplelogin.Kategorie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mind.simplelogin.R;

import java.util.ArrayList;
import java.util.Collections;

public class Interessen extends AppCompatActivity{
    RecyclerView interessenview;
    RecyclerView.LayoutManager layoutManager;
    InteressenAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interessen);


        ArrayList<Interessenitem> interessen = new ArrayList<>();


        interessen.add(new Interessenitem("Fussball", R.drawable.fussballneu));
        interessen.add(new Interessenitem("Pokern", R.drawable.ic_pokernsvg));
        interessen.add(new Interessenitem("Badminton", R.drawable.badminton));
        interessen.add(new Interessenitem("Bar besuch", R.drawable.ic_barsvg));
        interessen.add(new Interessenitem("Essen", R.drawable.essenneu));
        interessen.add(new Interessenitem("Joggen", R.drawable.laufenneu));
        interessen.add(new Interessenitem("Kino", R.drawable.ic_kinosvg));
        interessen.add(new Interessenitem("Tischtennis", R.drawable.tischtennis));
        interessen.add(new Interessenitem("Zocken", R.drawable.ic_playstation_4));
        interessen.add(new Interessenitem("Darts", R.drawable.darts));
        interessen.add(new Interessenitem("Schwimmbad", R.drawable.schwimmbad));
        interessen.add(new Interessenitem("Handball", R.drawable.handball));
        interessen.add(new Interessenitem("Spazieren", R.drawable.laufen));
        interessen.add(new Interessenitem("Fitnessstudio", R.drawable.fitness));
        interessen.add(new Interessenitem("Konzert", R.drawable.konzert));
        interessen.add(new Interessenitem("Billiard", R.drawable.pool));
        interessen.add(new Interessenitem("Shoppen", R.drawable.shoppen));
        interessen.add(new Interessenitem("Volleyball", R.drawable.volleyball));
        interessen.add(new Interessenitem("Zoo/Tierpark", R.drawable.zoo));
        interessen.add(new Interessenitem("Casino", R.drawable.casino));
        interessen.add(new Interessenitem("Sonstiges", R.drawable.fragezeichen));





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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinteressen, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
