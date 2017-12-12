package com.accherniakocich.android.freecourier.Activityes.Chat;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat extends AppCompatActivity {

    private ListView list_chat;
    private ImageButton image_button_send_message_chat;
    private EditText edit_text_chat;
    private ArrayList<String>listMessage;
    private TextView all_couriers,check_couriers;
    private ArrayList <String> listCouriersCheck;
    private boolean selectCouriers = false;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        adapterRefresh();
    }

    private void init() {
        listCouriersCheck = new ArrayList<>();
        Intent intent = getIntent();
        String fromWhere = intent.getStringExtra("fromWhere");
        edit_text_chat = (EditText)findViewById(R.id.edit_text_chat);
        image_button_send_message_chat = (ImageButton)findViewById(R.id.image_button_send_message_chat);
        if (fromWhere.equals("courier")){
            ((TableRow) findViewById(R.id.chat_admin_container)).setVisibility(View.INVISIBLE);
            ((TableRow) findViewById(R.id.chat_admin_container_check_couriers)).setVisibility(View.INVISIBLE);
        }
        listMessage = new ArrayList<>();

        list_chat = (ListView)findViewById(R.id.list_chat);

        all_couriers = (TextView)findViewById(R.id.all_couriers);
        check_couriers = (TextView)findViewById(R.id.check_couriers);
        all_couriers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_couriers.setBackgroundColor(Color.GREEN);
            }
        });
        check_couriers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Chat.this,ListCouriersForChat.class);
                startActivityForResult(i,9);
                check_couriers.setBackgroundColor(Color.GREEN);
            }
        });

        image_button_send_message_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edit_text_chat.getText().toString().equals("")
                        &&!selectCouriers){
                    long dateMessage = new Date().getTime();
                    FirebaseDatabase.getInstance().getReference().child("chat").child(dateMessage+"")
                            .setValue(edit_text_chat.getText().toString());
                    edit_text_chat.setText("");

                    Toast.makeText(Chat.this, "Сообщение отправлено", Toast.LENGTH_SHORT).show();
                }else if (!edit_text_chat.getText().toString().equals("")
                        &&selectCouriers){
                    if (selectCouriers&&!listCouriersCheck.isEmpty()){
                        if (!edit_text_chat.getText().toString().equals("")){
                            for (int i = 0;i<listCouriersCheck.size();i++){
                                long dateMessage = new Date().getTime();
                                FirebaseDatabase.getInstance().getReference().child("chatForSelectCouriers")
                                        .child(listCouriersCheck.get(i))
                                        .setValue(edit_text_chat.getText().toString());
                            }
                            Toast.makeText(Chat.this, "Сообщение будет отправлено!", Toast.LENGTH_SHORT).show();
                            edit_text_chat.setText("");
                        }
                    }
                }
            }
        });
    }

    private void adapterRefresh() {
        FirebaseDatabase.getInstance().getReference().child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listMessage.add(dataSnapshot.getValue(String.class));
                adapter = new ArrayAdapter(Chat.this,android.R.layout.simple_list_item_1,listMessage);
                list_chat.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("chatForSelectCouriers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listMessage.add(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        if (requestCode==9){
            Log.d(StartActivity.LOG_TAG,"swich = " + selectCouriers);
            selectCouriers = data.getBooleanExtra("swich",false);
            listCouriersCheck = data.getStringArrayListExtra("list");
        }
    }
}
