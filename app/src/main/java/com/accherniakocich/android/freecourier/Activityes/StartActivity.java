package com.accherniakocich.android.freecourier.Activityes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.accherniakocich.android.freecourier.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {


    public static final String LOG_TAG = "MyLogs";

    private Button start_activity_log_in,start_activity_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();
    }

    private void init() {
        start_activity_log_in = (Button)findViewById(R.id.start_activity_log_in);
        start_activity_registration = (Button)findViewById(R.id.start_activity_registration);



        start_activity_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,Log_In.class);
                startActivity(intent);
            }
        });
        start_activity_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,Registration.class);
                startActivity(intent);
            }
        });
    }
}
