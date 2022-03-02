package com.pinarakdag.detectiondictionary.WordCardList;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.pinarakdag.detectiondictionary.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;


public class WordCard2ActivityList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Word> list;
    WordCardListAdapter adapter = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_card_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new WordCardListAdapter(this, R.layout.word_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = WordCardActivity.sqLiteHelper.getData("SELECT * FROM WORD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            String tr = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Word(id, en, tr, image));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(WordCard2ActivityList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = WordCardActivity.sqLiteHelper.getData("SELECT id FROM WORD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(WordCard2ActivityList.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = WordCardActivity.sqLiteHelper.getData("SELECT id FROM WORD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
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

    ImageView imageViewWord;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_word_activity);
        dialog.setTitle("Update");

        imageViewWord = (ImageView) dialog.findViewById(R.id.imageViewWord);
        final EditText enEdit = (EditText) dialog.findViewById(R.id.enEdit);
        final EditText trEdit = (EditText) dialog.findViewById(R.id.trEdit);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        Cursor cursor = WordCardActivity.sqLiteHelper.getData("SELECT * FROM WORD WHERE id="+position);
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            enEdit.setText(en);
            String tr = cursor.getString(2);
            trEdit.setText(tr);
            byte[] image = cursor.getBlob(3);
            imageViewWord.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
            list.add(new Word(id,en,tr,image));
        }

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        WordCard2ActivityList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WordCardActivity.sqLiteHelper.updateData(
                            enEdit.getText().toString().trim(),
                            trEdit.getText().toString().trim(),
                            WordCardActivity.imageViewToByte(imageViewWord),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateWordList();
            }
        });
    }

    private void showDialogDelete(final int idWord){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(WordCard2ActivityList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    WordCardActivity.sqLiteHelper.deleteData(idWord);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
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

    private void updateWordList(){
        // get all data from sqlite
        Cursor cursor = WordCardActivity.sqLiteHelper.getData("SELECT * FROM WORD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            String tr = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Word(id, en, tr, image));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //galeriden seçilen resmi imageview olarak ayarlar
                imageViewWord.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
