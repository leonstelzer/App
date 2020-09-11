package com.mind.simplelogin.Profil;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Event;
import com.mind.simplelogin.events.findevents.EventListAdapter;
import com.mind.simplelogin.events.neuerstellen.Kategorie.InteressenProfil;
import com.mind.simplelogin.overviewact;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfilBearbeiten extends AppCompatActivity {


    Button bestätigen;
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;
    TextView fullName, eevent, tevent, interessen, interessen1;

    AutoCompleteTextView ort;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView user;
    Uri imageurl;
    StorageReference mStorageRef;
    private List<Event> eventList ;
    private List<Event> eventList2 ;
    private EventListAdapter eventListAdapter;



    private StorageTask uploadtask;
    private Bitmap compressor;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilbearbeiten);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Images");
        DatabaseReference reference = database.getReference();
        eevent= findViewById(R.id.eevent);
        tevent = findViewById(R.id.tevent);

        userId = fAuth.getCurrentUser().getUid();
        fullName = findViewById(R.id.tv_name);
        bestätigen = findViewById(R.id.btbestätigen);
        ort = findViewById(R.id.tv_address);
        interessen = findViewById(R.id.tvInt);
        interessen1 = findViewById(R.id.Int);
        //telefonummer = findViewById(R.id.tvtelefon);


        //beschreibung = findViewById(R.id.tvBesc);
        user = findViewById(R.id.User);



        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filechooser();



            }
        });
        ort.setAdapter(new PlaceAutoSuggestAdapter(ProfilBearbeiten.this,android.R.layout.simple_list_item_1));
        interessen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilBearbeiten.this, InteressenProfil.class);
                startActivity(intent);
            }
        });
        interessen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilBearbeiten.this, InteressenProfil.class);
                startActivity(intent);
            }
        });



        bestätigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get Information from Edit Text or fileuploader()
                //final String eemail = email.getText().toString().trim();
                final String efullname = fullName.getText().toString();
                final String eort = ort.getText().toString();
                final String bild = "";
                final String image = imageurl != null ? imageurl.toString() : null;


                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userId);

                //save edited Information in Database

                Map<String, Object> user = new HashMap<>();
                user.put("Benutername", efullname);
                user.put("Ort", eort);


                documentReference.update(user);


                Intent intent = new Intent(ProfilBearbeiten.this, Profile.class);
                startActivity(intent);
            }
        });
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                fullName.setText(documentSnapshot.getString("Benutername"));
                ort.setText(documentSnapshot.getString("Ort"));
                Picasso.get().load(documentSnapshot.getString("Image")).into(user);


            }
        });
        eventList = new ArrayList<>();
        eventListAdapter = new EventListAdapter(getApplicationContext(), eventList);
        eventList2 = new ArrayList<>();


        fStore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                }
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        final String eventid = doc.getDocument().getId();
                        final String usid = fAuth.getCurrentUser().getUid();
                        String event1 = doc.getDocument().toObject(Event.class).getId();
                        if(usid.equals(event1)) {
                            Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                            eventList.add(event);
                            eventListAdapter.notifyDataSetChanged();
                            eevent.setText(String.valueOf(eventList.size()));







                        }
                    }

                }
            }
        });



        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        String id = doc.getDocument().getId();

                        if (id.equals(userId)){
                            final String username = doc.getDocument().toObject(Users.class).getBenutername();

                            fStore.collection("event").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                    if (e != null) {

                                    }
                                    for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                        if (doc.getType() == DocumentChange.Type.ADDED){
                                            List<String>teilnehmern=new ArrayList<>();
                                            final String eventid = doc.getDocument().getId();

                                            teilnehmern = ((Event) doc.getDocument().toObject(Event.class)).getTeilnehmer();
                                            String id = doc.getDocument().getId();
                                            if(teilnehmern.contains(username)) {
                                                Event event = doc.getDocument().toObject(Event.class).withId(eventid);
                                                eventList2.add(event);
                                                eventListAdapter.notifyDataSetChanged();
                                                tevent.setText(String.valueOf(eventList2.size()));


                                            }
                                        }
                                    }
                                }
                            });


                        }
                    }
                }}
        });



    }

    // uploads and open gallery on device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageurl = data.getData();
            user.setImageURI(imageurl);
            fileuploader();
            Picasso.get().load(imageurl).into(user);

        }

    }

    private void filechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);


    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void fileuploader() {
        final StorageReference Ref = mStorageRef.child(System.currentTimeMillis() + "," + getExtension(imageurl));
        uploadtask = Ref.putFile(imageurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                        Toast.makeText(ProfilBearbeiten.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                        // Get a URL to the uploaded content
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL, so write it to the database
                                userId = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userId);

                                documentReference.update("Image", uri.toString());
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
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
