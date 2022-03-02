package com.pinarakdag.detectiondictionary.PhotoWithWordList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.pinarakdag.detectiondictionary.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class PhotoWordList2Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<Model> list;
    WordListAdapter adapter = null;
    ImageView imageViewIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_word_list2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Word List");
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new WordListAdapter(this,R.layout.row,list);
        listView.setAdapter(adapter);



        PhotoWordListActivity.mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS WORD(id INTEGER PRIMARY KEY AUTOINCREMENT, en VARCHAR, tr VARCHAR, sentence VARCHAR, image BLOB)");

        Cursor cursor = PhotoWordListActivity.mSQLiteHelper.getData("SELECT * FROM WORD");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            String tr = cursor.getString(2);
            String sentence = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            list.add(new Model(id,en,tr,sentence,image));
        }
        adapter.notifyDataSetChanged();
        if(list.size()==0){
            Toast.makeText(this,"No record found...",Toast.LENGTH_SHORT).show();
        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"Update","Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoWordList2Activity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i == 0){
                            //update
                            Cursor c = PhotoWordListActivity.mSQLiteHelper.getData("SELECT id FROM WORD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            //dialog güncellemesini göster
                            showDialogUpdate(PhotoWordList2Activity.this,arrID.get(position));

                        }
                        if(i ==1){
                            //delete
                            Cursor c = PhotoWordListActivity.mSQLiteHelper.getData("SELECT id FROM WORD");
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
    private  void showDialogDelete(final int idRecord){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(PhotoWordList2Activity.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    PhotoWordListActivity.mSQLiteHelper.deleteData(idRecord);
                    Toast.makeText(PhotoWordList2Activity.this,"Delete successfully",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error",e.getMessage());
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
    private void showDialogUpdate(final Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("Update");

        imageViewIcon = dialog.findViewById(R.id.imageViewRecord);
        final EditText engEdit = dialog.findViewById(R.id.engEdit);
        final EditText trEdit = dialog.findViewById(R.id.trEdit);
        final EditText sentenceEdit = dialog.findViewById(R.id.sentenceEdit);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        Cursor cursor = PhotoWordListActivity.mSQLiteHelper.getData("SELECT * FROM WORD WHERE id="+position);
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String en = cursor.getString(1);
            engEdit.setText(en);
            String tr = cursor.getString(2);
            trEdit.setText(tr);
            String sentence = cursor.getString(3);
            sentenceEdit.setText(sentence);
            byte[] image = cursor.getBlob(4);
            imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
            list.add(new Model(id,en,tr,sentence,image));
        }

        //dialog width
        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        //dialog heigth
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(PhotoWordList2Activity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},888);

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PhotoWordListActivity.mSQLiteHelper.updateData(engEdit.getText().toString().trim(),trEdit.getText().toString().trim(),sentenceEdit.getText().toString().trim(),PhotoWordListActivity.imageViewToByte(imageViewIcon),position);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Update Successfull",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Update error",error.getMessage());
                }
                updateWordList();
            }
        });
    }

    private void updateWordList() {
        Cursor cursor = PhotoWordListActivity.mSQLiteHelper.getData("SELECT * FROM WORD");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String eng = cursor.getString(1);
            String tr = cursor.getString(2);
            String sentence = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            list.add(new Model(id,eng,tr,sentence,image));
        }
        adapter.notifyDataSetChanged();
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 888){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,888);
            }
            else{
                Toast.makeText(this,"Dosya konumuna erişim izniniz yok.",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 888 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            //görüntü yönergelerini etkinleştirir
            //görüntü kare olacak
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //galeriden seçilen resmi imageview olarak ayarlar
                imageViewIcon.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
