package com.accherniakocich.android.freecourier.Activityes.Chat;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        adapterRefresh();
    }

    private void init() {
        Intent intent = getIntent();
        String fromWhere = intent.getStringExtra("fromWhere");
        edit_text_chat = (EditText)findViewById(R.id.edit_text_chat);
        image_button_send_message_chat = (ImageButton)findViewById(R.id.image_button_send_message_chat);
        if (fromWhere.equals("courier")){
            ((TableRow) findViewById(R.id.chat_admin_container)).setVisibility(View.INVISIBLE);
        }
        listMessage = new ArrayList<>();

        list_chat = (ListView)findViewById(R.id.list_chat);

        image_button_send_message_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_text_chat.getText().toString().equals("")){
                    long dateMessage = new Date().getTime();
                    FirebaseDatabase.getInstance().getReference().child("chat").child(dateMessage+"")
                            .setValue(edit_text_chat.getText().toString());
                    edit_text_chat.setText("");
                    Toast.makeText(Chat.this, "Сообщение отправлено", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void adapterRefresh() {
        FirebaseDatabase.getInstance().getReference().child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listMessage.add(dataSnapshot.getValue(String.class));
                ArrayAdapter adapter = new ArrayAdapter(Chat.this,android.R.layout.simple_list_item_1,listMessage);
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
    }
}
