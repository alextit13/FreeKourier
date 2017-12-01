package com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrivateRoomCourier extends AppCompatActivity {

    private TextView edit_courier_name,edit_courier_email,edit_courier_number_phone
            ,edit_courier_number_draver_root,edit_courier_date_of_birdth
            ,edit_courier_nnumber_card,edit_courier_about_courier;
    private RatingBar edit_courier_rating;
    private ImageView edit_courier_get_photo_from_camenra,edit_courier_get_photo_from_memory;
    private de.hdodenhof.circleimageview.CircleImageView edit_courier_photo;
    private ListView edit_courier_list_ads_personal;
    private String mCurrentPhotoPath;
    private Uri photoURI;
    static final int REQUEST_TAKE_PHOTO = 500;
    private Bitmap photo;
    private static final int SELECT_FILE = 2;
    private Courier courier;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_room_courier);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        courier = (Courier) intent.getSerializableExtra("courier");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        edit_courier_name = (TextView)findViewById(R.id.edit_courier_name);
        edit_courier_email = (TextView)findViewById(R.id.edit_courier_email);
        edit_courier_number_phone = (TextView)findViewById(R.id.edit_courier_number_phone);
        edit_courier_number_draver_root = (TextView)findViewById(R.id.edit_courier_number_draver_root);
        edit_courier_date_of_birdth = (TextView)findViewById(R.id.edit_courier_date_of_birdth);
        edit_courier_nnumber_card = (TextView)findViewById(R.id.edit_courier_nnumber_card);
        edit_courier_about_courier = (TextView)findViewById(R.id.edit_courier_about_courier);

        edit_courier_rating = (RatingBar)findViewById(R.id.edit_courier_rating);

        edit_courier_get_photo_from_camenra = (ImageView)findViewById(R.id.edit_courier_get_photo_from_camenra);
        edit_courier_get_photo_from_memory = (ImageView)findViewById(R.id.edit_courier_get_photo_from_memory);

        edit_courier_photo = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.edit_courier_photo);

        edit_courier_list_ads_personal = (ListView)findViewById(R.id.edit_courier_list_ads_personal);

        initDataFromCourier();
    }

    private void initDataFromCourier() {
        edit_courier_name.setText(courier.getNameCourier());
        edit_courier_email.setText(courier.getEmailCourier());
        edit_courier_number_phone.setText(courier.getNumberOfPhone());
        edit_courier_number_draver_root.setText(courier.getNumberOfDriverRoot());
        edit_courier_date_of_birdth.setText(courier.getDateOfBirdth());
        edit_courier_nnumber_card.setText(courier.getNumberOfCard());
        edit_courier_about_courier.setText(courier.getAboutCourier());

        edit_courier_rating.setRating(courier.getRatingCourier());

        Picasso.with(this).load(courier.getImagePathCourier()).into(edit_courier_photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_private_rooms, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new AlertDialog.Builder(PrivateRoomCourier.this)
                .setTitle("Выход")
                .setMessage("Выйти не сохраняя данные?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //выход
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // пользователь отказался от выхода
                dialog.dismiss();
            }
        }).show();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_data_private_room) {
            // тут режим редактирования
            return true;
        }else if (id == R.id.save_data_private_room){
            // тут сохраняем все шо написали
            Courier c = new Courier(courier.getTimeCourierCreate(),edit_courier_email.getText().toString()
            ,edit_courier_name.getText().toString()
            ,edit_courier_rating.getNumStars()
            ,edit_courier_about_courier.getText().toString()
            ,""
            ,edit_courier_number_draver_root.getText().toString()
            ,false
            ,new ArrayList<Ad>()
            ,edit_courier_number_phone.getText().toString(),edit_courier_date_of_birdth.getText().toString()
            ,edit_courier_nnumber_card.getText().toString());

            saveDataAndImageOnDatabase(c);

            Toast.makeText(this,"Данные сохранены",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDataAndImageOnDatabase(final Courier c){
        if (photo!=null){
            String namePhoto = courier.getTimeCourierCreate()+".jpg"; // уникальное имя фото
            StorageReference mountainsRef = mStorageRef.child(namePhoto);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    //reference.child("ads").child(nameChild+"").setValue(ad);
                    c.setImagePathCourier(downloadUrl+"");
                    FirebaseDatabase.getInstance().getReference()
                            .child("couriers").child(c.getTimeCourierCreate()+"")
                            .child("imagePathCourier").setValue(downloadUrl+"");
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("couriers")
                            .child(c.getTimeCourierCreate()+"")
                            .setValue(c);
                }
            });
        }else{

        }

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_courier_get_photo_from_camenra:
                takePhotoFromCamera();
                break;
            case R.id.edit_courier_get_photo_from_memory:
                takePhotoFromMemory();
        }
    }

    private void takePhotoFromMemory() {
        // тут берем фото из памяти
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SELECT_FILE);
    }

    private void takePhotoFromCamera() {
        //тут берем фото с камеры
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.accherniakocich.android.freecourier", photoFile);
                //Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        //Log.d(PrivateRoomCourier.LOG_TAG,"go");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                //Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
                try {
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI); // тут мы получаем полноценное изображение
                    //Log.d(MainActivity.LOG_TAG,"photo image = "+bitmap.getWidth());
                    edit_courier_photo.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                    // So need to Delete the path from DB
                }
            }
        }else if (requestCode==SELECT_FILE){
            Uri selectedImage = data.getData();
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            edit_courier_photo.setImageURI(selectedImage);
        }
    }
}
