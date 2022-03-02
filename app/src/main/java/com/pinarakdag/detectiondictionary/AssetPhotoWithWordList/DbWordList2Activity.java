package com.pinarakdag.detectiondictionary.AssetPhotoWithWordList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.pinarakdag.detectiondictionary.R;

import java.util.ArrayList;

public class DbWordList2Activity extends AppCompatActivity {
    MyDbClassLessons objMyDbClass;
    ArrayList<DbModelClass> objDbModelClassArrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_word_list2);
        objMyDbClass = new MyDbClassLessons(this);
        objDbModelClassArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.dataRV);
        showData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.joblessoncategories, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();


        if(id == R.id.jobs){
            Intent intent = new Intent(DbWordList2Activity.this, DbWordListActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void showData(){
        try {
            objDbModelClassArrayList=objMyDbClass.getAllData();
            DbAdapter objDbAdapter = new DbAdapter(objDbModelClassArrayList);

            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(objDbAdapter);
        }
        catch (Exception e){
            Toast.makeText(this,"showData:-"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
