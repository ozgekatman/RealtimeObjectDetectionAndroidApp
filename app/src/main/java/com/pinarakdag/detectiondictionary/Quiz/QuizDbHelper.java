package com.pinarakdag.detectiondictionary.Quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.pinarakdag.detectiondictionary.Quiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper{

    private static final String DATABABASE_NAME="Quiz.db";
    private static final int DATABASE_VERSION=1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    public QuizDbHelper( Context context) {
        super(context, DATABABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE "
                +CategoriesTable.TABLE_NAME + "( "
                +CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +CategoriesTable.COLUMN_NAME + " TEXT "
                + ")";
        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        fillCategoriesTable();

        final String SQL_CREATE_QUESTIONS_TABLE="CREATE TABLE "
                + QuestionsTable.TABLE_NAME + "("
                + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QuestionsTable.COLUMN_QUESTION + " TEXT, "
                + QuestionsTable.COLUMN_TEXT + " TEXT, "
                + QuestionsTable.COLUMN_OPTION1 + " TEXT, "
                + QuestionsTable.COLUMN_OPTION2 + " TEXT, "
                + QuestionsTable.COLUMN_OPTION3 + " TEXT, "
                + QuestionsTable.COLUMN_OPTION4 + " TEXT, "
                + QuestionsTable.COLUMN_ANSWER_NR + " INTEGER,"
                + QuestionsTable.COLUMN_DIFFICULTY + " TEXT, "
                + QuestionsTable.COLUMN_CATEGOTY_ID + " INTEGER, "
                + "FOREIGN KEY (" + QuestionsTable.COLUMN_CATEGOTY_ID + ") REFERENCES "
                + CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE"
                + ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        //fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable(){
        Category c1= new Category("FLAGS");
        addCategory(c1);
        Category c2= new Category("WORDS");
        addCategory(c2);
        Category c3= new Category(" CONVERSATION");
        addCategory(c3);
    }

    private void addCategory(Category category) {
        ContentValues cv=new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME,null,cv);
    }

   /* private void fillQuestionTable() {
        Question q1 = new Question("afghanistan",".", "timorleste", "fiji", "afghanistan", "albania", 3, Question.DIFFICULTY_MEDIUM, Category.FLAGS);
        addQuestion(q1);
        Question q2 = new Question("armenia", ".","bahrain", "bolivia", "armenia", "barbados", 3, Question.DIFFICULTY_EASY, Category.FLAGS);
        addQuestion(q2);
        Question q3 = new Question("botswana",".", "brunei", "brazil", "botswana", "azerbaijan", 3, Question.DIFFICULTY_HARD, Category.FLAGS);
        addQuestion(q3);
    }*/

    public void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_TEXT, question.getText());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGOTY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories(){
        List<Category> categoryList=new ArrayList<>();
        db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do {
                Category category=new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            }while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty){
        ArrayList<Question> questionList=new ArrayList<>();
        db=getReadableDatabase();

        String selection=QuestionsTable.COLUMN_CATEGOTY_ID + " = ? "
                + " AND " +QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs=new String[]{String.valueOf(categoryID),difficulty};

        Cursor c=db.query(
                QuestionsTable.TABLE_NAME,
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
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setText(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_TEXT)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGOTY_ID)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
