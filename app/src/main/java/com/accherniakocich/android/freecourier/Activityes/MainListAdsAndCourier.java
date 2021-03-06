package com.accherniakocich.android.freecourier.Activityes;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu.PersonalAdsForCourier;
import com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu.PrivateRoomCourier;
import com.accherniakocich.android.freecourier.Activityes.ActivitiesInNavMenu.PrivateRoomUser;
import com.accherniakocich.android.freecourier.Adapters.AdAdapter;
import com.accherniakocich.android.freecourier.Adapters.CourierAdapter;
import com.accherniakocich.android.freecourier.R;
import com.accherniakocich.android.freecourier.Сlasses.Ad;
import com.accherniakocich.android.freecourier.Сlasses.Courier;
import com.accherniakocich.android.freecourier.Сlasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainListAdsAndCourier extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar content_main_list_ads_progress_bar;
    private ListView mainListAdsCourier_list_view;
    private AdAdapter adAdapter;
    private ArrayList<Ad> listAd;
    private ArrayList<Courier> listCourier;
    private CourierAdapter courierAdapter;
    private DatabaseReference reference;
    private Courier courier;
    private User user;
    private FloatingActionButton fab;
    private int clickPosition = 0;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_ads);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //добавление объявления
                Intent intent = new Intent(MainListAdsAndCourier.this, AddAd.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        init();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        city = "Все города";
        checkCity();

        content_main_list_ads_progress_bar = (ProgressBar) findViewById(R.id.content_main_list_ads_progress_bar);
        content_main_list_ads_progress_bar.setVisibility(View.VISIBLE);
        mainListAdsCourier_list_view = (ListView) findViewById(R.id.mainListAds_list_view);

        Intent intent = getIntent();
        courier = (Courier) intent.getSerializableExtra("courier");
        user = (User) intent.getSerializableExtra("user");
        if (courier != null) {
            // вошел курьер. Показываем лист с объявлениями
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(courier);
            prefsEditor.putString("courier", json);
            prefsEditor.commit();

            deleteFloatingActionButton();
            reference = FirebaseDatabase.getInstance().getReference().child("ads");
            listAd = new ArrayList<>();
             // проверяем объявления на город
            mGettingAdsList(reference);
        } else if (user != null) {
            // вошел пользователь, показываем курьеров
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            prefsEditor.putString("user", json);
            prefsEditor.commit();
            reference = FirebaseDatabase.getInstance().getReference().child("couriers");
            listCourier = new ArrayList<>();
            mGettingCourierList(reference);
        }
    }

    private void checkCity(){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json_c = appSharedPrefs.getString("City", "");
        city = gson.fromJson(json_c, String.class);
        if (gson.fromJson(json_c, String.class)==null){
            city="Все города";
        }

        Toast.makeText(this,city,Toast.LENGTH_LONG).show();

    }

    private void deleteFloatingActionButton() {
        fab.setVisibility(View.INVISIBLE);
    }

    private void mGettingCourierList(DatabaseReference ref) {
        final ArrayList<Courier> list = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d(StartActivity.LOG_TAG, "data = " + data.toString());
                    list.add(data.getValue(Courier.class));
                }
                adapterCOURIERstart(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void adapterCOURIERstart(final ArrayList<Courier> list) {
        clickPosition = 0;
        courierAdapter = new CourierAdapter(MainListAdsAndCourier.this, list, null, false, "");
        mainListAdsCourier_list_view.setAdapter(courierAdapter);
        content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);

        mainListAdsCourier_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickPosition = position;
                Intent intent = new Intent(MainListAdsAndCourier.this, PersonalAdsForCourier.class);
                intent.putExtra("user", user);
                intent.putExtra("courier", list.get(position));
                startActivityForResult(intent, 8);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Ad adFromPersonalDialog = (Ad) data.getSerializableExtra("ad");
        Courier c = (Courier) data.getSerializableExtra("courier");
        FirebaseDatabase.getInstance().getReference().child("ads").child(adFromPersonalDialog.getTimeAd() + "")
                .child("courier").setValue(c.getTimeCourierCreate() + "");
        Toast.makeText(this, "Заказ отправлен!", Toast.LENGTH_SHORT).show();
    }

    private void mGettingAdsList(DatabaseReference ref) {
        final ArrayList<Ad> list = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(StartActivity.LOG_TAG,"city = " + city);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    list.add(data.getValue(Ad.class));
                }
                adapterADstart(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void adapterADstart(ArrayList<Ad> list) {
        ArrayList<Ad> finalList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheckAdmin()) {
                finalList.add(list.get(i));
            }
        }
        adAdapter = new AdAdapter(MainListAdsAndCourier.this, finalList, courier);
        mainListAdsCourier_list_view.setAdapter(adAdapter);
        content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        courier = null;
        user = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new AlertDialog.Builder(MainListAdsAndCourier.this)
                .setTitle("Выход")
                .setMessage("Вы уверены что хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //выход
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // пользователь отказался от выхода
                dialog.dismiss();
            }
        }).show();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (courier!=null){
            getMenuInflater().inflate(R.menu.main_list_ads_with_find, menu);
        }else if (user!=null){
            getMenuInflater().inflate(R.menu.main_list_ads, menu);
        }
        return true;
    }


    private String[] getListCity() {
        String[] listCity = new String[]{

                "Все города",
                "Абакан",
                "Азов",
                "Александров",
                "Алексин",
                "Альметьевск",
                "Анапа",
                "Ангарск",
                "Анжеро-Судженск",
                "Апатиты",
                "Арзамас",
                "Армавир",
                "Арсеньев",
                "Артем",
                "Архангельск",
                "Асбест",
                "Астрахань",
                "Ачинск",
                "Балаково",
                "Балахна",
                "Балашиха",
                "Балашов",
                "Барнаул",
                "Батайск",
                "Белгород",
                "Белебей",
                "Белово",
                "Белогорск (Амурская область)",
                "Белорецк",
                "Белореченск",
                "Бердск",
                "Березники",
                "Березовский (Свердловская область)",
                "Бийск",
                "Биробиджан",
                "Благовещенск (Амурская область)",
                "Бор",
                "Борисоглебск",
                "Боровичи",
                "Братск",
                "Брянск",
                "Бугульма",
                "Буденновск",
                "Бузулук",
                "Буйнакск",
                "Великие Луки",
                "Великий Новгород",
                "Верхняя Пышма",
                "Видное",
                "Владивосток",
                "Владикавказ",
                "Владимир",
                "Волгоград",
                "Волгодонск",
                "Волжск",
                "Волжский",
                "Вологда",
                "Вольск",
                "Воркута",
                "Воронеж",
                "Воскресенск",
                "Воткинск",
                "Всеволожск",
                "Выборг",
                "Выкса",
                "Вязьма",
                "Гатчина",
                "Геленджик",
                "Георгиевск",
                "Глазов",
                "Горно-Алтайск",
                "Грозный",
                "Губкин",
                "Гудермес",
                "Гуково",
                "Гусь-Хрустальный",
                "Дербент",
                "Дзержинск",
                "Димитровград",
                "Дмитров",
                "Долгопрудный",
                "Домодедово",
                "Донской",
                "Дубна",
                "Евпатория",
                "Егорьевск",
                "Ейск",
                "Екатеринбург",
                "Елабуга",
                "Елец",
                "Ессентуки",
                "Железногорск (Красноярский край)",
                "Железногорск (Курская область)",
                "Жигулевск",
                "Жуковский",
                "Заречный",
                "Зеленогорск",
                "Зеленодольск",
                "Златоуст",
                "Иваново",
                "Ивантеевка",
                "Ижевск",
                "Избербаш",
                "Иркутск",
                "Искитим",
                "Ишим",
                "Ишимбай",
                "Йошкар-Ола",
                "Казань",
                "Калининград",
                "Калуга",
                "Каменск-Уральский",
                "Каменск-Шахтинский",
                "Камышин",
                "Канск",
                "Каспийск",
                "Кемерово",
                "Керчь",
                "Кинешма",
                "Кириши",
                "Киров (Кировская область)",
                "Кирово-Чепецк",
                "Киселевск",
                "Кисловодск",
                "Клин",
                "Клинцы",
                "Ковров",
                "Когалым",
                "Коломна",
                "Комсомольск-на-Амуре",
                "Копейск",
                "Королев",
                "Кострома",
                "Котлас",
                "Красногорск",
                "Краснодар",
                "Краснокаменск",
                "Краснокамск",
                "Краснотурьинск",
                "Красноярск",
                "Кропоткин",
                "Крымск",
                "Кстово",
                "Кузнецк",
                "Кумертау",
                "Кунгур",
                "Курган",
                "Курск",
                "Кызыл",
                "Лабинск",
                "Лениногорск",
                "Ленинск-Кузнецкий",
                "Лесосибирск",
                "Липецк",
                "Лиски",
                "Лобня",
                "Лысьва",
                "Лыткарино",
                "Люберцы",
                "Магадан",
                "Магнитогорск",
                "Майкоп",
                "Махачкала",
                "Междуреченск",
                "Мелеуз",
                "Миасс",
                "Минеральные Воды",
                "Минусинск",
                "Михайловка",
                "Михайловск (Ставропольский край)",
                "Мичуринск",
                "Москва",
                "Мурманск",
                "Муром",
                "Мытищи",
                "Набережные Челны",
                "Назарово",
                "Назрань",
                "Нальчик",
                "Наро-Фоминск",
                "Находка",
                "Невинномысск",
                "Нерюнгри",
                "Нефтекамск",
                "Нефтеюганск",
                "Нижневартовск",
                "Нижнекамск",
                "Нижний Новгород",
                "Нижний Тагил",
                "Новоалтайск",
                "Новокузнецк",
                "Новокуйбышевск",
                "Новомосковск",
                "Новороссийск",
                "Новосибирск",
                "Новотроицк",
                "Новоуральск",
                "Новочебоксарск",
                "Новочеркасск",
                "Новошахтинск",
                "Новый Уренгой",
                "Ногинск",
                "Норильск",
                "Ноябрьск",
                "Нягань",
                "Обнинск",
                "Одинцово",
                "Озерск (Челябинская область)",
                "Октябрьский",
                "Омск",
                "Орел",
                "Оренбург",
                "Орехово-Зуево",
                "Орск",
                "Павлово",
                "Павловский Посад",
                "Пенза",
                "Первоуральск",
                "Пермь",
                "Петрозаводск",
                "Петропавловск-Камчатский",
                "Подольск",
                "Полевской",
                "Прокопьевск",
                "Прохладный",
                "Псков",
                "Пушкино",
                "Пятигорск",
                "Раменское",
                "Ревда",
                "Реутов",
                "Ржев",
                "Рославль",
                "Россошь",
                "Ростов-на-Дону",
                "Рубцовск",
                "Рыбинск",
                "Рязань",
                "Салават",
                "Сальск",
                "Самара",
                "Санкт-Петербург",
                "Саранск",
                "Сарапул",
                "Саратов",
                "Саров",
                "Свободный",
                "Севастополь",
                "Северодвинск",
                "Северск",
                "Сергиев Посад",
                "Серов",
                "Серпухов",
                "Сертолово",
                "Сибай",
                "Симферополь",
                "Славянск-на-Кубани",
                "Смоленск",
                "Соликамск",
                "Солнечногорск",
                "Сосновый Бор",
                "Сочи",
                "Ставрополь",
                "Старый Оскол",
                "Стерлитамак",
                "Ступино",
                "Сургут",
                "Сызрань",
                "Сыктывкар",
                "Таганрог",
                "Тамбов",
                "Тверь",
                "Тимашевск",
                "Тихвин",
                "Тихорецк",
                "Тобольск",
                "Тольятти",
                "Томск",
                "Троицк",
                "Туапсе",
                "Туймазы",
                "Тула",
                "Тюмень",
                "Узловая",
                "Улан-Удэ",
                "Ульяновск",
                "Урус-Мартан",
                "Усолье-Сибирское",
                "Уссурийск",
                "Усть-Илимск",
                "Уфа",
                "Ухта",
                "Феодосия",
                "Фрязино",
                "Хабаровск",
                "Ханты-Мансийск",
                "Хасавюрт",
                "Химки",
                "Чайковский",
                "Чапаевск",
                "Чебоксары",
                "Челябинск",
                "Черемхово",
                "Череповец",
                "Черкесск",
                "Черногорск",
                "Чехов",
                "Чистополь",
                "Чита",
                "Шадринск",
                "Шали",
                "Шахты",
                "Шуя",
                "Щекино",
                "Щелково",
                "Электросталь",
                "Элиста",
                "Энгельс",
                "Южно-Сахалинск",
                "Юрга",
                "Якутск",
                "Ялта",
                "Ярославль"};
        return listCity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            content_main_list_ads_progress_bar.setVisibility(View.VISIBLE);
            if (courier != null) {
                mGettingAdsList(FirebaseDatabase.getInstance().getReference().child("ads"));
            } else if (user != null) {
                mGettingCourierList(FirebaseDatabase.getInstance().getReference().child("couriers"));
            }
            content_main_list_ads_progress_bar.setVisibility(View.INVISIBLE);
        }else if (id == R.id.action_find){

            if (courier!=null){
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
                dialogBuilder.setView(dialogView);

                Button b_cancel = (Button)dialogView.findViewById(R.id.dialog_cancel);

                Button b_ok = (Button)dialogView.findViewById(R.id.dialog_ok);
                final Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner_city);
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getListCity());
                spinner.setAdapter(adapter);

                final AlertDialog alertDialog = dialogBuilder.create();
                b_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                b_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!spinner.getSelectedItem().toString().equals("Все города")){
                            showOnlyCity(spinner.getSelectedItem().toString());
                        }
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

                /*
                Intent intent = new Intent(MainListAdsAndCourier.this, Find.class);
                intent.putExtra("courier",courier);
                startActivity(intent);*/
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOnlyCity(final String cityFromDialog) {
        final ArrayList<Ad> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(StartActivity.LOG_TAG,"new city = " + city);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue(Ad.class).getFrom().contains(cityFromDialog)
                            ||data.getValue(Ad.class).getTo().contains(cityFromDialog)){
                        list.add(data.getValue(Ad.class));
                        Log.d(StartActivity.LOG_TAG,"add");
                    }
                }
                Log.d(StartActivity.LOG_TAG,"start");
                adapterADstart(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @SuppressLint("ApplySharedPref")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.private_room) {
            if (courier != null) { // если вошел курьер
                Intent intent = new Intent(MainListAdsAndCourier.this, PrivateRoomCourier.class);
                intent.putExtra("courier", courier);
                startActivity(intent);
            } else if (user != null) { // если вошел пользователь
                Intent intent = new Intent(MainListAdsAndCourier.this, PrivateRoomUser.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        } else if (id == R.id.exit) {
            new AlertDialog.Builder(MainListAdsAndCourier.this)
                    .setTitle("Выход")
                    .setMessage("Вы уверены что хотите выйти?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //пользаватель взял в работу заказ
                            dialog.dismiss();
                            SharedPreferences appSharedPrefs = PreferenceManager
                                    .getDefaultSharedPreferences(getApplicationContext());
                            appSharedPrefs
                                    .edit()
                                    .clear()
                                    .commit();
                            finish();
                        }
                    }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // пользователь отказался от заказа
                    dialog.dismiss();
                }
            }).show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
