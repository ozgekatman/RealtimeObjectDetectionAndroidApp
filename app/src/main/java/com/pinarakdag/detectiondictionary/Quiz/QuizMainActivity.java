package com.pinarakdag.detectiondictionary.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.pinarakdag.detectiondictionary.R;

import java.util.List;

public class QuizMainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ=1;
    public static final String EXTRA_CATEGORY_ID="extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME="extraCategoryName";
    public static final String EXTRA_DIFFICULTY="extraDifficulty";

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String KEY_HIGHSCORE="keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerCategory;
    private Spinner spinnerDifficulty;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        spinnerDifficulty=findViewById(R.id.spinner_difficulty);
        spinnerCategory=findViewById(R.id.spinner_category);
        loadCategories();
        loadDifficultyLevels();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        textViewHighscore=findViewById(R.id.text_view_highscore);
        loadHighscore();
    }
    private void startQuiz () {
        Category selectedCategory=(Category) spinnerCategory.getSelectedItem();
        int categoryID= selectedCategory.getId();
        String categoryName=selectedCategory.getName();
        String difficulty=spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(QuizMainActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID,categoryID);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_QUIZ){
            if(resultCode==RESULT_OK){
                int score=data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if(score>highscore){
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadCategories(){
        QuizDbAssetHelper dbHelper= QuizDbAssetHelper.getInstance(this);
        List<Category> categories=dbHelper.getAllCategories();

        ArrayAdapter<Category> adapterCategories=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
    }

    private void loadDifficultyLevels(){
        String[] difficultyLevels= Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }

    private void loadHighscore(){
        SharedPreferences prefs=getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore=prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore:"+highscore);
    }

    private void updateHighscore(int highscoreNew){
        highscore=highscoreNew;
        textViewHighscore.setText("Highscore:"+highscore);

        SharedPreferences prefs=getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt(KEY_HIGHSCORE,highscore);
        editor.apply();
    }
}
