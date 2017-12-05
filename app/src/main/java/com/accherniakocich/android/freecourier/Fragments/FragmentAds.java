package com.accherniakocich.android.freecourier.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentAds extends Fragment{

    private ArrayList<Ad>list;
    private ListView fragment_ads_admin;
    private AdAdapter adAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ads,null);
        fragment_ads_admin = (ListView)v.findViewById(R.id.fragment_ads_admin);
        takeDataFromServer();

        return v;
    }

    private void takeDataFromServer() {
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    list.add(data.getValue(Ad.class));
                }
                //Log.d(StartActivity.LOG_TAG,"listSize = " + list.size());
                adAdapter = new AdAdapter(getActivity(),list,null);
                fragment_ads_admin.setAdapter(adAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
