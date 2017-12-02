package com.accherniakocich.android.freecourier.Сlasses;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    private String userFIO;
    private String userNumberPhone;
    private String userEmail;
    private ArrayList<Ad>listCreatedAds;
    private long date;

    public User() {
    }

    public User(String userFIO, String userNumberPhone, String userEmail, ArrayList<Ad> listCreatedAds, long date) {
        this.userFIO = userFIO;
        this.userNumberPhone = userNumberPhone;
        this.userEmail = userEmail;
        this.listCreatedAds = listCreatedAds;
        this.date = date;
    }

    public String getUserFIO() {
        return userFIO;
    }

    public void setUserFIO(String userFIO) {
        this.userFIO = userFIO;
    }

    public String getUserNumberPhone() {
        return userNumberPhone;
    }

    public void setUserNumberPhone(String userNumberPhone) {
        this.userNumberPhone = userNumberPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<Ad> getListCreatedAds() {
        return listCreatedAds;
    }

    public void setListCreatedAds(ArrayList<Ad> listCreatedAds) {
        this.listCreatedAds = listCreatedAds;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
