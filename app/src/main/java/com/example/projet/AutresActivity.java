package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.activities.AlarmeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AutresActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.item_navigation_calendrier:
                        openCalendrier();
                        return true;
                    case R.id.item_navigation_agenda:
                        openAgenda();
                        return true;
                    case R.id.item_navigation_alarme:
                        openAlarme();
                        return true;
                    case R.id.item_navigation_autres:
                        return true;
                    case R.id.item_navigation_disponibilite:
                        openDispo();
                        return true;
                }
                return false;
            };

    private void openAgenda(){
        Intent intent = new Intent(AutresActivity.this, HistoriqueActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openAlarme(){
        Intent intent = new Intent(AutresActivity.this, AlarmeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openCalendrier(){
        /*String username = LoginActivity.Nom.getText().toString();
        String password = LoginActivity.Mdp.getText().toString();
        String[] nomPrenom = LoginActivity.db.getNomPrenom(username,password);

        intent.putExtra("nom", nomPrenom[0]);
        intent.putExtra("prenom", nomPrenom[1]);*/
        Intent intent = new Intent(AutresActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openDispo(){
        Intent intent = new Intent(AutresActivity.this, DispoSalleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autres);
        Button absence_button = findViewById(R.id.btnAbs);
        absence_button.setOnClickListener(view -> {
            Intent intent = new Intent(AutresActivity.this, HistoriqueAbsence.class);
            startActivity(intent);
        });
        Button prof_button = findViewById(R.id.btnProf);
        prof_button.setOnClickListener(view -> {
            Intent intent = new Intent(AutresActivity.this, ProfListeActivity.class);
            startActivity(intent);
        });

        BottomNavigationView navigation = findViewById(R.id.barre_navigation_autres);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menuNav = navigation.getMenu();
        MenuItem navItem1 = menuNav.findItem(R.id.item_navigation_autres);
        navItem1.setChecked(true);
    }
}