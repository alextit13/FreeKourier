package com.accherniakocich.android.freecourier.Activityes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.Adapters.CourierAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;

import java.util.ArrayList;

public class MainListAdsAndCourier extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mainListAdsCourier_list_view;
    private AdAdapter adAdapter;
    private ArrayList<Ad> listAd;
    private ArrayList<Courier>listCourier;
    private CourierAdapter courierAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_ads);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {

        //лист с объявлениями
        /*listAd = new ArrayList<>();
        mainListAdsCourier_list_view = (ListView)findViewById(R.id.mainListAds_list_view);
        for (int i = 0; i<100;i++){
            Ad ad = new Ad("Перевезти всякие вещи и что там еще что есть", "Москва, ул. Ленина,25 и вооще где только я живу оттуда и везти","Москва, ул. Чкалова, 9 куда завезете это ваше дело, не мне вас судить","Нужно перевезти коробку вообще там много всего всякого. Плачу копейки и больше ни чучуть даже",50);
            listAd.add(ad);
        }
        adAdapter = new AdAdapter(MainListAdsAndCourier.this, listAd);
        mainListAdsCourier_list_view.setAdapter(adAdapter);*/



        //лист с курьерами
        listCourier = new ArrayList<>();
        mainListAdsCourier_list_view = (ListView)findViewById(R.id.mainListAds_list_view);
        for (int i = 0; i<50;i++){
            Courier courier = new Courier("Иванов Иван Петрович", 46,"Завезем везде быстро и недорого","https://tinyclipart.com/resource/man/man-15.jpg"
            ,false,new ArrayList<Ad>());
            listCourier.add(courier);
        }
        courierAdapter = new CourierAdapter(MainListAdsAndCourier.this, listCourier);
        mainListAdsCourier_list_view.setAdapter(courierAdapter);

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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
