package com.example.projet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    SQLiteHelper db;
    EditText nom,prenom,pseudo,mdp,mdp2,email;
    Button valider,login;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        db = new SQLiteHelper(this);
        nom= findViewById(R.id.nom);
        prenom= findViewById(R.id.prenom);
        pseudo= findViewById(R.id.pseudo);
        mdp= findViewById(R.id.mdp);
        mdp2= findViewById(R.id.mdp2);
        email= findViewById(R.id.email);

        login= findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        valider= findViewById(R.id.valider);
        valider.setOnClickListener(v -> {
            String nomString = nom.getText().toString().toUpperCase();
            String prenomString = prenom.getText().toString();
            String pseudoString = pseudo.getText().toString();
            String mdpString = mdp.getText().toString();
            String mdp2String = mdp2.getText().toString();
            String emailString = email.getText().toString();
            boolean existUser = false;
            try {
                existUser = isUserExist(nomString, prenomString);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(nomString.equals("")||prenomString.equals("")||pseudoString.equals("")||mdpString.equals("")||mdp2String.equals("")||emailString.equals("")){
                Toast.makeText(getApplicationContext(),"Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
            else{
                if(existUser){
                    if(mdpString.equals(mdp2String)){
                        Boolean checkemail = db.checkemail(emailString);
                        Boolean checkusername = db.checkusername(pseudoString);
                        if((checkemail)&&(checkusername)){
                            Boolean insert = db.insert(emailString,nomString, prenomString, mdpString, pseudoString);
                            if(insert){
                                Toast.makeText(getApplicationContext(),"Vous vous êtes inscrit avec succès", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Xianli t'es nulle", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email ou nom d'utilisateur déjà pris", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Les mots de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Vous n'êtes pas dans notre base de données", Toast.LENGTH_SHORT).show();
                }

            }

        });




    }

    private boolean isUserExist(String nom, String prenom) throws IOException {
        InputStream is = getResources().getAssets().open("E3S2.xls");
        EtudiantsMatieresGroupes emg = ExcelReader.getXlsData(is, 0, nom, prenom);
        return emg != null;
    }
}