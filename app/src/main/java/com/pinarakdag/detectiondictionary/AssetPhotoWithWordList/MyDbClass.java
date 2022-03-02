package com.pinarakdag.detectiondictionary.AssetPhotoWithWordList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDbClass extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "wordlists.db";
    private static final int DATABASE_VERSION = 1;

    Context context;
    public MyDbClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    public ArrayList<DbModelClass> getAllData(){
        try{
            ArrayList<DbModelClass> objDbModelClassArrayList = new ArrayList<>();
            SQLiteDatabase objSqLiteDatabase = getReadableDatabase();
            if(objSqLiteDatabase!=null){
                Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT *FROM jobs",null);

                if(objCursor.getCount()!=0){
                    while (objCursor.moveToNext()){
                        String en = objCursor.getString(0);
                        String tr = objCursor.getString(1);
                        String sentence = objCursor.getString(2);
                        byte[] imagesByte = objCursor.getBlob(3);

                        Bitmap ourImage = BitmapFactory.decodeByteArray(imagesByte,0,imagesByte.length);
                        objDbModelClassArrayList.add(new DbModelClass(en,tr,sentence,ourImage));
                    }

                    return objDbModelClassArrayList;
                }
                else{
                    Toast.makeText(context,"No data is retrieved...",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context,"Data is null!",Toast.LENGTH_SHORT).show();
                return null;
            }

        }
        catch (Exception e){
            Toast.makeText(context,"getAllData: -"+e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
