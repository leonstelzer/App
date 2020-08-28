package com.mind.simplelogin.events.neuerstellen.Kategorie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mind.simplelogin.Profil.otherProfile;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Startseite;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.Userliste.UsersListAdapter;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.Freundeeinladen.EventEinladen;
import com.mind.simplelogin.events.chat.ChatMessage;
import com.mind.simplelogin.events.chat.ChatRoom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class allevent extends AppCompatActivity {

    TextView name, ort, zeit, datum,  kategorietxt, kosten, anzahlteilnehmer, ersteller, privat;
    ImageView image, teilnehmen, share, delete, einladen, map, chat;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    RecyclerView teilnehmer;
    String userId, eventid;
    private List<Users> usersList ;

    LinearLayout bar;
    private TeilnehmerAdapter teilnehmerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allevent);

        bar = findViewById(R.id.linear);
        name = findViewById(R.id.kate);
        map = findViewById(R.id.maps);
        image = findViewById(R.id.bildkate);
        kosten = findViewById(R.id.kosten);
        anzahlteilnehmer = findViewById(R.id.anzahl);
        ersteller = findViewById(R.id.username);
        ort = findViewById(R.id.ort);
        zeit = findViewById(R.id.zeit);
        datum = findViewById(R.id.date);
        privat = findViewById(R.id.privat);
        teilnehmer = findViewById(R.id.teilnehmer);
        teilnehmen = findViewById(R.id.bildteilnehmer);
        kategorietxt = findViewById(R.id.kate);
        share = findViewById(R.id.share);
        einladen = findViewById(R.id.einladen);
        delete = findViewById(R.id.delete);
        chat = findViewById(R.id.bildchat);
        chat.setVisibility(INVISIBLE);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        String kategorie = getIntent().getStringExtra("kategorie");
        eventid = getIntent().getStringExtra("eventid");
        //share  = findViewById(R.id.button);
        bar.setVisibility(INVISIBLE);
        usersList = new ArrayList<>();
        teilnehmerAdapter = new TeilnehmerAdapter(getApplicationContext(), usersList);
        teilnehmer.setHasFixedSize(true);
        teilnehmer.setLayoutManager(new LinearLayoutManager((this)));
        teilnehmer.setAdapter(teilnehmerAdapter);


        final DocumentReference documentReference = fStore.collection("event").document(eventid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            String privaten, kost, kategorie, place;
            List<String> teil;

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                zeit.setText(documentSnapshot.getString("Zeit"));
                ort.setText(documentSnapshot.getString("Ort"));
                datum.setText(documentSnapshot.getString("Datum"));



                teil = documentSnapshot.toObject(Event.class).getTeilnehmer();
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format(Locale.ENGLISH, "geo:19.076,72.8777");
         //               String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                });

                final int anzahl = teil.size();
                for (int i = 0; i < teil.size(); i++) {
                    final int finalI = i;
                    fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                final String username = doc.getDocument().toObject(Users.class).getBenutername();
                                if(username.equals(teil.get(finalI))) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        final String otheruserid = doc.getDocument().getId();
                                        String benutzername = teil.get(finalI);
                                        teilnehmer.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(allevent.this, otherProfile.class);
                                                intent.putExtra("user_id", otheruserid);
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                }
                            }
                        }
                    });
                }
                usersList.clear();
                fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            final String username = doc.getDocument().toObject(Users.class).getBenutername();


                            if(teil.contains(username)) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                   // chat.setVisibility(ImageView.VISIBLE);
                                    final String otheruserid = doc.getDocument().getId();
                                    Users users = doc.getDocument().toObject(Users.class).withId(otheruserid);
                                    usersList.add(users);
                                    teilnehmerAdapter.notifyDataSetChanged();


                                }
                            }

                        }
                    }
                });



                kategorietxt.setText(documentSnapshot.getString("Kategorie"));
                kosten.setText(documentSnapshot.getString("Kosten"));
                privat.setText(documentSnapshot.getString("Private"));
                final int max = documentSnapshot.toObject(Event.class).getMax();
                if(max == anzahl){
                    teilnehmen.setEnabled(false);
                }


                anzahlteilnehmer.setText(anzahl+"/"+max);
                kategorie = kategorietxt.getText().toString();
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
                final String id = documentSnapshot.toObject(Event.class).getId();

                if(id.equals(userId)) {
                    DocumentReference documentReference2 = fStore.collection("users").document(userId);
                    documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            final String name = documentSnapshot.toObject(Users.class).getBenutername();
                            ersteller.setText(name);
                            bar.setVisibility(VISIBLE);

                            if(max == anzahl){
                                einladen.setVisibility(INVISIBLE);
                                share.setVisibility(INVISIBLE);

                            }
                            einladen.setImageResource(R.drawable.people);
                            einladen.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(allevent.this, EventEinladen.class);
                                    intent.putExtra("eventid", eventid);
                                    intent.putExtra("herkunft", "2");
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
                else{
                    DocumentReference documentReference1 = fStore.collection("users").document(id);
                    documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            final String name = documentSnapshot.toObject(Users.class).getBenutername();
                            ersteller.setText(name);
                        }
                    });
                }
                ersteller.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(allevent.this, otherProfile.class);
                        intent.putExtra("user_id", id);
                        startActivity(intent);
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
        });

        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(final DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    String id = doc.getDocument().getId();

                    if(id.equals(userId)) {

                        final String username = doc.getDocument().toObject(Users.class).getBenutername();

                        DocumentReference documentReference = fStore.collection("event").document(eventid);
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<String>teilnehmern=new ArrayList<>();

                                Event event = (Event) documentSnapshot.toObject(Event.class);
                                teilnehmern = event.getTeilnehmer();

                                if (teilnehmern.contains(username)) {
                                    teilnehmen.setImageResource(R.drawable.minus);
                                    usersList.remove(username);
                                    teilnehmerAdapter.notifyDataSetChanged();
                                    chat.setVisibility(VISIBLE);
                                } else {
                                    teilnehmen.setImageResource(R.drawable.add);
                                }

                            }
                        });

                    }
                }
            }
        });

        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    String id = doc.getDocument().getId();

                    if(id.equals(userId)) {
                        final Users myuser = doc.getDocument().toObject(Users.class);
                        final String username = doc.getDocument().toObject(Users.class).getBenutername();

                        teilnehmen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final DocumentReference documentReference = fStore.collection("event").document(eventid);

                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        List<String>teilnehmern=new ArrayList<>();

                                        Event event = (Event) documentSnapshot.toObject(Event.class);
                                        teilnehmern = event.getTeilnehmer();


                                        if (teilnehmern.contains(username)) {
                                            teilnehmern.remove(username);
                                            usersList.remove(myuser);
                                            documentReference.update("Teilnehmer", teilnehmern);
                                            teilnehmerAdapter.notifyDataSetChanged();
                                            teilnehmen.setImageResource(R.drawable.add);

                                        } else {
                                            teilnehmern.add(username);
                                            usersList.add(myuser);
                                            documentReference.update("Teilnehmer", teilnehmern);
                                            teilnehmen.setImageResource(R.drawable.minus);
                                            teilnehmerAdapter.notifyDataSetChanged();



                                        }

                                    }
                                });

                            }
                        });
                    }
                }
            }
        });
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("event").document(eventid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(allevent.this, "Gelöscht", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(allevent.this, Startseite.class);
                        startActivity(intent);
                    }
                });
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(allevent.this, ChatRoom.class);
                intent.putExtra("eventid", eventid);
                startActivity(intent);
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