package com.accherniakocich.android.freecourier.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Admin;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourierAdapterCheckAdmin extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Courier> objects;
    Admin administrator;
    boolean review;
    String dateTimeAd;
    Ad ad;
    CourierAdapterCheckAdmin adapter;

    int swicher = 0;

    public CourierAdapterCheckAdmin(Context ctx, ArrayList<Courier> objects, Admin administrator, boolean review, String dateAd,Ad ad) {
        lInflater = LayoutInflater.from(ctx);
        this.ad=ad;
        this.dateTimeAd=dateAd;
        this.ctx = ctx;
        this.objects = objects;
        this.administrator = administrator;
        this.review = review;
        this.adapter=this;
    }

    @Override
    public int getCount() {
        return 1;
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_courier_check_admin, parent, false);
        }


        final Courier courier = getAd(position);

        if (courier!=null){
            if (!ad.getCourier().equals("")){
                ((TextView) view.findViewById(R.id.review)).setText("Исполнитель");
                ((TextView) view.findViewById(R.id.item_list_courier_name)).setText(courier.getNameCourier());
                ((TextView) view.findViewById(R.id.item_list_courier_number_of_card)).setText(courier.getNumberOfCard());
                ((TextView) view.findViewById(R.id.item_list_courier_number_of_phone)).setText(courier.getNumberOfPhone());
                ((TextView) view.findViewById(R.id.item_list_courier_date_of_birdth)).setText(courier.getDateOfBirdth());
                ((TextView) view.findViewById(R.id.item_list_courier_about)).setText(courier.getAboutCourier());
                ((TextView) view.findViewById(R.id.item_list_courier_number_of_driver_root)).setText(courier.getNumberOfDriverRoot());
                ((RatingBar) view.findViewById(R.id.item_list_courier_rating_bar)).setRating(courier.getRatingCourier());
                ((RatingBar) view.findViewById(R.id.item_list_courier_rating_bar)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float  rating, boolean fromUser) {
                        Log.d(StartActivity.LOG_TAG,"rating = " + rating);
                        float newRatingCourier = (courier.getRatingCourier()+rating*10)/11;
                        FirebaseDatabase.getInstance().getReference().child("couriers").child(courier.getTimeCourierCreate()+"")
                                .child("ratingCourier").setValue(newRatingCourier);
                        swicher = 1;
                        Toast.makeText(ctx, "Рейтинг выставлен!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                ((TextView) view.findViewById(R.id.review)).setText("Сделать исполнителем");
                ((TextView) view.findViewById(R.id.review)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getTimeAd()+"").child("courier")
                                .setValue(courier.getTimeCourierCreate()+"");
                        Toast.makeText(ctx, "Курьер назначен исполнителем!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if (swicher==0){
            return view;
        }else{
            return null;
        }
    }

    // товар по позиции
    Courier getAd(int position) {
        return ((Courier) getItem(position));
    }
}