package com.accherniakocich.android.freecourier.Activityes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.Adapters.CourierAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

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

        content_main_list_ads_progress_bar = (ProgressBar)findViewById(R.id.content_main_list_ads_progress_bar);
        content_main_list_ads_progress_bar.setVisibility(View.VISIBLE);
        mainListAdsCourier_list_view = (ListView)findViewById(R.id.mainListAds_list_view);

        //лист с объявлениями
        /*reference = FirebaseDatabase.getInstance().getReference().child("ads");
        listAd = new ArrayList<>();
        mGettingAdsList(reference);*/


        //лист с курьерами
        reference = FirebaseDatabase.getInstance().getReference().child("couriers");

        listCourier = new ArrayList<>();
        mGettingCourierList(reference); // ТУТ НУЖНО ВКЛЮЧИТЬ ПОТОМ НЕ ЗАБЫТЬ!!!!!!!
    }

    private void mGettingCourierList(DatabaseReference ref) {
        final ArrayList<Courier>list = new ArrayList<>();
        //Log.d(StartActivity.LOG_TAG,"start download");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    //Log.d(StartActivity.LOG_TAG,"data = " + data.toString());
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
        courierAdapter = new CourierAdapter(MainListAdsAndCourier.this, list);
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
        adAdapter = new AdAdapter(MainListAdsAndCourier.this, list);
        mainListAdsCourier_list_view.setAdapter(adAdapter);
        content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);
    }

    /*private void methFortest() {
        listCourier = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("couriers");

        for (int i = 0; i<100;i++){
            long time = new Date().getTime();
            Courier courier = new Courier("Иванов Иван Петрович", 46,"Завезем везде быстро и недорого","https://tinyclipart.com/resource/man/man-15.jpg",
                    "D8873",false,new ArrayList<Ad>(),"+552845651","04.5.1987","7654 7650 7643 8761");
            reference.child(time+"").setValue(courier);
        }
        //adAdapter = new AdAdapter(MainListAdsAndCourier.this, listAd);
        //mainListAdsCourier_list_view.setAdapter(adAdapter);
    }*/

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
