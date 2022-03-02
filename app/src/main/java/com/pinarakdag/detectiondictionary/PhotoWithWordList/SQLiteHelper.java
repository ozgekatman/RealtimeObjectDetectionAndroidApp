package com.pinarakdag.detectiondictionary.PhotoWithWordList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

    SQLiteHelper(Context context, String en,SQLiteDatabase.CursorFactory factory,int version){
        super(context,en,factory,version);
    }
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void insertData(String en, String tr, String sentence,byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO WORD VALUES(NULL, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,en);
        statement.bindString(2,tr);
        statement.bindString(3,sentence);
        statement.bindBlob(4,image);

        statement.executeInsert();
    }
    public void updateData(String en, String tr, String sentence, byte[] image,int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE WORD SET en=?, tr=?, sentence=?, image=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,en);
        statement.bindString(2,tr);
        statement.bindString(3,sentence);
        statement.bindBlob(4,image);
        statement.bindDouble(5,(double)id);

        statement.execute();
        database.close();
    }
    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM WORD  WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
