package com.accherniakocich.android.freecourier.Activityes;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu.PrivateRoomCourier;
import com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu.PrivateRoomUser;
import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.Adapters.CourierAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainListAdsAndCourier extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar content_main_list_ads_progress_bar;
    private ListView mainListAdsCourier_list_view;
    private AdAdapter adAdapter;
    private ArrayList<Ad> listAd;
    private ArrayList<Courier>listCourier;
    private CourierAdapter courierAdapter;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private int SWICH_start_list_position = 1;
    private int SWICH_end_list_position = 20;
    private Courier courier;
    private User user;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_ads);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //добавление объявления
                Intent intent = new Intent(MainListAdsAndCourier.this,AddAd.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        init();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {

        content_main_list_ads_progress_bar = (ProgressBar)findViewById(R.id.content_main_list_ads_progress_bar);
        content_main_list_ads_progress_bar.setVisibility(View.VISIBLE);
        mainListAdsCourier_list_view = (ListView)findViewById(R.id.mainListAds_list_view);

        Intent intent = getIntent();
        courier = (Courier) intent.getSerializableExtra("courier");
        user = (User)intent.getSerializableExtra("user");
        if (courier!=null){
            // вошел курьер. Показываем лист с объявлениями
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(courier);
            prefsEditor.putString("courier", json);
            prefsEditor.commit();

            deleteFloatingActionButton();
            reference = FirebaseDatabase.getInstance().getReference().child("ads");
            listAd = new ArrayList<>();
            mGettingAdsList(reference);
        }else if (user!=null){
            // вошел пользователь, показываем курьеров
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            prefsEditor.putString("user", json);
            prefsEditor.commit();

            reference = FirebaseDatabase.getInstance().getReference().child("couriers");

            listCourier = new ArrayList<>();
            mGettingCourierList(reference);
        }
    }

    private void deleteFloatingActionButton() {
        fab.setVisibility(View.INVISIBLE);
    }

    private void mGettingCourierList(DatabaseReference ref) {
        final ArrayList<Courier>list = new ArrayList<>();
        //Log.d(StartActivity.LOG_TAG,"start download");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Log.d(StartActivity.LOG_TAG,"data = " + data.toString());
                    list.add(data.getValue(Courier.class));
                }
                //Log.d(StartActivity.LOG_TAG,"data = " + list.size());
                adapterCOURIERstart(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void adapterCOURIERstart(ArrayList<Courier> list) {
        courierAdapter = new CourierAdapter(MainListAdsAndCourier.this, list,null,false,"");
        mainListAdsCourier_list_view.setAdapter(courierAdapter);
        content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);
    }

    private void mGettingAdsList(DatabaseReference ref) {
        final ArrayList<Ad>list = new ArrayList<>();
        //Log.d(StartActivity.LOG_TAG,"start download");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    //Log.d(StartActivity.LOG_TAG,"data = " + data.toString());
                    list.add(data.getValue(Ad.class));
                }
                adapterADstart(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void adapterADstart(ArrayList<Ad> list) {
        ArrayList<Ad>finalList = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            if (list.get(i).isCheckAdmin()){
                finalList.add(list.get(i));
            }
        }
        adAdapter = new AdAdapter(MainListAdsAndCourier.this, finalList,courier);
        mainListAdsCourier_list_view.setAdapter(adAdapter);
        content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        courier = null;
        user = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new AlertDialog.Builder(MainListAdsAndCourier.this)
                .setTitle("Выход")
                .setMessage("Вы уверены что хотите выйти?")
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
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_list_ads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_refresh){
            content_main_list_ads_progress_bar.setVisibility(View.VISIBLE);
            if (courier!=null){
                mGettingAdsList(FirebaseDatabase.getInstance().getReference().child("ads"));
            }else if (user!=null){
                mGettingCourierList(FirebaseDatabase.getInstance().getReference().child("couriers"));
            }
            content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ApplySharedPref")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.private_room) {
            if (courier!=null){ // если вошел курьер
                Intent intent = new Intent(MainListAdsAndCourier.this,PrivateRoomCourier.class);
                intent.putExtra("courier",courier);
                startActivity(intent);
            }else if (user!=null){ // если вошел пользователь
                Intent intent = new Intent(MainListAdsAndCourier.this, PrivateRoomUser.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }

            // Handle the camera action
        } else if (id == R.id.exit) {
            new AlertDialog.Builder(MainListAdsAndCourier.this)
                    .setTitle("Выход")
                    .setMessage("Вы уверены что хотите выйти?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //пользаватель взял в работу заказ
                            dialog.dismiss();
                            SharedPreferences appSharedPrefs = PreferenceManager
                                    .getDefaultSharedPreferences(getApplicationContext());
                            appSharedPrefs
                                    .edit()
                                    .clear()
                                    .commit();
                            finish();
                        }
                    }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // пользователь отказался от заказа
                    dialog.dismiss();
                }
            }).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
