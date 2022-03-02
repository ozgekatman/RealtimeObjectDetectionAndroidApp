package com.pinarakdag.detectiondictionary.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pinarakdag.detectiondictionary.AssetWordCardList.DBWordCardActivity;
import com.pinarakdag.detectiondictionary.AssetPhotoWithWordList.DbWordListActivity;
import com.pinarakdag.detectiondictionary.AssetWordList.DbListActivity;
import com.pinarakdag.detectiondictionary.Detection.DetectionActivity;
import com.pinarakdag.detectiondictionary.R;
import com.pinarakdag.detectiondictionary.PhotoWithWordList.PhotoWordListActivity;
import com.pinarakdag.detectiondictionary.Game.GameMainActivity;
import com.pinarakdag.detectiondictionary.Quiz.QuizMainActivity;
import com.pinarakdag.detectiondictionary.WordCardList.WordCardActivity;
import com.pinarakdag.detectiondictionary.WordList.WordListActivity;

public class MainActivity extends AppCompatActivity {
    CardView btnPractice,btnDetection,btnPhotoList,btnQuiz,btnWordCardList,btnGame,btnWordList,btnList,btnAddList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPractice = (CardView) findViewById(R.id.btnPractice);
        btnDetection = (CardView) findViewById(R.id.btnDetection);
        btnPhotoList = (CardView) findViewById(R.id.btnPhotoList);
        btnQuiz = (CardView) findViewById(R.id.btnQuiz);
        btnWordCardList = (CardView) findViewById(R.id.btnWordCardList);
        btnGame = (CardView) findViewById(R.id.btnGame);
        btnWordList = (CardView) findViewById(R.id.btnWordList);
        btnList = (CardView) findViewById(R.id.btnList);
        btnAddList = (CardView) findViewById(R.id.btnAddList);
        btnDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetectionActivity.class);
                startActivity(intent);
            }
        });



        btnPhotoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhotoWordListActivity.class);
                startActivity(intent);
            }
        });

        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordCardActivity.class);
                startActivity(intent);
            }
        });
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizMainActivity.class);
                startActivity(intent);
            }
        });
        btnWordCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DBWordCardActivity.class);
                startActivity(intent);
            }
        });
        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameMainActivity.class);
                startActivity(intent);
            }
        });
        btnWordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DbWordListActivity.class);
                startActivity(intent);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DbListActivity.class);
                startActivity(intent);
            }
        });
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordListActivity.class);
                startActivity(intent);
            }
        });

    }
}

