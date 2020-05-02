package com.mind.simplelogin.events.neuerstellen.Kategorie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mind.simplelogin.R;
import com.mind.simplelogin.events.DatePickerFragment;
import com.mind.simplelogin.events.TimerPickerFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class date_time extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ProgressBar progressBar;
    ImageView time,date;
    CircleImageView image;
    TextView ettime,etdate;
    Button weiter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        ettime = findViewById(R.id.ettime);
        etdate = findViewById(R.id.etdate);
        final String kategorie = getIntent().getStringExtra("kategorie");
        weiter= findViewById(R.id.btLogin);
        image = findViewById(R.id.kate);


        progressBar = findViewById(R.id.seekbar);
        progressBar.setProgress(50);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Bitte wählen Sie Zeit und Datum aus");
        weiter.setVisibility(View.INVISIBLE);




        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimerPickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                if (!ettime.equals("Zeit anzeigen")&& !etdate.equals("Datum anzeigen")){
                    weiter.setVisibility(View.VISIBLE);
                }
            }
        });

        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String edate = etdate.getText().toString();
                final String etime = ettime.getText().toString();

                if (etime.equals("Zeit anzeigen" )|| edate.equals("Datum anzeigen")){

                    if (etime.equals("Zeit anzeigen")){
                        ettime.setError("Zeit angeben");
                    }

                    if (edate.equals("Datum anzeigen")){
                        etdate.setError("Datum angeben");
                    }
                    return;
                }

                else {
                    ettime.setError(null);
                    etdate.setError(null);


                    Intent intent = new Intent(date_time.this, ort.class);
                    intent.putExtra("kategorie", kategorie);
                    intent.putExtra("zeit", etime);
                    intent.putExtra("date", edate);

                    System.out.println(etime + " " + edate);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
        });

        if(kategorie.equals("Fussball")){
            image.setImageResource(R.drawable.fussballneu);

        }
        else if(kategorie.equals("Pokern")){
            image.setImageResource(R.drawable.ic_pokernsvg);

        }
        else if(kategorie.equals("Badminton")){
            image.setImageResource(R.drawable.badminton);

        }
        else if(kategorie.equals("Bar besuch")){
            image.setImageResource(R.drawable.ic_barsvg);

        }
        else if(kategorie.equals("Essen")){
            image.setImageResource(R.drawable.essenneu);

        }
        else if(kategorie.equals("Kino")){
            image.setImageResource(R.drawable.ic_kinosvg);

        }
        else if(kategorie.equals("Tischtennis")){
            image.setImageResource(R.drawable.tischtennis);

        }
        else if(kategorie.equals("Zocken")){
            image.setImageResource(R.drawable.ic_playstation_4);

        }
        else if(kategorie.equals("Darts")){
            image.setImageResource(R.drawable.darts);

        }
        else if(kategorie.equals("Schwimmbad")){
            image.setImageResource(R.drawable.schwimmbad);

        }
        else if(kategorie.equals("Handball")){
            image.setImageResource(R.drawable.handball);

        }
        else if(kategorie.equals("Spazieren")){
            image.setImageResource(R.drawable.laufen);

        }
        else if(kategorie.equals("Fitnessstudio")){
            image.setImageResource(R.drawable.fitness);

        }
        else if(kategorie.equals("Konzert")){
            image.setImageResource(R.drawable.konzert);

        }
        else if(kategorie.equals("Billiard")){
            image.setImageResource(R.drawable.pool);

        }
        else if(kategorie.equals("Shoppen")){
            image.setImageResource(R.drawable.shoppen);

        }
        else if(kategorie.equals("Volleyball")){
            image.setImageResource(R.drawable.volleyball);

        }
        else if(kategorie.equals("Zoo/Tierpark")){
            image.setImageResource(R.drawable.zoo);

        }
        else if(kategorie.equals("Joggen")){
            image.setImageResource(R.drawable.laufenneu);

        }
        else if(kategorie.equals("Casino")){
            image.setImageResource(R.drawable.casino);

        }  else if (kategorie.equals("Sonstiges")) {
            image.setImageResource(R.drawable.fragezeichen);

        }


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        etdate.setText(currentDateString);
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
        if (minute < 10) ettime.setText(hourOfDay+":0"+minute);
        else ettime.setText(hourOfDay+":"+minute);
    }
}


