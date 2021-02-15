package com.example.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="SCHEDULEGENIXDB.db";
    private static final int DATABASE_VERSION =1;


    private final String USER_TABLE = "USER_TABLE";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creation_user_table = "CREATE TABLE " + USER_TABLE + "("
                + "email TEXT PRIMARY KEY NOT NULL, " +
                "nom TEXT," +
                "prenom TEXT," +
                "password TEXT," +
                "username TEXT); ";
        db.execSQL(creation_user_table);
        String TABLE_AGENDA = "TABLE_AGENDA";
        String creation_agenda_table = "CREATE TABLE " + TABLE_AGENDA + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "evenement VARCHAR," +
                "descripton VARCHAR," +
                "date VARCHAR," +
                "image BLOB); ";
        db.execSQL(creation_agenda_table);
        String TABLE_ABSENCE = "TABLE_ABSENCE";
        String creation_absence_table = "CREATE TABLE " + TABLE_ABSENCE + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dateabs VARCHAR," +
                "heureabs VARCHAR," +
                "justif VARCHAR); ";
        db.execSQL(creation_absence_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("drop table if exists user");
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    //insertData
    public void insertData(String evenement, String description, String date_, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO TABLE_AGENDA VALUES(NULL,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,evenement);
        statement.bindString(2,description);
        statement.bindString(3,date_);
        statement.bindBlob(4,image);

        statement.executeInsert();
    }
    //update data
    public void updateData(String evenement, String description, String date_, byte[] image, int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TABLE_AGENDA SET evenement=?,description=?,date_=?,image=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,evenement);
        statement.bindString(2,description);
        statement.bindString(3,date_);
        statement.bindBlob(4,image);
        statement.bindDouble(5, id);
        statement.execute();
        database.close();
    }
    //delete data
    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql= "DELETE FROM TABLE_AGENDA WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, id);
        statement.execute();
        database.close();
    }
    //get data
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public boolean insert(String email, String nom, String prenom, String password, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("nom",nom);
        contentValues.put("prenom",prenom);
        contentValues.put("password",password);
        contentValues.put("username",username);
        long ins = db.insert("USER_TABLE",null,contentValues);
        return ins != -1;
    }


    //checking if email exists;
    public Boolean checkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_TABLE WHERE email=?", new String[]{email});
        return cursor.getCount() <= 0;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from USER_TABLE where username=?", new String[]{username});
        return cursor.getCount() <= 0;
    }

    public String[] getNomPrenom(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nom, prenom FROM USER_TABLE WHERE username=? and password=?",new String[]{username,password});
        cursor.moveToFirst();
        String[] nomPrenom = new String[2];
        nomPrenom[0] = cursor.getString(0);
        nomPrenom[1] = cursor.getString(1);
        cursor.close();
        return nomPrenom;
    }


    //checking the username and password;
    public Boolean usernamepassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from USER_TABLE where username=? and password=?",new String[]{username,password});
        return cursor.getCount() > 0;
    }

    public void updatePassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        db.update(USER_TABLE,contentValues,"email=?",new String[]{email});
        db.close();
    }


    public void insertDataa(String dateabs, String heureabs,String justif){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO TABLE_ABSENCE VALUES(NULL,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,dateabs);
        statement.bindString(2,heureabs);
        statement.bindString(3,justif);
        statement.executeInsert();
    }
    public void updateDataa(String dateabs, String heureabs,String justif,int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TABLE_ABSENCE SET dateabs=?,heureabs=?,justif=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,dateabs);
        statement.bindString(2,heureabs);
        statement.bindString(3,justif);
        statement.bindDouble(4, id);
        statement.execute();
        database.close();
    }
    public void deleteDataa(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql= "DELETE FROM TABLE_ABSENCE WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, id);
        statement.execute();
        database.close();
    }
    public Cursor getDataa(String sql){
        SQLiteDatabase databasee = getReadableDatabase();
        return databasee.rawQuery(sql,null);
    }


}
