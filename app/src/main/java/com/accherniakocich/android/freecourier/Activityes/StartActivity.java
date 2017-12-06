package com.accherniakocich.android.freecourier.Activityes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Admin;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.gson.Gson;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Admin;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.gson.Gson;

public class StartActivity extends AppCompatActivity {


    public static final String LOG_TAG = "MyLogs";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private Button start_activity_log_in, start_activity_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startProgrammWithLoginUser();
        init();

    }

    private void startProgrammWithLoginUser() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json_c = appSharedPrefs.getString("courier", "");
        String json_u = appSharedPrefs.getString("user", "");
        String json_a = appSharedPrefs.getString("admin", "");
        Courier c = gson.fromJson(json_c, Courier.class);
        User u = gson.fromJson(json_u, User.class);
        Admin a = gson.fromJson(json_a, Admin.class);

        if (a != null) {
            Intent intent = new Intent(StartActivity.this, AdminConsolle.class);
            intent.putExtra("admin",a);
            startActivity(intent);
        } else if (c != null) {
            Intent intent = new Intent(StartActivity.this, MainListAdsAndCourier.class);
            intent.putExtra("courier", c);
            startActivity(intent);
        } else if (u != null) {
            Intent intent = new Intent(StartActivity.this, MainListAdsAndCourier.class);
            intent.putExtra("user", u);
            startActivity(intent);
        }
    }

    private void init() {
        start_activity_log_in = (Button) findViewById(R.id.start_activity_log_in);
        start_activity_registration = (Button) findViewById(R.id.start_activity_registration);


        start_activity_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, Log_In.class);
                startActivity(intent);
            }
        });
        start_activity_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }
}