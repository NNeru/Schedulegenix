package com.example.projet;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.activities.AlarmeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DispoSalleActivity extends AppCompatActivity {

    private Donnees_web donnees_web;

    private List<Salle> salleListe;


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
                        openAutres();
                        return true;
                    case R.id.item_navigation_disponibilite:
                        return true;
                }
                return false;
            };

    private void openAgenda(){
        Intent intent = new Intent(DispoSalleActivity.this, HistoriqueActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openAlarme(){
        Intent intent = new Intent(DispoSalleActivity.this, AlarmeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openCalendrier(){

        /*String username = LoginActivity.Nom.getText().toString();
        String password = LoginActivity.Mdp.getText().toString();
        String[] nomPrenom = LoginActivity.db.getNomPrenom(username,password);

        intent.putExtra("nom", nomPrenom[0]);
        intent.putExtra("prenom", nomPrenom[1]);*/
        Intent intent = new Intent(DispoSalleActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openAutres(){
        Intent intent = new Intent(DispoSalleActivity.this, AutresActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispo_salle);
        final TextView listeSalles = findViewById(R.id.listeSalles);
        Spinner spinner = findViewById(R.id.spinner_types_salles);
        donnees_web = new Donnees_web();
        BottomNavigationView navigation = findViewById(R.id.barre_navigation_dispo);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menuNav = navigation.getMenu();
        MenuItem navItem1 = menuNav.findItem(R.id.item_navigation_disponibilite);
        navItem1.setChecked(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                List<String> sallesDispos = null;
                try {

                    switch (position){
                        case 0 : //listeSalles.setText("");break;
                            sallesDispos = new ArrayList<>();
                            break;
                        case 1 : //listeSalles.setText(.toString());
                            sallesDispos = donnees_web.getDispoSalles("vid??o");
                            break;
                        case 2 : //listeSalles.setText(.toString());
                            sallesDispos = donnees_web.getDispoSalles("SANS INSTRUMENTATION");
                            break;
                        case 3 : //listeSalles.setText(.toString());
                            sallesDispos = donnees_web.getDispoSalles("AVEC INSTRUMENTATION");
                            break;
                        case 4 : //listeSalles.setText(.toString());
                            sallesDispos = donnees_web.getDispoSalles("SPECIFIQUE");
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listeSalles.setText(getListeSalleRange(sallesDispos));
                listeSalles.setTextColor(Color.BLACK);
                listeSalles.setTextSize(16);
                listeSalles.setGravity(Gravity.CENTER);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private String getListeSalleRange(List<String> listeNonRange){
        StringBuilder listeRange = new StringBuilder();
        int i = 1;
        for(String nomSalle : listeNonRange){
            listeRange.append(nomSalle).append("\t\t\t\t\t\t");
            if(i%3==0){
                listeRange.append("\n\n");
            }
            i++;
        }
        return listeRange.toString();
    }

}