package com.example.projet.activities;

import com.example.projet.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.AutresActivity;
import com.example.projet.DispoSalleActivity;
import com.example.projet.HistoriqueActivity;
import com.example.projet.LoginActivity;
import com.example.projet.MainActivity;
import com.example.projet.ProfListeActivity;
import com.example.projet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.projet.activities.AlarmeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.projet.LoginActivity;

public class AlarmeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_navigation_calendrier:
                    openCalendrier();
                    return true;
                case R.id.item_navigation_agenda:
                    openAgenda();
                    return true;
                case R.id.item_navigation_alarme:
                    return true;
                case R.id.item_navigation_disponibilite:
                    openDispo();
                    return true;
                case R.id.item_navigation_autres:
                    openAutres();
                    return true;
            }
            return false;
        }
    };

    private void openCalendrier(){
        /*String username = LoginActivity.Nom.getText().toString();
        String password = LoginActivity.Mdp.getText().toString();
        String[] nomPrenom = LoginActivity.db.getNomPrenom(username,password);

        intent.putExtra("nom", nomPrenom[0]);
        intent.putExtra("prenom", nomPrenom[1]);*/
        Intent intent = new Intent(AlarmeActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }

    private void openAgenda(){
        Intent intent = new Intent(AlarmeActivity.this, HistoriqueActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void openAutres(){
        Intent intent = new Intent(AlarmeActivity.this, AutresActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void openDispo(){
        Intent intent = new Intent(AlarmeActivity.this, DispoSalleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.barre_navigation_alarme);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menuNav = navigation.getMenu();
        MenuItem navItem1 = menuNav.findItem(R.id.item_navigation_alarme);
        navItem1.setChecked(true);
    }
}
