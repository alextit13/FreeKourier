package com.accherniakocich.android.freecourier.Fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.Adapters.CourierAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Admin;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentCouriers extends Fragment{

    private ArrayList<Courier> list;
    private ListView fragment_ads_admin;
    private CourierAdapter courierAdapter;
    private ProgressBar progress_bar_courier_fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_couriers,null);
        fragment_ads_admin = (ListView)v.findViewById(R.id.fragment_courier_admin);
        progress_bar_courier_fragment = (ProgressBar)v.findViewById(R.id.progress_bar_courier_fragment);
        takeDataFromServer();
        return v;
    }
    private void takeDataFromServer() {
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("couriers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    list.add(data.getValue(Courier.class));
                }
                //Log.d(StartActivity.LOG_TAG,"listSize = " + list.size());
                courierAdapter = new CourierAdapter(getActivity(),list,new Admin(),false,"");
                fragment_ads_admin.setAdapter(courierAdapter);
                progress_bar_courier_fragment.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}