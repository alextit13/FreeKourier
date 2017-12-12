package com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonalAdsForCourier extends AppCompatActivity {

    private ListView list_view_personal_ads_for_courier;
    private ArrayList<Ad>listAds;
    private User user;
    private AdAdapter adAdapter;
    private Courier courier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_ads_for_courier);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        courier = (Courier) intent.getSerializableExtra("courier");
        listAds = new ArrayList<>();
        list_view_personal_ads_for_courier = (ListView)findViewById(R.id.list_view_personal_ads_for_courier);
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            listAds.add(data.getValue(Ad.class));// сюда загрузили все объявления
                        }
                        createAdapter(listAds);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void createAdapter(final ArrayList<Ad> listAd) {
        final ArrayList<Ad>finalList = new ArrayList<>();//тут все объявы которые нужно добавлять в лист в кабинете юзера

        for (int i = 0; i<listAd.size();i++){
            if (listAd.get(i).getUsersTime().equals(user.getDate()+"")){
                finalList.add(listAd.get(i));
            }
        }
        adAdapter = new AdAdapter(this,finalList,null);
        list_view_personal_ads_for_courier.setAdapter(adAdapter);
        list_view_personal_ads_for_courier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("ad", listAd.get(position));
                intent.putExtra("courier", courier);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
