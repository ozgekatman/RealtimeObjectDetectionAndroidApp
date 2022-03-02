package com.pinarakdag.detectiondictionary.Quiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class QuizDbAssetHelper extends SQLiteAssetHelper {

    private static final String DATABABASE_NAME="Quiz.db";
    private static final int DATABASE_VERSION=1;

    private static QuizDbAssetHelper instance;

    public QuizDbAssetHelper(Context context) {
        super(context, DATABABASE_NAME, null, DATABASE_VERSION);

    }

    public static synchronized QuizDbAssetHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbAssetHelper(context.getApplicationContext());
        }
        return instance;
    }

    public List<Category> getAllCategories(){
        List<Category> categoryList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM " + QuizContract.CategoriesTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do {
                Category category=new Category();
                category.setId(c.getInt(c.getColumnIndex(QuizContract.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            }while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty){
        ArrayList<Question> questionList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String selection= QuizContract.QuestionsTable.COLUMN_CATEGOTY_ID + " = ? "
                + " AND " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs=new String[]{String.valueOf(categoryID),difficulty};

        Cursor c=db.query(
                QuizContract.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()){
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setText(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_TEXT)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGOTY_ID)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
