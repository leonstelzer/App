package com.mind.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class overviewact extends AppCompatActivity {

    private ImageButton fussball;
    private Button weiter;
    private ImageButton badminton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_overview);

        weiter = (Button) findViewById(R.id.btweiter);
        fussball = (ImageButton) findViewById(R.id.btfussball);

        //fussball.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
        //        fussball.setBackgroundColor(Color.BLUE);
        //    };


        //


        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(overviewact.this, RegisterActivity.Startseite.class);
                startActivity(intent);
            };
        });
    };
};
