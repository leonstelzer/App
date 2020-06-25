package com.mind.simplelogin.events.findevents;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.mind.simplelogin.events.DatePickerFragment;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Filter extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageView date;
    TextView etdate;
    Button bestätigen;
    AutoCompleteTextView ort;
    EditText interessen;




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
