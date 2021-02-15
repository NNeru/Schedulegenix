package com.example.projet;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AjoutAgendaActivity extends AppCompatActivity {
    Button ButtonAjoutRappel;
    ImageView imageView;
    Button mChooseBtn;
    EditText EditTextEvenement;
    EditText EditTextDescription;
    //Button ButtonTerminer;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private static final String TAG = "AjoutAgendaActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public static SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_agenda);

        //views
        //     ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Nouvel Ajout");


        Button btnCamera =findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.imageView);
        mDisplayDate = findViewById(R.id.TextDate);
        mDisplayDate.setText("");
        mChooseBtn = findViewById(R.id.choose_image_btn);
        EditTextEvenement = findViewById(R.id.Textevenement);
        EditTextEvenement.setText("");
        EditTextDescription =findViewById(R.id.TextDescription);
        EditTextDescription.setText("");
        ButtonAjoutRappel =findViewById(R.id.AjoutRappel);
        //ButtonTerminer =findViewById(R.id.Terminer);
        //creating database
        mSQLiteHelper = new SQLiteHelper(this);
        //creating table in database
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS TABLE_AGENDA(id INTEGER PRIMARY KEY AUTOINCREMENT, evenement VARCHAR,descripton VARCHAR,date VARCHAR,image BLOB)");


        //handle button click
        mChooseBtn.setOnClickListener(v -> {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    // permission not granted, request it
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup for runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else {
                //system os is less then marshmallow
                pickImageFromGallery();
            }
        });

        btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        });




        mDisplayDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    AjoutAgendaActivity.this,
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

        ButtonAjoutRappel.setOnClickListener(v -> {
            if(hasNullOrEmptyDrawable(imageView)){
                mSQLiteHelper.insertData(
                        EditTextEvenement.getText().toString().trim(),
                        EditTextDescription.getText().toString().trim(),
                        mDisplayDate.getText().toString().trim(),
                        ImageViewtoByte(imageView)
                );
            } else{
                mSQLiteHelper.insertData(
                        EditTextEvenement.getText().toString().trim(),
                        EditTextDescription.getText().toString().trim(),
                        mDisplayDate.getText().toString().trim(),
                        ImageViewtoByte(imageView)
                );
                Toast.makeText(AjoutAgendaActivity.this,"Ajout réussi",Toast.LENGTH_SHORT).show();
                EditTextEvenement.setText("");
                EditTextDescription.setText("");
                mDisplayDate.setText("");
                startActivity(new Intent(AjoutAgendaActivity.this,HistoriqueActivity.class));
            }


        });
        // ButtonTerminer.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View v) {
        //  startActivity(new Intent(AgendaActivity.this,HistoriqueActivity.class));
        //  }
        // });
    }



    public static byte[] ImageViewtoByte(ImageView image) {
        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();

    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    // permission denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // handle result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            // SET IMAGE TO IMAGE VIEW
            // imageView.setImageURI(data.getData());
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
        else {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
    public static boolean hasNullOrEmptyDrawable(ImageView iv)
    {
        Drawable drawable = iv.getDrawable();
        BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable)drawable : null;

        return bitmapDrawable == null || bitmapDrawable.getBitmap() == null;
    }

}