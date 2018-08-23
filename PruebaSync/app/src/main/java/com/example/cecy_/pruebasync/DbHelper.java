package com.example.cecy_.pruebasync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cecy_ on 30/07/2018.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DbContact.TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + DbContact.NAME + " TEXT," + DbContact.SYNC_STATUS + " INTEGER);";
    private static final String DROP_TABLE = "DROP TABLE IF EXIST " + DbContact.TABLE_NAME;

    public DbHelper(Context context){
        super(context, DbContact.DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveToLocalDatabase(String name, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContact.NAME, name);
        contentValues.put(DbContact.SYNC_STATUS, sync_status);
        database.insert(DbContact.TABLE_NAME, null, contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database){
        String[] projection = {DbContact.NAME, DbContact.SYNC_STATUS};

        return (database.query(DbContact.TABLE_NAME, projection, null, null, null, null, null));
    }

    public void updateLocalDatabase(String name, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContact.SYNC_STATUS, sync_status);
        String selection = DbContact.NAME + " LIKE ?";
        String[] selection_args = {name};
        database.update(DbContact.TABLE_NAME, contentValues, selection, selection_args);
    }
}
