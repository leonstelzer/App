package com.mind.simplelogin.events.findevents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {

    public tab2() {
        // Required empty public constructor
    }


    private RecyclerView events;
    private FirebaseFirestore mFirestore;
    private List<Event> eventList ;
    private EventListAdapter eventListAdapter;
    FirebaseAuth fAuth;




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
        String usid = fAuth.getCurrentUser().getUid();

        events.setHasFixedSize(true);
        events.setLayoutManager(new LinearLayoutManager((this.getContext())));
        events.setAdapter(eventListAdapter);


        mFirestore.collection("users").document(usid).collection("eventeinladung").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String eventid = doc.getDocument().getId();
                        //Toast.makeText(yourFriends.this, fAuth.getUid(), Toast.LENGTH_SHORT).show();


                        mFirestore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                if (e != null) {

                                }
                                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED){

                                        String id = doc.getDocument().getId();
                                        if(eventid.equals(id)) {
                                            Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                                            eventList.add(event);
                                            eventListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        });

                    }
                }
            }
        });

        return RootView;
    }


}
