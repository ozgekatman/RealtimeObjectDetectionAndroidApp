package com.pinarakdag.detectiondictionary.Game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pinarakdag.detectiondictionary.R;

public class GameMainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GAME=1;
    public static final String EXTRA_TYPE="extraType";

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String KEY_HIGHSCORE="keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerType;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        spinnerType=findViewById(R.id.spinner_type);
        loadTypes();

        Button buttonStartGame = findViewById(R.id.button_start_game);
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        textViewHighscore=findViewById(R.id.text_game_highscore);
        loadHighscore();
    }

    private void loadTypes() {
        String[] wordTypes=Word.getAllTypes();

        ArrayAdapter<String> adapterTypes=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wordTypes);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerType.setAdapter(adapterTypes);
    }

    private void startGame () {
        String type=spinnerType.getSelectedItem().toString();
        Intent intent = new Intent(GameMainActivity.this, GameActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        startActivityForResult(intent, REQUEST_CODE_GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_GAME){
            if(resultCode==RESULT_OK){
                int score=data.getIntExtra(GameActivity.EXTRA_SCORE, 0);
                if(score>highscore){
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadHighscore() {
        SharedPreferences prefs=getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore=prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText(" Game Highscore: "+highscore);
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
