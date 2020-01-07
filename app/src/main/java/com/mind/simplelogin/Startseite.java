package com.mind.simplelogin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Startseite extends AppCompatActivity {

    private LinearLayout profil;
    private LinearLayout newsfeed;

    private LinearLayout friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);
        profil      = findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, Profile.class);
                startActivity(intent);
            }
        });
        newsfeed    = findViewById(R.id.newsfeed);
        newsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, Searchfilter.class);
                startActivity(intent);
            }
        });

        friends = findViewById(R.id.friends);
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Startseite.this, RowFriends.class);
                startActivity(intent);
            }
        });
    }
}
