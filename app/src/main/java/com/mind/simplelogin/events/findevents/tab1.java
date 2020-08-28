package com.mind.simplelogin.events.findevents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.firestore.DocumentChange;
import com.mind.simplelogin.Startseite;
import com.mind.simplelogin.Userliste.findFriends;
import com.mind.simplelogin.events.DatePickerFragment;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.Userliste.UsersListAdapter;

import java.sql.Time;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.neuerstellen.Kategorie.ort;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        events = RootView.findViewById(R.id.eventlist);
        filter = RootView.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tab1.this.getActivity(), Filter.class);
                startActivity(intent);
            }
        });
        mFirestore = FirebaseFirestore.getInstance();
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
                eventList.clear();
                eventListAdapter.notifyDataSetChanged();

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        final String eventid = doc.getDocument().getId();
                        System.out.println(eventid);
                        Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                        String date = doc.getDocument().toObject(Event.class).getDatum();
                        String time = doc.getDocument().toObject(Event.class).getZeit();
                       // date +=" ";
                      //  date += time;

                        // Get Current Date Time

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


}
