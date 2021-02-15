package com.example.projet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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


public class ProfListeActivity extends AppCompatActivity {

    private Donnees_web donnees_web;

    private TextView tv;

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
                        openDispo();
                        return true;
                }
                return false;
            };

    private void openAgenda(){
        Intent intent = new Intent(ProfListeActivity.this, HistoriqueActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openAlarme(){
        Intent intent = new Intent(ProfListeActivity.this, AlarmeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openCalendrier(){
        /*String username = LoginActivity.Nom.getText().toString();
        String password = LoginActivity.Mdp.getText().toString();
        String[] nomPrenom = LoginActivity.db.getNomPrenom(username,password);

        intent.putExtra("nom", nomPrenom[0]);
        intent.putExtra("prenom", nomPrenom[1]);*/
        Intent intent = new Intent(ProfListeActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openAutres(){
        Intent intent = new Intent(ProfListeActivity.this, AutresActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openDispo(){
        Intent intent = new Intent(ProfListeActivity.this, DispoSalleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    List<Prof> listeProfs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_prof);

        BottomNavigationView navigation = findViewById(R.id.barre_navigation_liste_prof);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menuNav = navigation.getMenu();
        MenuItem navItem1 = menuNav.findItem(R.id.item_navigation_autres);
        navItem1.setChecked(true);

        Spinner spinner = findViewById(R.id.spinner_dept_prof);
        tv = findViewById(R.id.liste_profs);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position){
                    case 0 :
                        break;
                    case 1 :
                        donnees_web = new Donnees_web();
                        listeProfs = donnees_web.getListeProfs("APS");
                        break;
                    case 2 :
                        donnees_web = new Donnees_web();
                        listeProfs = donnees_web.getListeProfs("Dept.%20ISYS|Dept.%20ISYS:(CG)|Dept.%20ISYS:(NM)|DEpt.%20ISYS:(NM)");
                        break;
                    case 3 :
                        donnees_web = new Donnees_web();
                        listeProfs = donnees_web.getListeProfs("Dept.%20SEN|Dept.%20SEn:(ML)|Dept.%20SEN:(ML)");
                        break;
                    case 4 :
                        donnees_web = new Donnees_web();
                        listeProfs = donnees_web.getListeProfs("Dept.%20MTEL|Dept.%20MTEL:(LL)|Dept.%20MTEL:(FS)");
                        break;
                    case 5 :
                        donnees_web = new Donnees_web();
                        listeProfs = donnees_web.getListeProfs("Dept.%20IT|Dept.%20IT:(LL)|Dept.%20IT:(CA)");
                        break;
                }
                tv.setTextColor(Color.BLACK);
                tv.setText(getProfInfo(listeProfs));
                tv.setTextSize(14);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //tv.setText(getProfInfo(listeProfs));

    }

    private String getProfInfo(List<Prof> profs){
        StringBuilder profInfo = new StringBuilder();
        for(Prof prof : profs){
            String info = prof.getPrenomProf() + " " + prof.getNomProf() + "\n" + prof.getPrenomProf().toLowerCase() + "." + prof.getNomProf().toLowerCase() + "@esiee.fr\n\n";
            profInfo.append(info);
        }
        return profInfo.toString();
    }

/*
    public class ListViewProfAdapter extends ArrayAdapter<Prof> {

        private int layoutId;

        public ListViewProfAdapter(@NonNull Context context, int resource, @NonNull List<Prof> objects) {
            super(context, resource, objects);
            this.layoutId = layoutId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            Prof prof = getItem(position);
            if(convertView == null){
                view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.item_img);
                viewHolder.textView = (TextView) view.findViewById(R.id.item_text);
                view.setTag(viewHolder);
            }else{
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            Drawable drawable = getResources().getDrawable(R.drawable.a);
            viewHolder.imageView.setImageDrawable(drawable);
            String profInfo = prof.getNomProf() + " " + prof.getPrenomProf();
            viewHolder.textView.setText(profInfo);

            return view;
        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }*/

}
