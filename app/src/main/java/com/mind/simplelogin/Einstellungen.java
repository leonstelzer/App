package com.mind.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Einstellungen extends AppCompatActivity {

    private TextView profilbearbeiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellungen);
        profilbearbeiten      = findViewById(R.id.profilbearbeiten);
        profilbearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Einstellungen.this, ProfilBearbeiten.class);
                startActivity(intent);
            }
        });
        {

        }
    }
}
