package com.mind.simplelogin;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilBearbeiten extends AppCompatActivity  {

    TextView name;
    AlertDialog nameein;
    EditText nameaus;
    TextView ort;
    AlertDialog ortein;
    EditText ortaus;
    TextView mail;
    AlertDialog mailein;
    EditText mailaus;
    TextView tel;
    AlertDialog telein;
    EditText telaus;
    TextView inte;
    AlertDialog inteein;
    EditText inteaus;
    TextView bes;
    AlertDialog besein;
    EditText besaus;
    Button bestätigen;
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilbearbeiten);

        bestätigen = findViewById(R.id.btbestätigen);
        name = findViewById(R.id.tv_name);
        nameein = new AlertDialog.Builder(this).create();
        nameaus = new EditText(this);
        nameein.setTitle(" Bearbeiten ");
        nameein.setView(nameaus);
        ort = findViewById(R.id.tv_address);
        ortein = new AlertDialog.Builder(this).create();
        ortaus = new EditText(this);
        ortein.setTitle(" Bearbeiten ");
        ortein.setView(ortaus);
        mail = findViewById(R.id.tvEmail);
        mailein = new AlertDialog.Builder(this).create();
        mailaus = new EditText(this);
        mailein.setTitle(" Bearbeiten ");
        mailein.setView(mailaus);
        tel = findViewById(R.id.tvTel);
        telein = new AlertDialog.Builder(this).create();
        telaus = new EditText(this);
        telein.setTitle(" Bearbeiten ");
        telein.setView(telaus);
        inte = findViewById(R.id.tvInt);
        inteein = new AlertDialog.Builder(this).create();
        inteaus = new EditText(this);
        inteein.setTitle(" Bearbeiten ");
        inteein.setView(inteaus);
        bes = findViewById(R.id.tvBesc);
        besein = new AlertDialog.Builder(this).create();
        besaus = new EditText(this);
        besein.setTitle(" Bearbeiten ");
        besein.setView(besaus);


        nameein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name.setText(nameaus.getText());
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameaus.setText(name.getText());
                nameein.show();
            }
        });
        ortein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ort.setText(ortaus.getText());
            }
        });
        ort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ortaus.setText(ort.getText());
                ortein.show();
            }
        });
        mailein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mail.setText(mailaus.getText());
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailaus.setText(mail.getText());
                mailein.show();
            }
        });
        telein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tel.setText(telaus.getText());
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaus.setText(tel.getText());
                telein.show();
            }
        });
        inteein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inte.setText(inteaus.getText());
            }
        });
        inte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inteaus.setText(inte.getText());
                inteein.show();
            }
        });
        besein.setButton(DialogInterface.BUTTON_POSITIVE, "speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bes.setText(besaus.getText());
            }
        });
        bes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                besaus.setText(bes.getText());
                besein.show();
            }
        });
        bestätigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ProfilBearbeiten.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
