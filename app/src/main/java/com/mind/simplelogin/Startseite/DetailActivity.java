package com.mind.simplelogin.Startseite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mind.simplelogin.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("param"));
    }
}
