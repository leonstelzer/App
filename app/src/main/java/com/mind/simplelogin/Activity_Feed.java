package com.mind.simplelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Activity_Feed extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(this, modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        populateRecyclerView();
    }

    public void populateRecyclerView() {

        ModelFeed modelFeed = new ModelFeed(1, 9, 20, R.drawable.ic_propic1, R.drawable.img_post1,
                "Peter", "20:00", "Suche Leute die zur Soccerhalle wollen");
        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed(2, 6, 15, R.drawable.ic_propic2, 0,
                "Karun ", "21:00", "Ich will heute abend noch Bowlen gehen");
        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed(3, 7, 10, R.drawable.ic_propic3, 0,
                "Lakshya ", "22:00", "Wer hat Lust zum Bowlen?");
        modelFeedArrayList.add(modelFeed);

        adapterFeed.notifyDataSetChanged();
    }
}
