package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AbsenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afficher_absence);
        FloatingActionButton fab = findViewById(R.id.fltPlus);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AbsenceActivity.this, AjoutAbsenceActivity.class);
            startActivity(intent);
        });
    }
}
