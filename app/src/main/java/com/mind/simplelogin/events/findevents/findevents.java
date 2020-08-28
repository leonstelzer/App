package com.mind.simplelogin.events.findevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.Profil.Profile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Startseite;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.Userliste.findFriends;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.neuerstellen.Kategorie.Interessen;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.util.ArrayList;
import java.util.List;

public class findevents extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2,tab3;
    public PagerAdapter pagerAdapter;
    private View profil, freunde, erstellen, events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findevents);
        tabLayout = findViewById(R.id.tablayout);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Events");


        viewPager = findViewById(R.id.viewpager);


        profil      = findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findevents.this, Profile.class);
                startActivity(intent);
            }
        });

        erstellen = findViewById(R.id.erstellen);
        erstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findevents.this, Interessen.class);
                startActivity(intent);

            }
        });

        freunde = findViewById(R.id.finden);
        freunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findevents.this, findFriends.class);
                startActivity(intent);

            }
        });
        events = findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findevents.this, com.mind.simplelogin.events.findevents.findevents.class);
                startActivity(intent);

            }
        });

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()== 0) {
            //        Bundle newBundle = new Bundle();
            //        newBundle.putString("interesse", "interesse");
                    pagerAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition()== 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition()== 2) {
                    pagerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuevents, menu);
        return true;
    }
}
