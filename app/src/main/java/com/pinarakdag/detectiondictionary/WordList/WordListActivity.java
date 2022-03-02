package com.pinarakdag.detectiondictionary.WordList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pinarakdag.detectiondictionary.R;

import java.io.ByteArrayOutputStream;

public class WordListActivity extends AppCompatActivity {
    EditText engEdit,trEdit;
    Button btnAdd,btnList;
    public static SQLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        engEdit = findViewById(R.id.engEdit);
        trEdit = findViewById(R.id.trEdit);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Word");

        mSQLiteHelper = new SQLiteHelper(this,"WORDLISTDB.sqlite",null,1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS WORDLIST(id INTEGER PRIMARY KEY AUTOINCREMENT, en VARCHAR, tr VARCHAR)");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mSQLiteHelper.insertData(engEdit.getText().toString().trim(), trEdit.getText().toString().trim());
                    Toast.makeText(WordListActivity.this,"Added successfully",Toast.LENGTH_SHORT).show();
                    engEdit.setText("");
                    trEdit.setText("");

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        //kelime listesi burada görülür.
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WordListActivity.this, WordList2Activity.class));
            }
        });
    }
}
