package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class Mdpoubli extends AppCompatActivity {

    private EditText textInputEditTextEmail;
    private SQLiteHelper databaseHelper;
    private NestedScrollView nestedScrollView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdp);
        textInputEditTextEmail = findViewById(R.id.txtMail);
        databaseHelper = new SQLiteHelper(this);
        Button buttonConfirm = findViewById(R.id.confirm);
        buttonConfirm.setOnClickListener(view -> verifyFromSQLite());
    }

    private void verifyFromSQLite(){

        if (textInputEditTextEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill your email", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!databaseHelper.checkemail(textInputEditTextEmail.getText().toString())) {
            Intent accountsIntent = new Intent(this, ConfirmPassword.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString());
            emptyInputEditText();
            startActivity(accountsIntent);
        }
        else {
            Toast.makeText(this, "Cet email n'existe pas", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText(){
        textInputEditTextEmail.setText("");
    }
}
