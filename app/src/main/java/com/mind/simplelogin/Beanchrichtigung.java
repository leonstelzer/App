package com.mind.simplelogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.mind.simplelogin.Profil.otherProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class Beanchrichtigung extends AppCompatActivity {

    private RecyclerView friendlist;
    private FirebaseFirestore mFirestore;
    private List<Users> usersList ;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private BenachrichtigungAdapter benachrichtigungAdapter;
    Button annehmen, ablehnen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benachrichtigung);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        friendlist = findViewById(R.id.friendlist);
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        benachrichtigungAdapter = new BenachrichtigungAdapter(getApplicationContext(), usersList);
        final String usid = fAuth.getCurrentUser().getUid();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Benachrichtigungen");


        friendlist.setHasFixedSize(true);
        friendlist.setLayoutManager(new LinearLayoutManager((this)));
        friendlist.setAdapter(benachrichtigungAdapter);


        mFirestore.collection("users").document(usid).collection("request").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                                                     @Override
                                                                                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                                                                        System.out.println("Start ON Event");
                                                                                                        if (e != null) {
                                                                                                        }
                                                                                                        System.out.println("MAP GROEESSE : " + queryDocumentSnapshots.getDocumentChanges().size());
                                                                                                         for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                                                                                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                                                                                                String type = (String) doc.getDocument().get("Type");
                                                                                                                String otherid = (String) doc.getDocument().get("otherid");

                                                                                                                System.out.println("type:"+type);
                                                                                                                System.out.println("otherid:"+ otherid);

                                                                                                                final String otherID = (String) doc.getDocument().get("otherid");
                                                                                                                if (type.equals("received")) {
                                                                                                                    mFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                                                                        @Override
                                                                                                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                                                                                            if (e != null) {

                                                                                                                            }
                                                                                                                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                                                                                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                                                                                                    String id = doc.getDocument().getId();
                                                                                                                                    if (otherID.equals(id)) {
                                                                                                                                        Users users = doc.getDocument().toObject(Users.class).withId(otherID);
                                                                                                                                        usersList.add(users);
                                                                                                                                        benachrichtigungAdapter.notifyDataSetChanged();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                });
        annehmen = findViewById(R.id.annehmen);
        ablehnen = findViewById(R.id.ablehnen);

      /*  ablehnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("users").document(otherid).collection("request").document(otherid+usid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Beanchrichtigung.this, "Gelöscht", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Beanchrichtigung.this, "Nö", Toast.LENGTH_SHORT).show();


                    }
                });
                fStore.collection("users").document(usid).collection("request").document(usid+otherid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Beanchrichtigung.this, "Nö", Toast.LENGTH_SHORT).show();


                    }
                });
            }

        });

        annehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, String> friends = new HashMap<>();
                friends.put(otherid, otherid);


                final Map<String, String> friends1 = new HashMap<>();
                friends1.put(usid, usid);

                fStore.collection("users").document(usid).collection("friends").document(otherid).set(friends, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Beanchrichtigung.this,"Ihr seid jetzt Freunde", Toast.LENGTH_SHORT).show();
                        fStore.collection("users").document(otherid).collection("request").document(otherid +usid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Beanchrichtigung.this, "Nö", Toast.LENGTH_SHORT).show();

                            }
                        });
                        fStore.collection("users").document(usid).collection("request").document(usid+ otherid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Beanchrichtigung.this, "Nö", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                fStore.collection("users").document(otherid).collection("friends").document(usid).set(friends1,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
            }

        });
*/

    }
    }
