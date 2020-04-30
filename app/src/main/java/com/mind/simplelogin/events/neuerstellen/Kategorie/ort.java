package com.mind.simplelogin.events.neuerstellen.Kategorie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ort extends AppCompatActivity {
    private ProgressBar progressBar;
    Button weiter;
    CircleImageView image;
    AutoCompleteTextView ort;
    String userID;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Switch kostenpflichtig, privat;
    EditText geld, max;
    TextView pp, ausage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place);
        weiter= findViewById(R.id.btLogin);
        image = findViewById(R.id.kate);
        ort = findViewById(R.id.ort);
        kostenpflichtig = findViewById(R.id.kostenswitch);
        privat = findViewById(R.id.privatswitch);
        geld = findViewById(R.id.kosten);
        ausage = findViewById(R.id.privat);
        pp = findViewById(R.id.properson);
        max = findViewById(R.id.max);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final String userId = fAuth.getCurrentUser().getUid();


        geld.setVisibility(View.INVISIBLE);
        pp.setVisibility(View.INVISIBLE);


        kostenpflichtig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    geld.setVisibility(View.VISIBLE);
                    pp.setVisibility(View.VISIBLE);
                }
                if(isChecked == false){
                    geld.setVisibility(View.INVISIBLE);
                    pp.setVisibility(View.INVISIBLE);
                }
            }
        });

        privat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    ausage.setText("Das Event ist nur für eingeladene User zu sehen");
                }
                if(isChecked == false){
                    ausage.setText("Das Event ist öffentlich");
                }
            }
        });
        final Boolean state = privat.isChecked();
        String privat = null;
        if(state.equals(true)){
            privat = "true";
        }
        if(state.equals(false)){
            privat = "false";
        }



        progressBar = findViewById(R.id.seekbar);
        progressBar.setProgress(75);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Wählen Sie bitte den Ort aus");

        final String kategorie = getIntent().getStringExtra("kategorie");
        final String zeit = getIntent().getStringExtra("zeit");
        final String date = getIntent().getStringExtra("date");
        final String[] eventid = {null};

        ort.setAdapter(new PlaceAutoSuggestAdapter(ort.this,android.R.layout.simple_list_item_1));

        final String finalPrivat = privat;
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ort.length()==0){
                    ort.setError("Ort eingeben");
                }
                if (max.length()==0){
                    max.setError("Maximale Teilnehmerzahl eingeben");
                }
                else {

                    DocumentReference documentReference2 = fStore.collection("users").document(userId);
                    documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            final String username = documentSnapshot.toObject(Users.class).getBenutername();

                            userID = fAuth.getCurrentUser().getUid();
                            String loc = ort.getText().toString();
                            String kosten = geld.getText().toString();
                            String maxe = max.getText().toString();
                            int i = Integer.parseInt(maxe);
                            final List<String> teilnehmer = new ArrayList<>();
                            teilnehmer.add(username);


                            Map<String,Object> event = new HashMap<>();

                            event.put("Kategorie", kategorie);
                            event.put("Datum", date);
                            event.put("Ort", loc);
                            event.put("Zeit", zeit);
                            event.put("Id", userID);
                            event.put("Teilnehmer", teilnehmer);
                            event.put("Kosten", kosten);
                            event.put("Private", finalPrivat);
                            event.put("Max", i);


                            fStore.collection("event").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(ort.this, "Event Created.", Toast.LENGTH_SHORT).show();
                                    eventid[0] = documentReference.getId();
                                    Intent intent = new Intent(ort.this, meinevent.class);
                                    intent.putExtra("eventid", eventid[0]);
                                    intent.putExtra("kategorie", kategorie);

                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                                }
                            });


                        }
                    });




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
            image.setImageResource(R.drawable.laufenneu);

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
        else if(kategorie.equals("Casino")){
            image.setImageResource(R.drawable.casino);

        }  else if (kategorie.equals("Sonstiges")) {
            image.setImageResource(R.drawable.fragezeichen);

        }




    }

}
