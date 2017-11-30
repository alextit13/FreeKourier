package com.accherniakocich.android.freecourier.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;

import java.util.ArrayList;

public class AdAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ad> objects;

    public AdAdapter(Context context, ArrayList<Ad> products) {
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
            view = lInflater.inflate(R.layout.item_list_ad, parent, false);
        }

        Ad ad = getAd(position);
        ((TextView) view.findViewById(R.id.item_list_ad_name)).setText(ad.getAdName());
        ((TextView) view.findViewById(R.id.item_list_ad_about_ad)).setText(ad.getAboutAd());
        ((TextView) view.findViewById(R.id.item_list_ad_from)).setText(ad.getFrom());
        ((TextView) view.findViewById(R.id.item_list_ad_to)).setText(ad.getTo());
        ((TextView) view.findViewById(R.id.item_list_ad_price)).setText(ad.getPrice()+"");
        //((TextView) view.findViewById(R.id.tvText)).setText(ad.getNameJobAd());

        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }
}