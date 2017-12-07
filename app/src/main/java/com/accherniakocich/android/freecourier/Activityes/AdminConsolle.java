package com.accherniakocich.android.freecourier.Activityes;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.accherniakocich.android.freecourier.Fragments.FragmentAds;
import com.accherniakocich.android.freecourier.Fragments.FragmentCouriers;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Admin;
import com.google.gson.Gson;
import java.util.ArrayList;

public class AdminConsolle extends AppCompatActivity implements FragmentAds.onSomeEventListener {

    private FragmentTransaction fTrans;
    private FragmentAds fragmentAds;
    private FragmentCouriers fragmentCouriers;
    private TextView textView_ads;
    private TextView textView_couriers;
    private ArrayList<Ad>listWithAds;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_consolle);
        checkSavedAdmin();
        init();
    }

    private void init() {
        listWithAds = new ArrayList<>();
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
            appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            prefsEditor = appSharedPrefs.edit();
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
            case R.id.action_sign_out:
                //выход
                new AlertDialog.Builder(AdminConsolle.this)
                        .setTitle("Выход")
                        .setMessage("Вы уверены что хотите выйти?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //выход
                                SharedPreferences appSharedPrefs = PreferenceManager
                                        .getDefaultSharedPreferences(getApplicationContext());
                                appSharedPrefs.edit().clear().apply();
                                finish();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // пользователь отказался от выхода
                        dialog.dismiss();
                    }
                }).show();
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

    @Override
    public void someEvent(ArrayList<Ad>list) {
        //Fragment frag1 = getFragmentManager().findFragmentById(R.id.fragment_ads_admin);
        listWithAds = list;

    }
}