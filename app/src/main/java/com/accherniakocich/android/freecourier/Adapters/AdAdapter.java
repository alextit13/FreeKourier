package com.accherniakocich.android.freecourier.Adapters;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.Activityes.MainListAdsAndCourier;
import com.accherniakocich.android.freecourier.Activityes.StartActivity;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ad> objects;
    Courier courier;
    String fromWhere;

    public AdAdapter(Context context, ArrayList<Ad> products, Courier c) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        courier = c;
    }

    public AdAdapter(Context context, ArrayList<Ad> products, Courier c, String from) {
        fromWhere = from;
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

        if (ad.getCourier().equals(courier.getTimeCourierCreate()+"")){
            ((TextView) view.findViewById(R.id.item_list_ad_take_to_work)).setText("Вы приняты исполнителем");
            ((LinearLayout)view.findViewById(R.id.container_Ad_adapter)).setBackgroundColor(0xFFaac2ad);
        }

        ((TextView) view.findViewById(R.id.item_list_ad_name)).setText(ad.getAdName());
        ((TextView) view.findViewById(R.id.item_list_ad_about_ad)).setText(ad.getAboutAd());
        ((TextView) view.findViewById(R.id.item_list_ad_from)).setText(ad.getFrom());
        ((TextView) view.findViewById(R.id.item_list_ad_to)).setText(ad.getTo());
        ((TextView) view.findViewById(R.id.item_list_ad_price)).setText(ad.getPrice()+" .р");
        if (courier==null){

            ((TextView) view.findViewById(R.id.item_list_ad_take_to_work)).setText("Удалить");
            (view.findViewById(R.id.item_list_ad_take_to_work)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(ctx)
                            .setTitle("Удалить заявку")
                            .setMessage("Вы уверены что хотите удалить заказ?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //пользаватель взял в работу заказ
                                    //ArrayList<Ad>listWithAdsCourier = courier.getListAdCourier();
                                    //listWithAdsCourier.add(objects.get(position));
                                    Log.d(StartActivity.LOG_TAG,"click");
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("ads")
                                            .child(objects.get(position).getTimeAd() + "")
                                            .removeValue();
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
        }else{
            ((TextView) view.findViewById(R.id.item_list_ad_take_to_work)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (courier!=null){
                        new AlertDialog.Builder(ctx)
                                .setTitle("Взять в работу")
                                .setMessage("Вы уверены что хотите взять в работу заказ?")
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //пользаватель взял в работу заказ
                                        //ArrayList<Ad>listWithAdsCourier = courier.getListAdCourier();
                                        //listWithAdsCourier.add(objects.get(position));
                                        long dateAddAdInList = new Date().getTime();
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("couriersWitwApp")
                                                .child(objects.get(position).getTimeAd())
                                                .child(courier.getTimeCourierCreate()+"")
                                                .setValue(courier.getTimeCourierCreate()+"");
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // пользователь отказался от заказа
                                dialog.dismiss();
                            }
                        }).show();
                    }else{ // если мы вошли через кабинет пользователя user

                    }
                }
            });
        }

        ((ImageView) view.findViewById(R.id.image_view_item_main_list_ad_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date=new Date(Long.parseLong(ad.getTimeAd()));
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                String dateText = df2.format(date);

                new AlertDialog.Builder(ctx)
                        .setTitle("О доставке")
                        .setMessage("Заказчик: " + ad.getPeopleNameAd()
                        + "\n\n" +
                        "Дата " + dateText
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

        if (fromWhere!=null){
            if (fromWhere.equals("privateRoomCourier")){
                ((TextView)view.findViewById(R.id.item_list_ad_take_to_work)).setText("Отменить заявку");
                ((TextView)view.findViewById(R.id.item_list_ad_take_to_work)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference().child("couriersWitwApp")
                                .child(objects.get(position).getTimeAd()+"")
                                .child(courier.getTimeCourierCreate()+"").removeValue();
                        Toast.makeText(ctx,"Заявка будет отменена",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        if ((courier==null)&&(ad==null)){
            ((TextView) view.findViewById(R.id.item_list_ad_take_to_work)).setText("Подтвердить");
            ((TextView) view.findViewById(R.id.item_list_ad_take_to_work)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("ads").child(objects.get(position).getTimeAd() + "").child("checkAdmin")
                            .setValue(true);
                    Toast.makeText(ctx, "Объявление одобрено", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }
}