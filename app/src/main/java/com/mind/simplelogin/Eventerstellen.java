package com.mind.simplelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.util.HashMap;
import java.util.Map;

public class Eventerstellen extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private Menu menu;
    private Button share;
    EditText name;
    EditText datum;
    EditText zeit;
    AutoCompleteTextView ort;
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
        datum =findViewById(R.id.etUsername);
        zeit =findViewById(R.id.etPassword);
        ort =findViewById(R.id.etRePassword);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final String[] eventid = {null};

        ort.setAdapter(new PlaceAutoSuggestAdapter(Eventerstellen.this,android.R.layout.simple_list_item_1));




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (name.length()==0){
                    name.setError("Name eingeben");
                }
                if (datum.length()==0){
                    datum.setError("Datum eingeben");
                }
                if (zeit.length()==0){
                    zeit.setError("Zeit eingeben");
                }
                if (ort.length()==0){
                    ort.setError("Ort eingeben");
                }
                else {
                    userID = fAuth.getCurrentUser().getUid();
                    String names = name.getText().toString();
                    String date = datum.getText().toString();
                    String time = zeit.getText().toString();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
