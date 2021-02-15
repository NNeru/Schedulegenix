package com.example.projet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AjoutAbsenceActivity extends AppCompatActivity {
    private TextView mDisplayDate;
    private TextView mDisplayHeure;
    CheckBox Justification;
    private String checking;
    Button btn_ajout;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "AjoutAbsenceActivity";
    public static SQLiteHelper mSQLiteHelper;
    int tHour,tMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absence_layout);

        mDisplayDate = findViewById(R.id.tvDate);
        mDisplayHeure = findViewById(R.id.txtHeure);
        Justification = findViewById(R.id.checkJustification);
        checking="Non justifié";
        btn_ajout = findViewById(R.id.btnAjouter);
        mSQLiteHelper = new SQLiteHelper(this);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS TABLE_ABSENCE(id INTEGER PRIMARY KEY AUTOINCREMENT, dateabs VARCHAR,heureabs VARCHAR, justif VARCHAR)");

        mDisplayDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    AjoutAbsenceActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        mDateSetListener = (datePicker, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyyy: " + dayOfMonth + "/" + month + "/" + year);

            String date = dayOfMonth + "/" + month + "/" + year;
            mDisplayDate.setText(date);
        };

        mDisplayHeure.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AjoutAbsenceActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    (view, hourOfDay, minute) -> {
                        tHour = hourOfDay;
                        tMinute = minute;
                        String time = tHour + ":" + tMinute;
                        SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm: aa"
                            );
                            mDisplayHeure.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }, 12, 0, false
            );
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.updateTime(tHour, tMinute);
            timePickerDialog.show();
        });
        Justification.setOnClickListener(view -> {
            if (Justification.isChecked()) {
                Justification.setTextColor(getResources().getColor(R.color.colorAccent));
                checking="Justifié";
            } else {
                Justification.setTextColor(getResources().getColor(R.color.ColorBlack));
                checking="Non justifié";
            }
        });

       // String checking;
       // if(Justification.isChecked()){
            //checking="Justifié";
       // }
        //else{
          //  checking="Non Justifié";
      //  }

        btn_ajout.setOnClickListener(v -> {
                mSQLiteHelper.insertDataa(
                        mDisplayHeure.getText().toString().trim(),
                        mDisplayDate.getText().toString().trim(),
                        checking

                );
                Toast.makeText(AjoutAbsenceActivity.this, "Ajout réussi", Toast.LENGTH_SHORT).show();
                mDisplayDate.setText("");
                mDisplayHeure.setText("");
                startActivity(new Intent(AjoutAbsenceActivity.this, HistoriqueAbsence.class));
        });


    }

}
