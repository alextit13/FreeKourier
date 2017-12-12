package com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.Adapters.CourierAdapterCheckAdmin;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class PrivateRoomUserReview extends AppCompatActivity {

    private CourierAdapterCheckAdmin adapter;
    private ListView list_private_room_user_review;
    private int position;
    private ArrayList<Courier> list;
    private ArrayList<Ad> listAd;
    private User user;
    private Ad ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_room_user_review);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        list_private_room_user_review = (ListView) findViewById(R.id.list_private_room_user_review);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        user = (User) intent.getSerializableExtra("user");
        ad = (Ad) intent.getSerializableExtra("ad");
        String numberAd = ad.getTimeAd();
        final ArrayList<String> listNumberOfCouriers = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("couriersWitwApp").child(numberAd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // делаем лист из всех номеров курьеров которые подали заявку
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    listNumberOfCouriers.add(data.getValue(String.class));
                }
                downloadListCouriers(listNumberOfCouriers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void downloadListCouriers(ArrayList<String> listCouriers) {

        for (int i = 0; i < listCouriers.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("couriers").child(listCouriers.get(i))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.add(dataSnapshot.getValue(Courier.class));
                            for (int i = 0; i<list.size();i++){
                                if (list.get(i)==null){
                                    list.remove(i);
                                }
                            }
                            if (list.size()>0){
                                adapter = new CourierAdapterCheckAdmin(PrivateRoomUserReview.this, list, null, true, ad.getTimeAd(), ad);
                                list_private_room_user_review.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }
}
