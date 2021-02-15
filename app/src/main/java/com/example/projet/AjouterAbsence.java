package com.example.projet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AjouterAbsence extends AppCompatActivity {

    private static final String TAG = "AjouterAbsence";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView mTimeTextView;
    Button ajouterButton;
    Button mPickTimeButton;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absence_layout);
        mDisplayDate = findViewById(R.id.tvDate);

        mTimeTextView = findViewById(R.id.txtHeure);

        Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        ajouterButton = findViewById(R.id.btnAjouter);

        mPickTimeButton = findViewById(R.id.btnHeure);
        ajouterButton.setOnClickListener(v -> {
            //
        });

        mPickTimeButton.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, (view, hourOfDay, minute1) -> mTimeTextView.setText(hourOfDay + ":" + minute1),hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
            timePickerDialog.show();
        });

        mDisplayDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    AjouterAbsence.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (datePicker, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyyy: " + dayOfMonth + "/" + month + "/" + year);

            String date = dayOfMonth + "/" + month + "/" + year;
            mDisplayDate.setText(date);
        };




    }


}