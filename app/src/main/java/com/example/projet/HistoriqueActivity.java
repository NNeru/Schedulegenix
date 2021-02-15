package com.example.projet;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.activities.AlarmeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HistoriqueActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<EvenementData> laliste;
    HistoriqueAdapter historiqueAdapter=null;
    ImageView imageViewIcon;
    SQLiteHelper db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.item_navigation_calendrier:
                        openCalendrier();
                        return true;
                    case R.id.item_navigation_agenda:
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

    private void openCalendrier(){
        /*String username = LoginActivity.Nom.getText().toString();
        String password = LoginActivity.Mdp.getText().toString();
        String[] nomPrenom = LoginActivity.db.getNomPrenom(username,password);

        intent.putExtra("nom", nomPrenom[0]);
        intent.putExtra("prenom", nomPrenom[1]);*/
        Intent intent = new Intent(HistoriqueActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }

    private void openAutres(){
        Intent intent = new Intent(HistoriqueActivity.this, AutresActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void openAlarme(){
        Intent intent = new Intent(HistoriqueActivity.this, AlarmeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openDispo(){
        Intent intent = new Intent(HistoriqueActivity.this, DispoSalleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        // ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("Liste des evenements");
        db = new SQLiteHelper(this);

        BottomNavigationView navigation = findViewById(R.id.barre_navigation_historique);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menuNav = navigation.getMenu();
        MenuItem navItem1 = menuNav.findItem(R.id.item_navigation_agenda);
        navItem1.setChecked(true);
        listview = findViewById(R.id.LaListe);
        laliste=new ArrayList<>();
        historiqueAdapter= new HistoriqueAdapter(this,R.layout.row,laliste);
        listview.setAdapter(historiqueAdapter);
        final FloatingActionButton float_add = findViewById(R.id.float_add);
        //get all data from sqlite

        Cursor cursor = db.getData("SELECT * FROM TABLE_AGENDA");
        laliste.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String evenement = cursor.getString(1);
            String description = cursor.getString(2);
            String date_ = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            //add to list
            laliste.add(new EvenementData(id,evenement,description,date_,image));
        }
        cursor.close();
        historiqueAdapter.notifyDataSetChanged();
        if(laliste.size()==0){
            Toast.makeText(this,"Aucun enregistrement trouve",Toast.LENGTH_SHORT).show();
        }
        listview.setOnItemLongClickListener((parent, view, position, l) -> {
            //alert dialog to display options of update and delete
            final CharSequence[] items = {"Supprimer"};
            final AlertDialog.Builder dialog = new AlertDialog.Builder(HistoriqueActivity.this);
            dialog.setTitle("Quelle opération voulez-vous effectuer ?");
            dialog.setItems(items, (dialog1, i) -> {
                Cursor c =  db.getData("SELECT id FROM TABLE_AGENDA");
                ArrayList<Integer> arrID = new ArrayList<>();
                while(c.moveToNext()){
                    arrID.add(c.getInt(0));
                }
                showDeleteDialog(arrID.get(position));
            });
            dialog.show();
            return true;
        });

        float_add.setOnClickListener(v -> {
            Intent  intent = new Intent(getApplicationContext(), AjoutAgendaActivity.class);
            startActivity(intent);
        });
    }
    private void updateRecordList(){
        //get all data from sqlite
        Cursor cursor = db.getData("SELECT * FROM TABLE_AGENDA");
        laliste.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String evenement = cursor.getString(1);
            String description = cursor.getString(2);
            String date_ = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
        }
        historiqueAdapter.notifyDataSetChanged();
    }
    private void showDeleteDialog(final int idRecord){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(HistoriqueActivity.this);
        dialogDelete.setTitle("Confirmation");
        dialogDelete.setMessage("Etes-vous sur(e) de vouloir supprimer cet evenement ?");
        dialogDelete.setPositiveButton("Oui", (dialog, which) -> {
            try{
                db.deleteData(idRecord);
                Toast.makeText(HistoriqueActivity.this,"Suppression reussie",Toast.LENGTH_SHORT).show();
                startActivity(getIntent());
            }
            catch(Exception e){
                Log.e("error",e.getMessage());
            }

        });
        dialogDelete.setNegativeButton("Non", (dialog, i) -> dialog.dismiss());
        dialogDelete.show();
    }

}