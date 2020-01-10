package com.mind.simplelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;

import io.opencensus.tags.Tag;

public class RegisterActivity extends AppCompatActivity {

    RelativeLayout rlayout;
    Animation animation;
    Menu menu;
    Button login;
    FirebaseAuth fAuth;
    EditText eemail, ename, epassswort, ewpasswort;
    ProgressBar progressBar;
    String userID;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        rlayout     = findViewById(R.id.rlayout);
        animation   = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        rlayout.setAnimation(animation);
        login      = findViewById(R.id.btLogin);
        eemail      = findViewById(R.id.etEmail);
        ename      = findViewById(R.id.etUsername);
        epassswort      = findViewById(R.id.etPassword);
        ewpasswort      = findViewById(R.id.etRePassword);


        fAuth = FirebaseAuth.getInstance();
        // progressBar = findViewById(R.id.progressBar);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = eemail.getText().toString().trim();
                String password = epassswort.getText().toString().trim();
                final String fullName = ename.getText().toString();
                 String wpasswort    = ewpasswort.getText().toString();

                if(TextUtils.isEmpty(email)){
                    eemail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    epassswort.setError("Password is Required.");
                    return;
                }

                else if(TextUtils.isEmpty(fullName)){
                    epassswort.setError("Benutzername muss gewählt sein");
                    return;
                }

                else if(password.length() < 6){
                    epassswort.setError("Password Must be >= 6 Characters");
                    return;
                }
                else if(password != wpasswort){
                    ewpasswort.setError("Muss gleich sein");
                    return;
                }



                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
 //                           Map<String,Object> user = new HashMap<>();
  //                          user.put("fName",fullName);
   //                         user.put("email",email);


                            startActivity(new Intent(getApplicationContext(),Startseite.class));

                        }else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
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
