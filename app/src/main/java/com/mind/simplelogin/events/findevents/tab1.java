package com.mind.simplelogin.events.findevents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import javax.annotation.Nullable;
public class tab1 extends Fragment  {

    public tab1() {
        // Required empty public constructor
    }

    private RecyclerView events;
    private FirebaseFirestore mFirestore;
    private List<Event> eventList ;
    private EventListAdapter eventListAdapter;
    ImageView filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        if(getArguments() != null){
            String yourText = getArguments().getString("interessen");
            System.out.println(yourText);
        }

        events = RootView.findViewById(R.id.eventlist);
        eventList = new ArrayList<>();
        mFirestore = FirebaseFirestore.getInstance();
        eventListAdapter = new EventListAdapter(getContext(), eventList);
        events.setHasFixedSize(true);
        events.setLayoutManager(new LinearLayoutManager((this.getContext())));
        events.setAdapter(eventListAdapter);


        mFirestore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                eventList.clear();
                eventListAdapter.notifyDataSetChanged();

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        final String eventid = doc.getDocument().getId();
                        System.out.println(eventid);
                        Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                        String date = doc.getDocument().toObject(Event.class).getDatum();
                        String time = doc.getDocument().toObject(Event.class).getZeit();

                        Calendar c = Calendar.getInstance();
                        Date current = new Date(date);
                        Long nextDay =  System.currentTimeMillis();

                        Date next = new Date(nextDay);

                        if(!next.after(current)){
                            eventList.add(event);
                        }

                    }
                }
                eventListAdapter.notifyDataSetChanged();
                events.invalidate();
            }
        });

        return RootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {



            case R.id.searchEvent:
                final SearchView searchView = (SearchView) item.getActionView();
                final List<Event> allEvents = new ArrayList<>();
                allEvents.addAll(eventList);
                //searchitem.setVisible(false);


                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        eventList.clear();
                        eventList.addAll(allEvents);
                        return true;

                }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                        eventList.clear();
                        eventList.addAll(allEvents);
                        return true;
                    }

                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        eventListAdapter.getFilter().filter(newText);
                        eventListAdapter.notifyDataSetChanged();
                        return false;
                    }

                });
                // Do Activity menu item stuff here
                return true;
            default:

                return false;
        }
    }
}
