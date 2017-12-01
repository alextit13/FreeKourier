package com.accherniakocich.android.freecourier.Adapters;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.Activityes.MainListAdsAndCourier;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;

import java.util.ArrayList;
import java.util.Date;

public class AdAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ad> objects;
    Courier courier;

    public AdAdapter(Context context, ArrayList<Ad> products, Courier c) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        courier = c;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_ad, parent, false);
        }

        final Ad ad = getAd(position);
        ((TextView) view.findViewById(R.id.item_list_ad_name)).setText(ad.getAdName());
        ((TextView) view.findViewById(R.id.item_list_ad_about_ad)).setText(ad.getAboutAd());
        ((TextView) view.findViewById(R.id.item_list_ad_from)).setText(ad.getFrom());
        ((TextView) view.findViewById(R.id.item_list_ad_to)).setText(ad.getTo());
        ((TextView) view.findViewById(R.id.item_list_ad_price)).setText(ad.getPrice()+" .р");
        ((TextView) view.findViewById(R.id.item_list_ad_take_to_work)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Взять в работу")
                        .setMessage("Вы уверены что хотите взять в работу заказ?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //пользаватель взял в работу заказ
                                ArrayList<Ad>listWithAdsCourier = courier.getListAdCourier();
                                listWithAdsCourier.add(objects.get(position));
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // пользователь отказался от заказа
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        ((ImageView) view.findViewById(R.id.image_view_item_main_list_ad_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ctx)
                        .setTitle("О доставке")
                        .setMessage("Заказчик: " + ad.getPeopleNameAd()
                        + "\n\n" +
                        "Дата " + new Date().getTime()
                                + "\n\n" +
                        "О задании: " + ad.getAboutAd()
                                + "\n\n" +
                        "Откуда: " + ad.getFrom()
                                + "\n\n" +
                        "Куда: " + ad.getTo()
                                + "\n\n\n" +
                        "Цена: " + ad.getPrice() + "р.")
                        .setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //пользаватель взял в работу заказ
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }
}