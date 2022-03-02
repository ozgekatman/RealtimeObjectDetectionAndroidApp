package com.pinarakdag.detectiondictionary.WordList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pinarakdag.detectiondictionary.R;

import java.util.ArrayList;

public class WordList2Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<Model> list;
    WordListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Word List");
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new WordListAdapter(this, R.layout.wordlist_row, list);
        listView.setAdapter(adapter);

        WordListActivity.mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS WORDLIST(id INTEGER PRIMARY KEY AUTOINCREMENT, en VARCHAR, tr VARCHAR)");
        Cursor cursor = WordListActivity.mSQLiteHelper.getData("SELECT * FROM WORDLIST");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            String tr = cursor.getString(2);
            list.add(new Model(id, en, tr));
        }
        adapter.notifyDataSetChanged();
        if (list.size() == 0) {
            Toast.makeText(this, "No record found...", Toast.LENGTH_SHORT).show();
        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(WordList2Activity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            //update
                            Cursor c = WordListActivity.mSQLiteHelper.getData("SELECT id FROM WORDLIST");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            //dialog güncellemesini göster
                            showDialogUpdate(WordList2Activity.this, arrID.get(position));

                        }
                        if (i == 1) {
                            //delete
                            Cursor c = WordListActivity.mSQLiteHelper.getData("SELECT id FROM WORDLIST");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void showDialogDelete(final int idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(WordList2Activity.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    WordListActivity.mSQLiteHelper.deleteData(idRecord);
                    Toast.makeText(WordList2Activity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateWordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void showDialogUpdate(final Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_list_dialog);
        dialog.setTitle("Update");

        final EditText engEdit = dialog.findViewById(R.id.engEdit);
        final EditText trEdit = dialog.findViewById(R.id.trEdit);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        Cursor cursor = WordListActivity.mSQLiteHelper.getData("SELECT * FROM WORDLIST WHERE id=" + position);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            engEdit.setText(en);
            String tr = cursor.getString(2);
            trEdit.setText(tr);
            list.add(new Model(id, en, tr));
        }
        //dialog width
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        //dialog heigth
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WordListActivity.mSQLiteHelper.updateData(engEdit.getText().toString().trim(), trEdit.getText().toString().trim(), position);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Successfull", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateWordList();
            }
        });
    }

    private void updateWordList() {
        Cursor cursor = WordListActivity.mSQLiteHelper.getData("SELECT * FROM WORDLIST");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String eng = cursor.getString(1);
            String tr = cursor.getString(2);
            list.add(new Model(id, eng, tr));
        }
        adapter.notifyDataSetChanged();
    }
}


