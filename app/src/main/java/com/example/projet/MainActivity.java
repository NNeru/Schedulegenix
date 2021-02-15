package com.example.projet;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projet.activities.AlarmeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Donnees_web donnees_web;

    private String[] userName =  new String[2];
    //private String[] userName = {"LI", "Xianli"};

    //la liste des id de l(utilisateur
    private List<String> groupesID = null;

    //le map des seances de l'utilisateur
    //private Map<String, List<Seance>> seancesJourmap;
    private int semaine;

    private final static String[] jours = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"};

    //les relativelayout
    private Map<String, RelativeLayout> viewJours = null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.item_navigation_calendrier:
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
                        openDispo();
                        return true;
                }
                return false;
            };

    private void openAgenda(){
        Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    private void openAutres(){
        Intent intent = new Intent(MainActivity.this, AutresActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    private void openAlarme(){
        Intent intent = new Intent(MainActivity.this, AlarmeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
    private void openDispo(){
        Intent intent = new Intent(MainActivity.this, DispoSalleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FileInputStream fis = openFileInput("username.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String text = buffer.readLine();
            String s[] = text.split(" ");
            userName[0] = s[0];
            userName[1] = s[1];
            Toast.makeText(getApplicationContext(),userName[0] + " " + userName[1],Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(),"Veuiilez cliquer sur Charger l'emploi du temps actuel pour visualiser l'emploi du temps",Toast.LENGTH_SHORT).show();

        BottomNavigationView navigation = findViewById(R.id.barre_navigation_calendrier);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menuNav = navigation.getMenu();
        MenuItem navItem1 = menuNav.getItem(2);
        navItem1.setChecked(true);

        donnees_web = new Donnees_web();
        initialiserViewsJours();

        Button btnChargement = findViewById(R.id.chargement_EDT);
        btnChargement.setOnClickListener(view -> {
            if(groupesID == null){
                initialiserGroupesID();
            }
            viderViewJours();
            semaine = 31;
            creerSeanceView();

        });
        Spinner spinner = findViewById(R.id.spinner_semaine);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if(position != 0){
                    if(groupesID == null) {
                        initialiserGroupesID();
                    }
                    viderViewJours();
                    semaine = position + 14;
                    creerSeanceView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void initialiserViewsJours(){
        this.viewJours = new HashMap<>();
        viewJours.put(jours[0], (RelativeLayout) findViewById(R.id.lundi));
        viewJours.put(jours[1], (RelativeLayout) findViewById(R.id.mardi));
        viewJours.put(jours[2], (RelativeLayout) findViewById(R.id.mercredi));
        viewJours.put(jours[3], (RelativeLayout) findViewById(R.id.jeudi));
        viewJours.put(jours[4], (RelativeLayout) findViewById(R.id.vendredi));
        viewJours.put(jours[5], (RelativeLayout) findViewById(R.id.samedi));
    }

    private void viderViewJours(){
        if(this.viewJours != null){
            for(RelativeLayout jour : this.viewJours.values()){
                jour.removeAllViews();
            }
        }

    }


    private void initialiserGroupesID(){
        try {
            InputStream is = getResources().getAssets().open("E3S2.xls");
            EtudiantsMatieresGroupes emg = ExcelReader.getXlsData(is, 0, userName[0], userName[1]);
            Map<String, String> matiereGroupe = emg.getMatiereGroupe();
            this.groupesID = this.donnees_web.getUserGroupeID(matiereGroupe);
            this.groupesID.add("4665");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, List<Seance>> getListSeancesUser(String semaine) {
        try{
            return this.donnees_web.getUserSeances(this.groupesID, semaine);
        }catch (Exception e){
            e.getCause();
            e.getMessage();
        }
        return null;
    }

    private void creerSeanceView(){
        Map<String, List<Seance>> seancesJourMap = this.getListSeancesUser(String.valueOf(this.semaine));

        if(!seancesJourMap.isEmpty()&&seancesJourMap != null){
            for(String jour : jours) {
                String date = null;
                if (seancesJourMap.get(jour) != null) {
                    for (Seance seance : seancesJourMap.get(jour)) {
                        CardView cardView = new CardView(this);
                        String[] rgb = seance.getCouleurSeance().split(",");
                        LinearLayout.LayoutParams paramsCardView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, seance.getHeightSeance());
                        cardView.setLayoutParams(paramsCardView);
                        cardView.setY(seance.getTransitionYSeance());
                        cardView.setCardBackgroundColor(Color.argb(255, Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
                        cardView.setRadius(10);
                        cardView.setCardElevation(8);
                        cardView.setContentPadding(5, 5, 5, 5);
                        TextView seanceTextView = new TextView(this);
                        StringBuilder contenuTextView = new StringBuilder("<b>" + seance.getNameSeance() + "</b>" + "<br />" + seance.getNomMatiereSeance() + "<br />");
                        for (String groupe : seance.getListeGroupes()) {
                            contenuTextView.append(groupe).append(" ");
                        }
                        contenuTextView.append("<br />");
                        for (String salle : seance.getListeSalles()) {
                            contenuTextView.append(salle).append(" ");
                        }
                        contenuTextView.append("<br />");
                        for (String prof : seance.getListeProfs()) {
                            contenuTextView.append(prof).append(" ");
                        }
                        seanceTextView.setText(Html.fromHtml(contenuTextView.toString()));

                        String contenuSeance = Html.fromHtml(contenuTextView.toString()).toString();
                        contenuSeance += "\n" + seance.getHeureDebutSeance() + " - " + seance.getHeureFinSeance() + "\n" + seance.getDateSeance();
                        AlertDialog normalDialog = new AlertDialog.Builder(MainActivity.this).setMessage(contenuSeance).create();
                        cardView.setOnClickListener(view -> normalDialog.show());
                        seanceTextView.setTextSize(8);
                        seanceTextView.setTextColor(Color.BLACK);
                        seanceTextView.setGravity(Gravity.CENTER);
                        LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        seanceTextView.setLayoutParams(paramsTextView);
                        cardView.addView(seanceTextView);
                        viewJours.get(jour).addView(cardView);
                    }
                }


            }

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getApplicationContext().getResources().getString(R.string.quit_fr));
        builder.setPositiveButton(getApplicationContext().getResources().getString(R.string.oui), (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application
            finish();
        });
        builder.setNegativeButton(getApplicationContext().getResources().getString(R.string.non), (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert=builder.create();
        alert.show();
    }



}