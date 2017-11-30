package com.accherniakocich.android.freecourier.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;

import java.util.ArrayList;

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

        Courier courier = new Courier();
        ((TextView) view.findViewById(R.id.item_list_courier_name)).setText(courier.getNameCourier());
        ((TextView) view.findViewById(R.id.item_list_courier_about)).setText(courier.getAboutCourier());
        ((CheckBox) view.findViewById(R.id.item_list_courier_check_box)).setChecked(false);
        //((RatingBar) view.findViewById(R.id.item_list_courier_rating_bar)).setRating(0.7f);

        //((TextView) view.findViewById(R.id.tvText)).setText(ad.getNameJobAd());

        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }
}