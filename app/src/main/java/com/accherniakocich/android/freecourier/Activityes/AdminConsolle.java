package com.accherniakocich.android.freecourier.Activityes;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.accherniakocich.android.freecourier.Fragments.FragmentAds;
import com.accherniakocich.android.freecourier.Fragments.FragmentCouriers;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class AdminConsolle extends AppCompatActivity {

    private FragmentTransaction fTrans;
    private FragmentAds fragmentAds;
    private FragmentCouriers fragmentCouriers;
    private TextView textView_ads;
    private TextView textView_couriers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_consolle);
        checkSavedAdmin();
        init();
    }

    private void init() {
        fragmentAds = new FragmentAds();
        fragmentCouriers = new FragmentCouriers();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.admin_container, fragmentAds);
        textView_ads = (TextView) findViewById(R.id.admin_ads);
        textView_couriers = (TextView) findViewById(R.id.admin_couriers);
        textView_ads.setBackgroundColor(0xFFaac2ad); // темнее
        textView_couriers.setBackgroundColor(0xFFb9c2ba); // светлее
        fTrans.commit();
    }

    private void checkSavedAdmin() {
        Intent intent = getIntent();
        Admin admin = (Admin) intent.getSerializableExtra("admin");
        if (admin == null) {
            Admin a = new Admin();
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(a);
            prefsEditor.putString("admin", json);
            prefsEditor.commit();
        }
    }

    public void onClick(View view) {
        fTrans = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.admin_ads:
                fTrans.replace(R.id.admin_container, fragmentAds);
                textView_ads.setBackgroundColor(0xFFaac2ad); // темнее
                textView_couriers.setBackgroundColor(0xFFb9c2ba); // светлее
                break;
            case R.id.admin_couriers:
                fTrans.replace(R.id.admin_container, fragmentCouriers);
                textView_ads.setBackgroundColor(0xFFb9c2ba); // светлее
                textView_couriers.setBackgroundColor(0xFFaac2ad); // темнее
                break;
            default:
                break;
        }
        fTrans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //настройки
                break;
            case R.id.action_delete:
                // удаляем выбранных курьеров
                break;
            case R.id.action_info:
                // инфо
                break;
            case R.id.action_check:
                // подтверждаем отправку объявления
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
