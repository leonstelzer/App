package com.mind.simplelogin.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mind.simplelogin.R;
import com.mind.simplelogin.Startseite.Startseite;
import com.mind.simplelogin.place.PlaceAutoSuggestAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    RelativeLayout rlayout;
    Animation animation;
    Menu menu;
    Button login;
    FirebaseAuth fAuth;
    EditText eemail, ename, epassswort;
    AutoCompleteTextView eort;

    String userID;
    FirebaseFirestore fStore;
    public static final String TAG = "YOUR-TAG-NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        login = findViewById(R.id.btLogin);
        eemail = findViewById(R.id.etEmail);
        ename = findViewById(R.id.etUsername);
        epassswort = findViewById(R.id.etPassword);
        eort = findViewById(R.id.etRePassword);
        eort.setAdapter(new PlaceAutoSuggestAdapter(RegisterActivity.this,android.R.layout.simple_list_item_1));



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = eemail.getText().toString().trim();
                String password = epassswort.getText().toString().trim();
                final String fullname = ename.getText().toString();
                final String ort = eort.getText().toString();
                final List<String> interesssen = new ArrayList<>();
                final String beschreibung = "Bitte noch ausfüllen";
                final String telefonnummer = "Bitte noch ausfüllen";
                final String image = null;

                if (TextUtils.isEmpty(email)) {
                    eemail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    epassswort.setError("Password is Required.");
                    return;
                } else if (TextUtils.isEmpty(fullname)) {
                    epassswort.setError("Benutzername muss gewählt sein");
                    return;
                } else if (password.length() < 6) {
                    epassswort.setError("Password Must be >= 6 Characters");
                    return;
                }
                else if (TextUtils.isEmpty(ort)) {
                    epassswort.setError("Ort muss angeben werdwen");
                    return;
                }



                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            Map<String,Object> user = new HashMap<>();
                            user.put("Benutername", fullname);
                            user.put("EMail", email);
                            user.put("Ort", ort);
                            user.put("Interessen", interesssen);
                            user.put("Beschreibung", beschreibung);
                            user.put("Telefonnummer", telefonnummer);
                            user.put("Image",image);
                            user.put("BenachrichtigungsCount", 0);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "New Profil for"+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Startseite.class));

                    }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                }


                });
            }

        });

    }


}




