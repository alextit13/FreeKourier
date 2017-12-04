package com.accherniakocich.android.freecourier.Activityes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddAd extends AppCompatActivity {

    private EditText name_ad,about_ad,from_ad,to_ad,price_ad;
    private Button button_ad_add_cancel,button_ad_add_ok;
    private User user;
    private long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        init();
    }

    private void init() {
        user = (User) getIntent().getSerializableExtra("user");
        name_ad = (EditText)findViewById(R.id.name_ad);
        about_ad = (EditText)findViewById(R.id.about_ad);
        from_ad = (EditText)findViewById(R.id.from_ad);
        to_ad = (EditText)findViewById(R.id.to_ad);
        price_ad = (EditText)findViewById(R.id.price_ad);

        button_ad_add_cancel = (Button)findViewById(R.id.button_ad_add_cancel);
        button_ad_add_ok = (Button)findViewById(R.id.button_ad_add_ok);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_ad_add_cancel:
                new AlertDialog.Builder(AddAd.this)
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
                break;
            case R.id.button_ad_add_ok:
                createAd();
        }
    }

    private void createAd() {
        date = new Date().getTime();
        if (!name_ad.getText().equals("")
                &&!from_ad.getText().equals("")
                &&!to_ad.getText().equals("")
                &&!about_ad.getText().equals("")
                &&!price_ad.getText().equals("")){
            Ad ad = new Ad(user.getUserFIO(),name_ad.getText().toString(),from_ad.getText().toString()
                    ,to_ad.getText().toString(),about_ad.getText().toString(), Integer.parseInt(price_ad.getText().toString())
                    ,date+"",user.getDate()+"");
            saveAndPushAd(ad);
        }else{
            Snackbar.make(button_ad_add_ok,"Заполните все поля", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private void saveAndPushAd(Ad ad) {
        FirebaseDatabase.getInstance().getReference()
                .child("ads")
                .child(date+"")
                .setValue(ad);
        Toast.makeText(this,"Объявление будет активно после проверки модератором",Toast.LENGTH_LONG).show();
        finish();
    }
}
