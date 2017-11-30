package com.accherniakocich.android.freecourier.Сlasses;

import java.io.Serializable;

public class User implements Serializable{
    private String courierFIO;
    private int courierNumberPhone;
    private String courierDateOfBirdth;
    private String courierNumberOfDriverRoot;
    private String courierNumberOfCard;
    private String courierEmail;
    private String courierNickName;
    private int courierRating;

    public User() {
    }

    public User(String courierFIO, int courierNumberPhone, String courierDateOfBirdth, String courierNumberOfDriverRoot, String courierEmail, String courierNickName, int courierRating) {
        this.courierFIO = courierFIO;
        this.courierNumberPhone = courierNumberPhone;
        this.courierDateOfBirdth = courierDateOfBirdth;
        this.courierNumberOfDriverRoot = courierNumberOfDriverRoot;
        this.courierEmail = courierEmail;
        this.courierNickName = courierNickName;
        this.courierRating = courierRating;
    }

    public User(String courierFIO, int courierNumberPhone, String courierDateOfBirdth, String courierNumberOfDriverRoot, String courierNumberOfCard, String courierEmail, String courierNickName, int courierRating) {
        this.courierFIO = courierFIO;
        this.courierNumberPhone = courierNumberPhone;
        this.courierDateOfBirdth = courierDateOfBirdth;
        this.courierNumberOfDriverRoot = courierNumberOfDriverRoot;
        this.courierNumberOfCard = courierNumberOfCard;
        this.courierEmail = courierEmail;
        this.courierNickName = courierNickName;
        this.courierRating = courierRating;
    }
}
