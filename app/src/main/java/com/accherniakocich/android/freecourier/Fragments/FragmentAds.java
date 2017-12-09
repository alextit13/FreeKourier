package com.accherniakocich.android.freecourier.Fragments;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    private ProgressBar progress_bar_ad_fragment;

    public interface onSomeEventListener {
        public void someEvent(ArrayList<Ad>list);
    }

    onSomeEventListener someEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ads,null);
        fragment_ads_admin = (ListView)v.findViewById(R.id.fragment_ads_admin);
        progress_bar_ad_fragment = (ProgressBar)v.findViewById(R.id.progress_bar_ad_fragment);
        takeDataFromServer();

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    private void takeDataFromServer() {
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    list.add(data.getValue(Ad.class));
                }
                ArrayList<Ad>finalList = new ArrayList<>();
                for (int i = 0;i<list.size();i++){
                    if (!list.get(i).isCheckAdmin()){
                        finalList.add(list.get(i));
                    }
                }
                //Log.d(StartActivity.LOG_TAG,"listSize = " + list.size());
                if (getActivity()!=null){
                    adAdapter = new AdAdapter(getActivity(),finalList,null,"admin");
                    fragment_ads_admin.setAdapter(adAdapter);
                    progress_bar_ad_fragment.setVisibility(View.INVISIBLE);
                    someEventListener.someEvent(finalList); // это и интерфейс не нужные вещи
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}