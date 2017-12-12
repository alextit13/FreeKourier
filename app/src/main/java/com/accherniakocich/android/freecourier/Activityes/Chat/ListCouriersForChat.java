package com.accherniakocich.android.freecourier.Activityes.Chat;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ListCouriersForChat extends AppCompatActivity {

    private ListView list_with_couriers_chat;
    private ArrayList <String> listCouriers;
    private ArrayList <String>list;
    private Button button_ok_couriers_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_couriers_for_chat);
        init();
    }

    private void init() {
        button_ok_couriers_chat = (Button)findViewById(R.id.button_ok_couriers_chat);
        list = new ArrayList<>();
        listCouriers = new ArrayList<>();
        list_with_couriers_chat = (ListView)findViewById(R.id.list_with_couriers_chat);
        formatedList();
        list_with_couriers_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listCouriers.add(list.get(position));
                view.setBackgroundColor(Color.GREEN);
            }
        });

        button_ok_couriers_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("list", listCouriers);
                intent.putExtra("swich", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void formatedList() {
        FirebaseDatabase.getInstance().getReference().child("couriers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    list.add(data.getValue(Courier.class).getNameCourier());
                }
                ArrayAdapter adapter = new ArrayAdapter(ListCouriersForChat.this,android.R.layout.simple_list_item_1,list);
                list_with_couriers_chat.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
