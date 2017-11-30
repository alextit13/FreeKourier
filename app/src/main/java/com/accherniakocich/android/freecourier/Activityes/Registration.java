package com.accherniakocich.android.freecourier.Activityes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    private FrameLayout registration_container;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText registration_ET_email,registration_ET_password;
    private RadioGroup registration_RG_check_group;
    private RadioButton registration_RB_customer,registration_RB_courier;
    private Button registration_button_cancel,registration_button_ok;

    private ProgressBar registration_progress_bar;
    private LinearLayout container_courier_data;
    private LinearLayout.LayoutParams layoutParams;

    //для регистрации курьера
    private EditText registration_courier_FIO, registration_courier_number_of_phone,registration_courier_date_of_birdth
            ,registration_courier_number_of_driver_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        container_courier_data = (LinearLayout) findViewById(R.id.container_courier_data);
        layoutParams = (LinearLayout.LayoutParams) container_courier_data.getLayoutParams();
        layoutParams.height = 0;
        container_courier_data.setLayoutParams(layoutParams);

        registration_ET_email = (EditText)findViewById(R.id.registration_ET_email);
        registration_ET_password = (EditText)findViewById(R.id.registration_ET_password);

        registration_RG_check_group = (RadioGroup)findViewById(R.id.registration_RG_check_group);

        registration_RB_customer = (RadioButton)findViewById(R.id.registration_RB_customer);
        registration_RB_courier = (RadioButton)findViewById(R.id.registration_RB_courier);

        registration_container = (FrameLayout)findViewById(R.id.registration_container);


        registration_RG_check_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Log.d(StartActivity.LOG_TAG, "group = " + i+", id = " + registration_RB_courier.getId());
                if (i==registration_RB_courier.getId()){
                    // курьер
                    layoutParams.height = 700;
                    container_courier_data.setLayoutParams(layoutParams);
                }else{
                    // пользователь
                    layoutParams.height = 0;
                    container_courier_data.setLayoutParams(layoutParams);
                }
            }
        });

        registration_courier_FIO = (EditText)findViewById(R.id.registration_courier_FIO);
        registration_courier_number_of_phone = (EditText)findViewById(R.id.registration_courier_number_of_phone);
        registration_courier_date_of_birdth = (EditText)findViewById(R.id.registration_courier_date_of_birdth);
        registration_courier_number_of_driver_root = (EditText)findViewById(R.id.registration_courier_number_of_driver_root);

        registration_button_cancel = (Button)findViewById(R.id.registration_button_cancel);
        registration_button_ok = (Button)findViewById(R.id.registration_button_ok);

        registration_progress_bar = (ProgressBar)findViewById(R.id.registration_progress_bar);
        registration_progress_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        try {
            Log.d(StartActivity.LOG_TAG,currentUser.getDisplayName());
        }catch (Exception e){
            Log.d(StartActivity.LOG_TAG,"нету пользователя");
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registration_button_cancel:
                finish();
                break;
            case R.id.registration_button_ok:
                if (!registration_ET_email.getText().toString().equals("")
                        &&!registration_ET_password.getText().toString().equals("")){
                    registration_container.setAlpha(0.3f);
                    registration_progress_bar.setVisibility(View.VISIBLE);
                    registration(registration_ET_email.getText().toString(),registration_ET_password.getText().toString());
                }else{
                    Snackbar.make(registration_button_ok,"Введите данные", BaseTransientBottomBar.LENGTH_LONG).show();
                }
        }
    }

    private void registration(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()){
                    if (registration_RB_courier.isChecked()){ // регистрация курьера

                        Log.d(StartActivity.LOG_TAG,"task = " + task.getException());
                        /*Intent intent;
                        String nickName = email.substring(0,email.indexOf("@",0));
                        //intent = new Intent(Registration.this,MainListCouriers.class); // регистрация как пользователь
                        intent = new Intent(Registration.this,MainListAdsAndCourier.class); // регистрация как курьер

                        User user = new User(
                                registration_courier_FIO.getText().toString()
                                ,Integer.parseInt(registration_courier_number_of_phone.getText().toString())
                                ,registration_courier_date_of_birdth.getText().toString()
                                ,registration_courier_number_of_driver_root.getText().toString()
                                ,registration_ET_email.getText().toString()
                                ,nickName
                                ,0
                        );
                        intent.putExtra("user",user);
                        startActivity(intent);*/
                    }

                }else{
                    Toast.makeText(Registration.this, "Регистрация неудачна", Toast.LENGTH_SHORT).show();
                    registration_progress_bar.setVisibility(View.INVISIBLE);
                    registration_container.setAlpha(1f);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        //FirebaseAuth.getInstance().signOut();
        super.onDestroy();
    }
}
