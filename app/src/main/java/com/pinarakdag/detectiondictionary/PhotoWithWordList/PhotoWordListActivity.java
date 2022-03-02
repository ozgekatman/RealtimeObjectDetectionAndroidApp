package com.pinarakdag.detectiondictionary.PhotoWithWordList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pinarakdag.detectiondictionary.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class PhotoWordListActivity extends AppCompatActivity {
    EditText engEdit,trEdit,sentenceEdit;
    Button btnAdd,btnList;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;
    public static SQLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_word_list);
        engEdit = findViewById(R.id.engEdit);
        trEdit = findViewById(R.id.trEdit);
        sentenceEdit = findViewById(R.id.sentenceEdit);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        imageView = findViewById(R.id.imageView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Word");

        mSQLiteHelper = new SQLiteHelper(this,"WORDDB.sqlite",null,1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS WORD(id INTEGER PRIMARY KEY AUTOINCREMENT, en VARCHAR, tr VARCHAR, sentence VARCHAR, image BLOB)");

        //imageView'a tıklandığında
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //galeriden resim seçmek için harici depolama izni okunur.
                ActivityCompat.requestPermissions(PhotoWordListActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });
        //btnAdd'e tıklandığında sqlite kaydolur
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mSQLiteHelper.insertData(engEdit.getText().toString().trim(), trEdit.getText().toString().trim(), sentenceEdit.getText().toString().trim(), imageViewToByte(imageView));
                    Toast.makeText(PhotoWordListActivity.this,"Added successfully",Toast.LENGTH_SHORT).show();
                    engEdit.setText("");
                    trEdit.setText("");
                    sentenceEdit.setText("");
                    imageView.setImageResource(R.drawable.addphoto);
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
                startActivity(new Intent(PhotoWordListActivity.this, PhotoWordList2Activity.class));
            }
        });
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
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY);
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
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
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
                imageView.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
