package com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class PrivateRoomUser extends AppCompatActivity {

    private User user;
    private TextView edit_user_name,edit_user_email,edit_user_number_phone;
    private ListView edit_user_list_ads_created;
    private ArrayList<String>listStringWithDatesAds;
    private ArrayList<Ad>listAds;
    private AdAdapter adAdapter;

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
        downloadDataFromServer(user);
        takeNumberCouriersForOneAd();
    }

    private void takeNumberCouriersForOneAd() {
        final ArrayList<String>listStringAdd = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("couriersWitwApp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    //Log.d(StartActivity.LOG_TAG,"data = " + data.toString());
                    listStringAdd.add(data.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void downloadDataFromServer(User user) {
        listStringWithDatesAds = new ArrayList<>();
        listAds = new ArrayList<>();
        /*FirebaseDatabase.getInstance().getReference().child("couriersWitwApp").child(user.getDate()+"")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            listStringWithDatesAds.add(data.getValue(String.class));// сюда загрузили все даты создания добавленных объявлений
                        }
                        Log.d(StartActivity.LOG_TAG,"1 = " + listStringWithDatesAds.size());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            listAds.add(data.getValue(Ad.class));// сюда загрузили все объявления
                        }
                        Log.d(StartActivity.LOG_TAG,"1 = " + listAds.size());
                        createAdapter(listAds);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void createAdapter(ArrayList<Ad> listAd) {
        final ArrayList<Ad>finalList = new ArrayList<>();//тут все объявы которые нужно добавлять в лист в кабинете юзера

        for (int i = 0; i<listAd.size();i++){
           if (listAd.get(i).getUsersTime().equals(user.getDate()+"")){
               finalList.add(listAd.get(i));
           }
        }
        adAdapter = new AdAdapter(this,finalList,null);
        edit_user_list_ads_created.setAdapter(adAdapter);

        edit_user_list_ads_created.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(PrivateRoomUser.this)
                        .setTitle("Удалить заказ")
                        .setMessage("Вы уверены что хотите удалить заказ?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //пользаватель взял в работу заказ
                                //ArrayList<Ad>listWithAdsCourier = courier.getListAdCourier();
                                //listWithAdsCourier.add(objects.get(position));
                                long dateAddAdInList = new Date().getTime();
                                FirebaseDatabase.getInstance().getReference()
                                        .child("ads")
                                        .child(finalList.get(i).getTimeAd())
                                        .removeValue();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // пользователь отказался от заказа
                        dialog.dismiss();
                    }
                }).show();
                adAdapter.notifyDataSetChanged();
                Toast.makeText(PrivateRoomUser.this, "Объявление будет удалено", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
