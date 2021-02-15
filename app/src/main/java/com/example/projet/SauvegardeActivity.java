package com.example.projet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SauvegardeActivity extends AppCompatActivity {

    /*MySQLiteOpenHelper objet = new MySQLiteOpenHelper(this);
    ArrayList<String> noms = new ArrayList<>();
    ArrayList<String> prenoms = new ArrayList<>();
    ArrayList<String> departements = new ArrayList<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sauvegarde);
        /*Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        ArrayList<Prof> listProf = new ArrayList<>();
        try {
            listProf = (ArrayList<Prof>) objet.initaliserTableProf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Prof i : listProf) {
            noms.add(i.getNomProf());
            prenoms.add(i.getPrenomProf());
            departements.add(i.getDepartementProf());
        }
        TextView textView_prof= findViewById(R.id.textView_prof);
        textView_prof.setText(noms.toString());*/
    }
}