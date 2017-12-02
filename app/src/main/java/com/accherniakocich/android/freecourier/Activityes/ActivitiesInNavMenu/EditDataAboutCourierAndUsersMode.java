package com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.accherniakocich.android.freecourier.Activityes.MainListAdsAndCourier;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;

public class EditDataAboutCourierAndUsersMode extends AppCompatActivity {

    private EditText edit_text_name_courier,edit_text_number_of_phone_courier,edit_text_number_of_driver_root_courier
            ,edit_text_number_of_card_courier,edit_text_name_about_courier,edit_text_date_of_birdth;
    private Button button_edit_data_about_courier_cancel,button_edit_data_about_courier_ok;

    private Courier courier;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_about_courier_and_users_mode);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        courier = (Courier) intent.getSerializableExtra("courier");
        user = (User)intent.getSerializableExtra("user");
        edit_text_number_of_driver_root_courier = (EditText)findViewById(R.id.edit_text_number_of_driver_root_courier);
        edit_text_number_of_card_courier = (EditText)findViewById(R.id.edit_text_number_of_card_courier);
        edit_text_name_about_courier = (EditText)findViewById(R.id.edit_text_name_about_courier);
        edit_text_date_of_birdth = (EditText)findViewById(R.id.edit_text_date_of_birdth);
        button_edit_data_about_courier_cancel = (Button) findViewById(R.id.button_edit_data_about_courier_cancel);
        button_edit_data_about_courier_ok = (Button)findViewById(R.id.button_edit_data_about_courier_ok);
        edit_text_name_courier = (EditText)findViewById(R.id.edit_text_name_courier);
        edit_text_number_of_phone_courier = (EditText)findViewById(R.id.edit_text_number_of_phone_courier);

        if (user!=null){
            // редактироваться пришел юзер
            edit_text_number_of_driver_root_courier.setVisibility(View.INVISIBLE);
            edit_text_number_of_card_courier.setVisibility(View.INVISIBLE);
            edit_text_name_about_courier.setVisibility(View.INVISIBLE);
            edit_text_date_of_birdth.setVisibility(View.INVISIBLE);
        }else if (courier!=null){
            edit_text_name_courier.setText(courier.getNameCourier());
            edit_text_number_of_phone_courier.setText(courier.getNumberOfPhone());
            edit_text_number_of_driver_root_courier.setText(courier.getNumberOfDriverRoot());
            edit_text_number_of_card_courier.setText(courier.getNumberOfCard());
            edit_text_name_about_courier.setText(courier.getAboutCourier());
            edit_text_date_of_birdth.setText(courier.getDateOfBirdth());
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_edit_data_about_courier_cancel:
                new AlertDialog.Builder(EditDataAboutCourierAndUsersMode.this)
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
            case R.id.button_edit_data_about_courier_ok:
                new AlertDialog.Builder(EditDataAboutCourierAndUsersMode.this)
                        .setTitle("Выход")
                        .setMessage("Изменить данные и выйти?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //выход
                                saveDataAndExit();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // пользователь отказался от выхода
                        dialog.dismiss();
                    }
                }).show();
        }
    }

    private void saveDataAndExit() {
        Intent intent = new Intent();
        if (user!=null){
            User u = new User(edit_text_name_courier.getText().toString()
            ,edit_text_number_of_phone_courier.getText().toString()
            ,user.getUserEmail(),user.getListCreatedAds(),user.getDate());
            intent.putExtra("u",u);
            setResult(RESULT_OK,intent);
            finish();
        }else if (courier!=null){
            Courier c = new Courier(courier.getTimeCourierCreate()
                    ,courier.getEmailCourier()
                    ,edit_text_name_courier.getText().toString()
                    ,courier.getRatingCourier()
                    ,edit_text_name_about_courier.getText().toString()
                    ,courier.getImagePathCourier()
                    ,edit_text_number_of_driver_root_courier.getText().toString()
                    ,false
                    ,courier.getListAdCourier()
                    ,edit_text_number_of_phone_courier.getText().toString()
                    ,edit_text_date_of_birdth.getText().toString()
                    ,edit_text_number_of_card_courier.getText().toString());
            intent.putExtra("c",c);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
