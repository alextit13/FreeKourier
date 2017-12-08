package com.accherniakocich.android.freecourier.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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

public class CourierAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Courier> objects;
    Admin administrator;
    boolean review;
    String dateTimeAd;

    public CourierAdapter(Context ctx, ArrayList<Courier> objects, Admin administrator, boolean review,String dateAd) {
        lInflater = LayoutInflater.from(ctx);
        this.dateTimeAd=dateAd;
        this.ctx = ctx;
        this.objects = objects;
        this.administrator = administrator;
        this.review = review;
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
            view = lInflater.inflate(R.layout.item_list_courier, parent, false);
        }

        final Courier courier = getAd(position);

        if (review&&!dateTimeAd.equals("")){
            ((TextView)view.findViewById(R.id.review)).setText("Сделать исполнителем");
            ((TextView)view.findViewById(R.id.review)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // отправляем уведомление курьеру
                    FirebaseDatabase.getInstance().getReference().child("ads").child(dateTimeAd)
                            .child("courier").setValue(courier.getTimeCourierCreate()+"");
                    Toast.makeText(ctx, "Исполнитель принят", Toast.LENGTH_SHORT).show();
                    courier.setCheckBoxCourier(true);
                }
            });
        }

        if (courier.isCheckBoxCourier()){ // если курьера выбрали для исполнения работы
            ((TextView) view.findViewById(R.id.item_list_courier_name)).setText(courier.getNameCourier());
            ((TextView) view.findViewById(R.id.item_list_courier_number_of_card)).setText(courier.getNumberOfCard());
            ((TextView) view.findViewById(R.id.item_list_courier_number_of_phone)).setText(courier.getNumberOfPhone());
            ((TextView) view.findViewById(R.id.item_list_courier_date_of_birdth)).setText(courier.getDateOfBirdth());
        }
        ((TextView) view.findViewById(R.id.item_list_courier_about)).setText(courier.getAboutCourier());
        ((TextView) view.findViewById(R.id.item_list_courier_number_of_driver_root)).setText(courier.getNumberOfDriverRoot());


        ((RatingBar) view.findViewById(R.id.item_list_courier_rating_bar)).setRating(courier.getRatingCourier());
        CircleImageView CIM = (CircleImageView) view.findViewById(R.id.item_list_courier_image);
        if (!courier.getImagePathCourier().equals("")){
            Picasso.with(ctx).load(courier.getImagePathCourier()).into(CIM);
        }else{
            Picasso.with(ctx).load("http://www.sitechecker.eu/img/not-available.png").into(CIM);
        }


        /**
         * НИЖЕ НУЖНО БУДЕТ ВКЛЮЧИТЬ. ДЛЯ ЭТОГО НУЖНО ДОБАВИТЬ КОРЗИНУ В ИТЕМ ДЛЯ КУРЬЕРА
         */


        if (administrator!=null){
            ((ImageView)view.findViewById(R.id.delete_courier)).setVisibility(View.VISIBLE);
            ((ImageView)view.findViewById(R.id.delete_courier)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("couriers").child(objects.get(position).getTimeCourierCreate()+"")
                            .removeValue();
                    Toast.makeText(ctx, "Курьер будет удален из базы", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            ((ImageView)view.findViewById(R.id.delete_courier)).setVisibility(View.INVISIBLE);
        }

        //((TextView) view.findViewById(R.id.tvText)).setText(ad.getNameJobAd());

        return view;
    }

    // товар по позиции
    Courier getAd(int position) {
        return ((Courier) getItem(position));
    }
}