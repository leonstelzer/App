package com.mind.simplelogin.events.neuerstellen.Kategorie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Startseite.Startseite;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.Freundeeinladen.EventEinladen;

import java.util.List;

import javax.annotation.Nullable;


public class meinevent  extends AppCompatActivity{

    TextView name, ort, zeit, datum, teilnehmer, kategorietxt, kosten, anzahlteilnehmer, ersteller, privat;
    ImageView image, teilnehmer2, share,delete,done;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, eventid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meinevent);

        name = findViewById(R.id.kate);
        image = findViewById(R.id.bildkate);
        kosten = findViewById(R.id.kosten);
        anzahlteilnehmer = findViewById(R.id.anzahl);
        ersteller = findViewById(R.id.username);
        ort = findViewById(R.id.ort);
        zeit = findViewById(R.id.zeit);
        datum = findViewById(R.id.date);
        privat = findViewById(R.id.privat);
        teilnehmer = findViewById(R.id.teilnehmer);
        teilnehmer2 = findViewById(R.id.bildteilnehmer);
        kategorietxt = findViewById(R.id.kate);
        share = findViewById(R.id.share);
        done = findViewById(R.id.done);
        delete = findViewById(R.id.delete);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        final String kategorie = getIntent().getStringExtra("kategorie");
        eventid = getIntent().getStringExtra("eventid");
        //share  = findViewById(R.id.button);

        final DocumentReference documentReference = fStore.collection("event").document(eventid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            String privaten, kost, max;
            List<String> teil;

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                zeit.setText(documentSnapshot.getString("Zeit"));
                ort.setText(documentSnapshot.getString("Ort"));
                datum.setText(documentSnapshot.getString("Datum"));
                teilnehmer.setText(lstToString((List)documentSnapshot.get("Teilnehmer")));
                kategorietxt.setText(documentSnapshot.getString("Kategorie"));
                kosten.setText(documentSnapshot.getString("Kosten"));
                privat.setText(documentSnapshot.getString("Private"));
                teil =documentSnapshot.toObject(Event.class).getTeilnehmer();
                int anzahl = teil.size();
                final String id = documentSnapshot.toObject(Event.class).getId();
                final int max = documentSnapshot.toObject(Event.class).getMax();
                anzahlteilnehmer.setText(anzahl+"/"+max);

                privaten = privat.getText().toString();
                kost = kosten.getText().toString();
                if (kost.equals("")){
                    kosten.setText("0 €");
                }
                if (!kost.equals("")){
                    kosten.setText(kost+" €");
                }
                if (privaten.equals("false")){
                    privat.setText("Öffentlich");
                }
                if (privaten.equals("true")){
                    privat.setText("Privat");
                }
                DocumentReference documentReference2 = fStore.collection("users").document(userId);
                documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final String name = documentSnapshot.toObject(Users.class).getBenutername();
                        ersteller.setText(name);
                    }
                });
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


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("Text/plain");
                String shareBody = "file:///C:/Users/AMD/Desktop/App/Anfrage.html";
                String shareSubject = "jtzdfuz";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                startActivity(Intent.createChooser(intent, "Share"));


            }
        });

        teilnehmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meinevent.this, EventEinladen.class);
                intent.putExtra("eventid", eventid);
                intent.putExtra("kategorie", kategorie);

                intent.putExtra("herkunft", "1");
                startActivity(intent);
            }
        });
        teilnehmer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meinevent.this, EventEinladen.class);
                intent.putExtra("eventid", eventid);
                intent.putExtra("herkunft", "1");
                intent.putExtra("kategorie", kategorie);

                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meinevent.this, Startseite.class);
                Toast.makeText(meinevent.this, "Fertig", Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("event").document(eventid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(meinevent.this, "Gelöscht", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(meinevent.this, Startseite.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(meinevent.this, "Nö", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private String lstToString(List<String> lst) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lst.size()-1; i++) {
            sb.append(lst.get(i));
            sb.append(", ");
        }
        if (lst.size()>0){
            sb.append(lst.get(lst.size()-1));}
        return sb.toString();
    }

}



