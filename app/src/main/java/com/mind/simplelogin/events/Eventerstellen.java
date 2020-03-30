package com.mind.simplelogin.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.R;
import com.mind.simplelogin.myEvent;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Eventerstellen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private RelativeLayout rlayout;
    private Animation animation;
    private Menu menu;
    private Button share;
    EditText name;

    AutoCompleteTextView ort;
    TextView date, dateanzeige, zeitanzeigen, zeit;
    String userID;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    public static final String TAG = "YOUR-TAG-NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_erstellen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        rlayout     = findViewById(R.id.rlayout);
        animation   = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        rlayout.setAnimation(animation);
        share      = findViewById(R.id.btLogin);
        name =findViewById(R.id.etEmail);
        zeitanzeigen =findViewById(R.id.etPassword);
        zeit = findViewById(R.id.tvPassword);
        ort =findViewById(R.id.etRePassword);
        date=findViewById(R.id.tvUsername);
        dateanzeige=findViewById(R.id.etUsername);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final String[] eventid = {null};

        ort.setAdapter(new PlaceAutoSuggestAdapter(Eventerstellen.this,android.R.layout.simple_list_item_1));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        zeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimerPickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (name.length()==0){
                    name.setError("Name eingeben");
                }
                if (dateanzeige.length()==0){
                    zeit.setError("Zeit eingeben");
                }
                if (dateanzeige.equals("Datum anzeigen")){
                    zeit.setError("Zeit eingeben");
                }
                if (zeitanzeigen.equals("Zeit anzeigen")){
                    zeit.setError("Zeit eingeben");
                }

                if (zeitanzeigen.length()==0){
                    zeit.setError("Zeit eingeben");
                }
                if (ort.length()==0){
                    ort.setError("Ort eingeben");
                }
                else {
                    userID = fAuth.getCurrentUser().getUid();
                    String names = name.getText().toString();
                    String date = dateanzeige.getText().toString();
                    String time = zeitanzeigen.getText().toString();
                    String loc = ort.getText().toString();

                    Map<String,String> event = new HashMap<>();
                    event.put("Eventname", names);
                    event.put("Datum", date);
                    event.put("Ort", loc);
                    event.put("Zeit", time);
                    event.put("Id", userID);
                    event.put("Teilnehmer", null);

                    fStore.collection("event").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Eventerstellen.this, "Event Created.", Toast.LENGTH_SHORT).show();
                            eventid[0] = documentReference.getId();
                            Intent intent = new Intent(Eventerstellen.this, myEvent.class);
                            intent.putExtra("event_id", eventid[0]);
                            startActivity(intent);
                                    }
                                });
                }

            }
        });

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dateanzeige.setText(currentDateString);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (minute < 10) zeitanzeigen.setText(hourOfDay+":0"+minute);
        else zeitanzeigen.setText(hourOfDay+":"+minute);
    }
}

