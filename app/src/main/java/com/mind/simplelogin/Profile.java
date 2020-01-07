package com.mind.simplelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class Profile extends AppCompatActivity {
    private ImageView bearbeiten;
    private ImageView back;
    TextView name;
    EditText nameaus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bearbeiten      = findViewById(R.id.bearbeiten);
        back            = findViewById(R.id.back);
        bearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Einstellungen.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Startseite.class);
                startActivity(intent);
            }
        });
        name = findViewById(R.id.tv_name);


    }
}