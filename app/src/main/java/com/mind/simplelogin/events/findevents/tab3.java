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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.events.Freundeeinladen.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {

    public tab3() {
        // Required empty public constructor
    }


    private RecyclerView events;
    private FirebaseFirestore mFirestore;
    private List<Event> eventList ;
    private EventListAdapter eventListAdapter;
    FirebaseAuth fAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        events = RootView.findViewById(R.id.eventlist);
        mFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        eventList = new ArrayList<>();
        eventListAdapter = new EventListAdapter(getContext(), eventList);

        events.setHasFixedSize(true);
        events.setLayoutManager(new LinearLayoutManager((this.getContext())));
        events.setAdapter(eventListAdapter);


        mFirestore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String eventid = doc.getDocument().getId();
                        final String usid = fAuth.getCurrentUser().getUid();
                        String event1 = doc.getDocument().toObject(Event.class).getId();
                        System.out.println(usid+" "+event1);
                        if(usid.equals(event1)) {
                            Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                            eventList.add(event);

                            eventListAdapter.notifyDataSetChanged();


                        }
                    }

                }
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
                //searchitem.setVisible(false);
                final SearchView searchView = (SearchView) item.getActionView();
                final List<Event> allEvents = new ArrayList<>();
                allEvents.addAll(eventList);

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





