package com.pinarakdag.detectiondictionary.Game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pinarakdag.detectiondictionary.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE="extraScore";

    private TextView gameTitle, word, info;
    private EditText wordEntered;
    private Button checkB, newB;

    private int score=0;
    private long backPressedTime;

    private List<Word> wordList;
    private int wordCounter;
    private int wordCountTotal;
    private Word currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameTitle=findViewById(R.id.gameTitle);
        info = findViewById(R.id.info);
        word = findViewById(R.id.word);
        wordEntered = findViewById(R.id.wordEntered);

        checkB = findViewById(R.id.checkB);
        newB = findViewById(R.id.newB);

        Intent intent=getIntent();
        String type=intent.getStringExtra(GameMainActivity.EXTRA_TYPE);

        GameDbAssetHelper dbHelper = GameDbAssetHelper.getInstance(this);
        wordList=dbHelper.getWords(type);
        wordCountTotal=wordList.size();
        Collections.shuffle(wordList);

        newGame();

        checkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wordEntered.getText().toString().equalsIgnoreCase(currentWord.getWord())){
                    gameTitle.setText("Awesome!");
                    checkB.setEnabled(false);
                    newB.setEnabled(true);
                    score++;
                } else {
                    gameTitle.setText("Retry!");
                }
            }
        });

         newB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }
    private void showSolution() {
        if(wordCounter < wordCountTotal){
            newB.setText("New");
        }else{
            newB.setText("Finish");
        }

    }
    private String shuffleWord(String word){//shuffle and split algoritması
        List<String> letters= Arrays.asList(word.split(""));
        Collections.shuffle(letters);
        String shuffled="";
        for(String letter:letters){
            shuffled+=letter;
        }
        return shuffled;
    }

    private void newGame(){
        if (wordCounter < wordCountTotal){
            currentWord=wordList.get(wordCounter);//rastgele kelime seçiyoruz WORDSlerden

            info.setText(currentWord.getInfo());
            word.setText(shuffleWord(currentWord.getWord()));//karıştırılmış kelimeyi göster
            wordEntered.setText("");// texvievı temizler
            gameTitle.setText("");
            wordCounter++;
            newB.setEnabled(true);//butonu killitler
            checkB.setEnabled(true);
            showSolution();
        }else {
            finishGame();
        }
    }

    private void finishGame() {
        Intent resultIntent=new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    public void onBackPressed(){
        if(backPressedTime + 2000>System.currentTimeMillis()){
            finishGame();
        }else{
            Toast.makeText(this,"Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}
