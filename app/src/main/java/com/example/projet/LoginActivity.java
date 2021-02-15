package com.example.projet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    public static EditText Nom;
    public static EditText Mdp;
    //private int compteur=5;
    public static SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.login_layout);

        // change actionbar title, if you don't change it, it will be according to your system default language, French
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;


        Button changeLang = findViewById(R.id.changeMyLang);
        changeLang.setOnClickListener(v -> {
            // show AlertDialog to display a list of languages, one can be selected
            showChangeLanguageDialog();
        });

        db = new SQLiteHelper(this);

        Nom = findViewById(R.id.Lognom);
        Mdp = findViewById(R.id.LogMdp);
        TextView mdp_oubli = findViewById(R.id.btnMdp);
        mdp_oubli.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Mdpoubli.class);
            startActivity(intent);
        });
        //private TextView Tentative;
        Button connexion = findViewById(R.id.Logbtn);
        connexion.setOnClickListener(v -> {
            String username = Nom.getText().toString();
            String password = Mdp.getText().toString();
            Boolean checkusernamepassword = db.usernamepassword(username,password);
            if(checkusernamepassword){
                //Toast.makeText(getApplicationContext(),"Connexion établie",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String[] nomPrenom = db.getNomPrenom(username,password);
                String filename = "username.txt";
                String fileContents = nomPrenom[0] + " " + nomPrenom[1];
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(fileContents.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(),"Nom d'utilisateur ou mot de passe incorrect",Toast.LENGTH_SHORT).show();

        });
        Button register = findViewById(R.id.signupbtn);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void showChangeLanguageDialog() {
        // array of languages to display in alert dialog
        final String[] listItems = {"Français", "English", "Malagasy", "Wolof", "中文"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        mBuilder.setTitle("Choisir une langue...");
        mBuilder.setSingleChoiceItems(listItems, -1, (dialog, i) -> {
            if (i == 0) {
                //French
                setLocale("fr");
                recreate();
            }
            else if (i == 1) {
                //English
                setLocale("en");
                recreate();
            }
            else if (i == 2) {
                //Malagasy
                setLocale("mg");
                recreate();
            }
            else if (i == 3) {
                //Wolof
                setLocale("wo");
                recreate();
            }
            else if (i == 4) {
                //中文
                setLocale("zh");
                recreate();
            }
            // dismiss alert dialog when language selected
            dialog.dismiss();
        });

        AlertDialog mDialog = mBuilder.create();
        // show alert dialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // load language in shared preferences
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
    //private void valider(String nomutilisateur, String mdputilisateur ){
    //  if((nomutilisateur.equals("Neil")) && (mdputilisateur.equals("2020"))){
    //Intent intent = new Intent(MainActivity.this,MenuActivity.class);
    //startActivity(intent);
    //finish();
    //}
    //else{
    //  compteur=compteur-1;
    //Tentative.setText("Le nombre de tentatives restant: " + String.valueOf(compteur));
    //if(compteur==0){
    //  Connexion.setEnabled(false);
    //}
    //}
    //}

}