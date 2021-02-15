package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ParamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_scrolling);
        Button sauvegarde = findViewById(R.id.sauvegarde);
        sauvegarde.setOnClickListener(view -> {
            Intent intent = new Intent(ParamActivity.this, SauvegardeActivity.class);
            startActivity(intent);
        });
        Button notif = findViewById(R.id.notifications);
        notif.setOnClickListener(view -> {
            Intent intent = new Intent(ParamActivity.this, NotifActivity.class);
            startActivity(intent);
        });
    }
}