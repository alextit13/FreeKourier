package com.accherniakocich.android.freecourier.Activityes;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.accherniakocich.android.freecourier.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText registration_ET_email,registration_ET_password;
    private RadioGroup registration_RG_check_group;
    private RadioButton registration_RB_customer,registration_RB_courier;
    private Button registration_button_cancel,registration_button_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                }
            }
            //...
        };
    }

    private void init() {
        registration_ET_email = (EditText)findViewById(R.id.registration_ET_email);
        registration_ET_password = (EditText)findViewById(R.id.registration_ET_password);

        registration_RG_check_group = (RadioGroup)findViewById(R.id.registration_RG_check_group);

        registration_RB_customer = (RadioButton)findViewById(R.id.registration_RB_customer);
        registration_RB_courier = (RadioButton)findViewById(R.id.registration_RB_courier);

        registration_button_cancel = (Button)findViewById(R.id.registration_button_cancel);
        registration_button_ok = (Button)findViewById(R.id.registration_button_ok);
    }
}
