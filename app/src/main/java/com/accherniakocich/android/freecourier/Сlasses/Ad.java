package com.accherniakocich.android.freecourier.Сlasses;

import java.io.Serializable;

public class Ad implements Serializable{
    private String peopleNameAd;
    private String adName;
    private String from;
    private String to;
    private String aboutAd;
    private int price;
    private String timeAd;
    private String usersTime;
    private boolean isCheckAdmin;

    public Ad() {
    }

    public Ad(String peopleNameAd, String adName, String from, String to, String aboutAd, int price, String timeAd, String usersTime, boolean isCheckAdmin) {
        this.peopleNameAd = peopleNameAd;
        this.adName = adName;
        this.from = from;
        this.to = to;
        this.aboutAd = aboutAd;
        this.price = price;
        this.timeAd = timeAd;
        this.usersTime = usersTime;
        this.isCheckAdmin = isCheckAdmin;
    }

    public String getPeopleNameAd() {
        return peopleNameAd;
    }

    public void setPeopleNameAd(String peopleNameAd) {
        this.peopleNameAd = peopleNameAd;
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

    public String getUsersTime() {
        return usersTime;
    }

    public void setUsersTime(String usersTime) {
        this.usersTime = usersTime;
    }

    public boolean isCheckAdmin() {
        return isCheckAdmin;
    }

    public void setCheckAdmin(boolean checkAdmin) {
        isCheckAdmin = checkAdmin;
    }
}
