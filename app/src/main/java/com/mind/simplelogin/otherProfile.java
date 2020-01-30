package com.mind.simplelogin;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class otherProfile extends AppCompatActivity {
    private ImageView back;
    ImageView user;
    TextView fullName, email, ort, beschreibung, telefonummer, interessen;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button add;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private String currentstate;
    private DatabaseReference FriendRequest;
    private FirebaseUser currentuser;
    public static final String TAG = "YOUR-TAG-NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherprofile);

        fullName = findViewById(R.id.tv_name);
        email = findViewById(R.id.tvEmail);
        ort = findViewById(R.id.tv_address);
        beschreibung = findViewById(R.id.beschreibung);
        telefonummer = findViewById(R.id.telefonummer);
        interessen = findViewById(R.id.interessen);
        user = findViewById(R.id.User);
        add = findViewById(R.id.add);
        currentstate = "not_friends";


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("user_id");
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();


        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(otherProfile.this, Startseite.class);
                startActivity(intent);
            }
        });


        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString("Benutername"));
                email.setText(documentSnapshot.getString("EMail"));
                ort.setText(documentSnapshot.getString("Ort"));
                telefonummer.setText(documentSnapshot.getString("Telefonnummer"));
                interessen.setText(documentSnapshot.getString("Interessen"));
                beschreibung.setText(documentSnapshot.getString("Beschreibung"));
                Picasso.get().load(documentSnapshot.getString("Image")).into(user);


            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                add.setEnabled(false);
                String yourid = fAuth.getCurrentUser().getUid();
                String otherid = getIntent().getStringExtra("user_id");
                String requestid = "";

                if (currentstate.equals("not_friends")) {
                    final Map<String, String> request = new HashMap<>();
                    request.put("yourid", yourid);
                    request.put("otherid", otherid);
                    request.put("Type", "req_send");
                    fStore.collection("request").add(request).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(otherProfile.this, "Versendet", Toast.LENGTH_SHORT).show();
                            currentstate = "req_send";
                            add.setEnabled(true);
                            add.setText("Anfrage löschen");
                            String requestid = documentReference.getId();
                            Toast.makeText(otherProfile.this, requestid, Toast.LENGTH_SHORT).show();

                        }
                    });
                }



                // Intent intent = new Intent(otherProfile.this, findFriends.class);
                // startActivity(intent);



                if (currentstate.equals("req_send")) {
                    fStore.collection("request").document(requestid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(otherProfile.this, "Gelöscht", Toast.LENGTH_SHORT).show();
                            add.setText("Freundschaftsanfrage versenden");
                            currentstate = "not_friends";
                            add.setEnabled(true);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(otherProfile.this, "Nö", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        });

    }

}