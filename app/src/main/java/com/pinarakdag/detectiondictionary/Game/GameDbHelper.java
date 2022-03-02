package com.pinarakdag.detectiondictionary.Game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.pinarakdag.detectiondictionary.Game.GameContract.WordsTable;;

import java.util.ArrayList;


public class GameDbHelper extends SQLiteOpenHelper {

    private static final String DATABABASE_NAME="Word.db";
    private static final int DATABASE_VERSION=1;

    private static GameDbHelper instance;

    private SQLiteDatabase db;

    public GameDbHelper(@Nullable Context context) {
        super(context, DATABABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized GameDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GameDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       this.db=db;

       final String SQL_CREATE_WORDS_TABLE=" CREATE TABLE "
               + WordsTable.TABLE_NAME + " ( "
               + WordsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + WordsTable.COLUMN_WORD + " TEXT, "
               + WordsTable.COLUMN_INFO + " TEXT, "
               + WordsTable.COLUMN_TYPE + " TEXT "
               + ")";
       db.execSQL(SQL_CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WordsTable.TABLE_NAME);
        onCreate(db);
    }

    private void addWord(Word word) {
        ContentValues cv=new ContentValues();
        cv.put(WordsTable.COLUMN_WORD, word.getWord());
        cv.put(WordsTable.COLUMN_INFO, word.getInfo());
        cv.put(WordsTable.COLUMN_TYPE, word.getType());
        db.insert(WordsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Word> getWords(String type){
        ArrayList<Word> wordList=new ArrayList<>();
        db=getReadableDatabase();

        String[] selectionArgs= new String[]{type};
        Cursor c= db.rawQuery("SELECT * FROM " + WordsTable.TABLE_NAME + " WHERE " + WordsTable.COLUMN_TYPE +" = ? ", selectionArgs);

        if (c.moveToFirst()){
            do {
                Word word=new Word();
                word.setWord(c.getString(c.getColumnIndex(WordsTable.COLUMN_WORD)));
                word.setInfo(c.getString(c.getColumnIndex(WordsTable.COLUMN_INFO)));
                word.setType(c.getString(c.getColumnIndex(WordsTable.COLUMN_TYPE)));
                wordList.add(word);
            }while (c.moveToNext());
        }
        c.close();
        return wordList;

    }

}
