package com.mind.simplelogin.events.neuerstellen.Kategorie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.mind.simplelogin.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InteressenProfil extends AppCompatActivity{
    RecyclerView interessenview;
    RecyclerView.LayoutManager layoutManager;
    InteressenProfilAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interessen_profil);


        ArrayList<InteressenProfilItem> interessen = new ArrayList<>();


        interessen.add(new InteressenProfilItem("Fussball", R.drawable.fussballneu, false));
        interessen.add(new InteressenProfilItem("Pokern", R.drawable.ic_pokernsvg, false));
        interessen.add(new InteressenProfilItem("Badminton", R.drawable.badminton, false));
        interessen.add(new InteressenProfilItem("Bar besuch", R.drawable.ic_barsvg, false));
        interessen.add(new InteressenProfilItem("Essen", R.drawable.essenneu, false));
        interessen.add(new InteressenProfilItem("Joggen", R.drawable.laufenneu, false));
        interessen.add(new InteressenProfilItem("Kino", R.drawable.ic_kinosvg, false));
        interessen.add(new InteressenProfilItem("Tischtennis", R.drawable.tischtennis, false));
        interessen.add(new InteressenProfilItem("Zocken", R.drawable.ic_playstation_4, false));
        interessen.add(new InteressenProfilItem("Darts", R.drawable.darts, false));
        interessen.add(new InteressenProfilItem("Schwimmbad", R.drawable.schwimmbad, false));
        interessen.add(new InteressenProfilItem("Handball", R.drawable.handball, false));
        interessen.add(new InteressenProfilItem("Spazieren", R.drawable.laufen, false));
        interessen.add(new InteressenProfilItem("Fitnessstudio", R.drawable.fitness, false));
        interessen.add(new InteressenProfilItem("Konzert", R.drawable.konzert, false));
        interessen.add(new InteressenProfilItem("Billiard", R.drawable.pool, false));
        interessen.add(new InteressenProfilItem("Shoppen", R.drawable.shoppen, false));
        interessen.add(new InteressenProfilItem("Volleyball", R.drawable.volleyball, false));
        interessen.add(new InteressenProfilItem("Zoo/Tierpark", R.drawable.zoo, false));
        interessen.add(new InteressenProfilItem("Casino", R.drawable.casino, false));
        interessen.add(new InteressenProfilItem("Sonstiges", R.drawable.fragezeichen, false));


        interessenview = findViewById(R.id.interessenprofil);
        interessenview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new InteressenProfilAdapter(getApplicationContext(),interessen);

        interessenview.setLayoutManager(layoutManager);
        interessenview.setAdapter(mAdapter);
        Collections.sort(interessen, InteressenProfilItem.myname);


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
