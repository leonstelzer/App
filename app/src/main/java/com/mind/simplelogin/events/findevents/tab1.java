package com.mind.simplelogin.events.findevents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.firestore.DocumentChange;
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
public class tab1 extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public tab1() {
        // Required empty public constructor
    }

    private RecyclerView events;
    private FirebaseFirestore mFirestore;
    private List<Event> eventList ;
    private EventListAdapter eventListAdapter;
    ImageView filter;
    TextView etdate;
    Dialog myDialog;

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
                ShowPopup(v);
            }
        });
        mFirestore = FirebaseFirestore.getInstance();
        eventList = new ArrayList<>();
        eventListAdapter = new EventListAdapter(getContext(), eventList);
        events.setHasFixedSize(true);
        events.setLayoutManager(new LinearLayoutManager((this.getContext())));
        events.setAdapter(eventListAdapter);
        myDialog = new Dialog(this.getContext());
        etdate = myDialog.findViewById(R.id.searchdate);


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

    public void ShowPopup(View v) {
        TextView txtclose, etdate;
        ImageView bestätigen, date;
        AutoCompleteTextView ort;


        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        bestätigen = (ImageView) myDialog.findViewById(R.id.done);
        etdate = myDialog.findViewById(R.id.searchdate);
        date = (ImageView) myDialog.findViewById(R.id.date);
        ort = myDialog.findViewById(R.id.searchplace);
        ort.setAdapter(new PlaceAutoSuggestAdapter(this.getContext() ,android.R.layout.simple_list_item_1));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        bestätigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        etdate.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
