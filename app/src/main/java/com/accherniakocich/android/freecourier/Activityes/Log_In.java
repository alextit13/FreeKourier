package com.accherniakocich.android.freecourier.Activityes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Log_In extends AppCompatActivity {

    private FrameLayout container_log_in_frame_layout;
    private ProgressBar progress_bar_log_in;
    private EditText log_in_edit_text_email,log_in_edit_text_password;
    private Button log_id_button_ok, log_id_button_cancel;
    private RadioButton radio_button_log_in_courier,radio_button_log_in_customer;
    private RadioGroup radio_group_log_in;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);
        init();

    }

    private void init() {
        container_log_in_frame_layout = (FrameLayout)findViewById(R.id.container_log_in_frame_layout);

        progress_bar_log_in = (ProgressBar)findViewById(R.id.progress_bar_log_in);
        progress_bar_log_in.setVisibility(View.INVISIBLE);

        log_in_edit_text_email = (EditText)findViewById(R.id.log_in_edit_text_email);
        log_in_edit_text_password = (EditText)findViewById(R.id.log_in_edit_text_password);

        log_id_button_ok = (Button)findViewById(R.id.log_id_button_ok);
        log_id_button_cancel = (Button)findViewById(R.id.log_id_button_cancel);

        radio_group_log_in = (RadioGroup)findViewById(R.id.radio_group_log_in);

        radio_button_log_in_courier = (RadioButton)findViewById(R.id.radio_button_log_in_courier);
        radio_button_log_in_customer = (RadioButton)findViewById(R.id.radio_button_log_in_customer);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.log_id_button_cancel:
                finish();
                break;
            case R.id.log_id_button_ok:
                container_log_in_frame_layout.setAlpha(.3f);
                progress_bar_log_in.setVisibility(View.VISIBLE);
                log_id_button_ok.setClickable(false);
                log_id_button_cancel.setClickable(false);
                if (log_in_edit_text_email.getText().toString().equals("admin@admin.com")&&
                        log_in_edit_text_password.getText().toString().equals("admin123")&&
                        radio_button_log_in_courier.isChecked()){
                    logIn(log_in_edit_text_email.getText().toString(),log_in_edit_text_password.getText().toString(),true);
                }
                if (!log_in_edit_text_email.getText().toString().equals("")&&!log_in_edit_text_password.getText().toString().equals("")
                        &&(radio_button_log_in_courier.isChecked()||radio_button_log_in_customer.isChecked())){
                    logIn(log_in_edit_text_email.getText().toString(),log_in_edit_text_password.getText().toString(),radio_button_log_in_courier.isChecked());
                }else{
                    Snackbar.make(log_id_button_ok,"Укажите данные", BaseTransientBottomBar.LENGTH_LONG).show();
                    progress_bar_log_in.setVisibility(View.INVISIBLE);
                    container_log_in_frame_layout.setAlpha(1f);
                    log_id_button_ok.setClickable(true);
                    log_id_button_cancel.setClickable(true);
                }
        }
    }

    private void logIn(String s, String s1, final boolean checked) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(s, s1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();
                            if (checked){
                                final ArrayList<Courier>list = new ArrayList<>();
                                FirebaseDatabase.getInstance().getReference()
                                        .child("couriers")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot data: dataSnapshot.getChildren()) {
                                                    list.add(data.getValue(Courier.class));
                                                }
                                                for (int i = 0; i<list.size();i++){
                                                    if (user.getEmail().equals(list.get(i).getEmailCourier())){
                                                        Intent intent = new Intent(Log_In.this,MainListAdsAndCourier.class);
                                                        intent.putExtra("courier",list.get(i));
                                                        startActivity(intent);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                            }else{
                                final ArrayList<User>list = new ArrayList<>();
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot data: dataSnapshot.getChildren()) {
                                                    list.add(data.getValue(User.class));
                                                }
                                                for (int i = 0; i<list.size();i++){
                                                    if (user.getEmail().equals(list.get(i).getUserEmail())){
                                                        Intent intent = new Intent(Log_In.this,MainListAdsAndCourier.class);
                                                        intent.putExtra("user",list.get(i));
                                                        startActivity(intent);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                            }if (log_in_edit_text_email.getText().toString().equals("admin@admin.com")){
                                Intent intent = new Intent(Log_In.this,AdminConsolle.class);
                                startActivity(intent);
                            }
                        } else {
                            progress_bar_log_in.setVisibility(View.INVISIBLE);
                            container_log_in_frame_layout.setAlpha(1f);
                            log_id_button_ok.setClickable(true);
                            log_id_button_cancel.setClickable(true);
                            Snackbar.make(log_id_button_ok,"Ошибка входа", BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
