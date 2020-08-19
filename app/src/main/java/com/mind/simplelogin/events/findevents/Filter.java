package com.mind.simplelogin.events.findevents;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Startseite;
import com.mind.simplelogin.events.DatePickerFragment;
import com.mind.simplelogin.events.neuerstellen.Kategorie.Interessenitem;
import com.mind.simplelogin.events.neuerstellen.Kategorie.date_time;
import com.mind.simplelogin.events.neuerstellen.Kategorie.ort;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Filter extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageView date;
    TextView etdate;
    Button bestätigen;
    AutoCompleteTextView ort,interessen;
    private static final String[] COUNTRIES = new String[]{
            "Fussball", "Pokern", "Badminton", "Bar besuch", "Essen","Joggen", "Kino", "Tischtennis", "Zocken", "Darts",
            "Schwimmbad", "Handball", "Spazieren", "Fitnessstudio", "Konzert","Billiard", "Shoppen", "Volleyball", "Zoo/Tierpark", "Casino","Sonstiges"
    };




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        date = findViewById(R.id.date);
        etdate = findViewById(R.id.etdate);
        bestätigen= findViewById(R.id.btLogin);
        ort = findViewById(R.id.ort);
        interessen = findViewById(R.id.interessen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Filtereinstellungen");
       // ort.setAdapter(new PlaceAutoSuggestAdapter(this,android.R.layout.simple_list_item_1));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        String[] inti = getResources().getStringArray(R.array.interessen);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, inti);
        interessen.setAdapter(adapter);





        bestätigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Filter.this, findevents.class);
                String edate = etdate.getText().toString();
                String inter = interessen.getText().toString();
                String loc = ort.getText().toString();

                intent.putExtra("interesse", inter);
                intent.putExtra("ort", loc);
                intent.putExtra("date", edate);
                startActivity(intent);


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
}
