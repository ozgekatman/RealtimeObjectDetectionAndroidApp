package com.pinarakdag.detectiondictionary.AssetWordList;

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

public class DbListActivity extends AppCompatActivity {
    MyDbClass objMyDbClass;
    ArrayList<DbModelClass> objDbModelClassArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_list);
        objMyDbClass = new MyDbClass(this);
        objDbModelClassArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.dataRV);
        showData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nounadjverb, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();


        if(id == R.id.adjective){
            Intent intent = new Intent(DbListActivity.this, DbList2Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.verb){
            Intent intent = new Intent(DbListActivity.this, DbList3Activity.class);
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
