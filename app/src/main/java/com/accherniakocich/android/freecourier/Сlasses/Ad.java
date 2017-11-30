package com.accherniakocich.android.freecourier.Сlasses;

import java.io.Serializable;

public class Ad implements Serializable{
    private String adName;
    private String from;
    private String to;
    private String aboutAd;
    private int price;
    private String timeAd;

    public Ad() {
    }

    public Ad(String adName, String from, String to, String aboutAd, int price, String timeAd) {
        this.adName = adName;
        this.from = from;
        this.to = to;
        this.aboutAd = aboutAd;
        this.price = price;
        this.timeAd = timeAd;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAboutAd() {
        return aboutAd;
    }

    public void setAboutAd(String aboutAd) {
        this.aboutAd = aboutAd;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTimeAd() {
        return timeAd;
    }

    public void setTimeAd(String timeAd) {
        this.timeAd = timeAd;
    }
}
