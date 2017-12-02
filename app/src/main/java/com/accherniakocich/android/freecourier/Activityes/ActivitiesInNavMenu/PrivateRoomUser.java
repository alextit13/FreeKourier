package com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class PrivateRoomUser extends AppCompatActivity {

    private User user;

    private TextView edit_user_name,edit_user_email,edit_user_number_phone;
    private ListView edit_user_list_ads_created;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_room_user);
        init();
    }

    private void init() {

        user = (User) getIntent().getSerializableExtra("user");

        edit_user_name = (TextView)findViewById(R.id.edit_user_name);
        edit_user_email = (TextView)findViewById(R.id.edit_user_email);
        edit_user_number_phone = (TextView)findViewById(R.id.edit_user_number_phone);

        edit_user_list_ads_created = (ListView)findViewById(R.id.edit_user_list_ads_created);
        initDataFromUser();
    }

    private void initDataFromUser(){
        edit_user_name.setText(user.getUserFIO());
        edit_user_email.setText(user.getUserEmail());
        edit_user_number_phone.setText(user.getUserNumberPhone());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_private_rooms, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_data_private_room) {
            // тут режим редактирования
            editorMode();
            return true;
        }else if (id == R.id.save_data_private_room){
            // тут сохраняем все шо написали
            User u = new User(edit_user_name.getText().toString()
            ,edit_user_number_phone.getText().toString()
            ,user.getUserEmail()
            ,user.getListCreatedAds()
            ,user.getDate());

            saveDataAndImageOnDatabase(u);

            Toast.makeText(this,"Данные сохранены",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDataAndImageOnDatabase(User u) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(u.getDate()+"")
                .setValue(u);
    }

    private void editorMode() {
        Intent intent = new Intent(PrivateRoomUser.this,EditDataAboutCourierAndUsersMode.class);
        intent.putExtra("user",user);
        startActivityForResult(intent,3); // реквест код для изменения данных 3
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3){
            if (data == null) {
                return;
            }else{
                User u = (User) data.getSerializableExtra("u");
                user = u;
                setDataInSharedPreferences(user);
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(user.getDate()+"")
                        .setValue(user);
                Toast.makeText(this, "Данные изменятся через несколько минут", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setDataInSharedPreferences(User userNewFromEditMode) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userNewFromEditMode);
        prefsEditor.putString("user", json);
        prefsEditor.commit();
    }
}
