package com.pinarakdag.detectiondictionary.Game;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class GameDbAssetHelper extends SQLiteAssetHelper {
    private static final String DATABABASE_NAME="Word.db";
    private static final int DATABASE_VERSION=1;

    private static GameDbAssetHelper instance;

    public GameDbAssetHelper(Context context) {
        super(context, DATABABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized GameDbAssetHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GameDbAssetHelper(context.getApplicationContext());
        }
        return instance;
    }

    public ArrayList<Word> getWords(String type){
        ArrayList<Word> wordList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String[] selectionArgs= new String[]{type};
        Cursor c= db.rawQuery("SELECT * FROM " + GameContract.WordsTable.TABLE_NAME + " WHERE " + GameContract.WordsTable.COLUMN_TYPE +" = ? ", selectionArgs);

        if (c.moveToFirst()){
            do {
                Word word=new Word();
                word.setWord(c.getString(c.getColumnIndex(GameContract.WordsTable.COLUMN_WORD)));
                word.setInfo(c.getString(c.getColumnIndex(GameContract.WordsTable.COLUMN_INFO)));
                word.setType(c.getString(c.getColumnIndex(GameContract.WordsTable.COLUMN_TYPE)));
                wordList.add(word);
            }while (c.moveToNext());
        }
        c.close();
        return wordList;

    }
}
