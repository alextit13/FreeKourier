package com.accherniakocich.android.freecourier.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourierAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Courier> objects;

    public CourierAdapter(Context context, ArrayList<Courier> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_courier, parent, false);
        }

        Courier courier = getAd(position);
        ((TextView) view.findViewById(R.id.item_list_courier_name)).setText(courier.getNameCourier());
        ((TextView) view.findViewById(R.id.item_list_courier_about)).setText(courier.getAboutCourier());
        ((TextView) view.findViewById(R.id.item_list_courier_number_of_driver_root)).setText(courier.getNumberOfDriverRoot());
        ((TextView) view.findViewById(R.id.item_list_courier_number_of_card)).setText(courier.getNumberOfCard());
        ((TextView) view.findViewById(R.id.item_list_courier_number_of_phone)).setText(courier.getNumberOfPhone());
        ((TextView) view.findViewById(R.id.item_list_courier_date_of_birdth)).setText(courier.getDateOfBirdth());

        ((CheckBox) view.findViewById(R.id.item_list_courier_check_box)).setChecked(false);

        ((RatingBar) view.findViewById(R.id.item_list_courier_rating_bar)).setRating(courier.getRatingCourier());
        CircleImageView CIM = (CircleImageView) view.findViewById(R.id.item_list_courier_image);
        if (!courier.getImagePathCourier().equals("")){
            Picasso.with(ctx).load(courier.getImagePathCourier()).into(CIM);
        }else{
            Picasso.with(ctx).load("http://www.sitechecker.eu/img/not-available.png").into(CIM);
        }

        //((TextView) view.findViewById(R.id.tvText)).setText(ad.getNameJobAd());

        return view;
    }

    // товар по позиции
    Courier getAd(int position) {
        return ((Courier) getItem(position));
    }
}