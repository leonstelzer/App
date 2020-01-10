package com.mind.simplelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Eventerstellen extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private Menu menu;
    private Button share;
    EditText name;
    EditText datum;
    EditText zeit;
    EditText ort;

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
        String names = name.getText().toString();


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
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("Text/plain");
                    String shareBody = "file:///C:/Users/AMD/Desktop/App/Anfrage.html";
                    String shareSubject = "jtzdfuz";
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                    startActivity(Intent.createChooser(intent, "Share"));
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
