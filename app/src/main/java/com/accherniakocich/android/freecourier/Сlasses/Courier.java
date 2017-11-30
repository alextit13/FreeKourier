package com.accherniakocich.android.freecourier.Сlasses;


import java.io.Serializable;
import java.util.ArrayList;

public class Courier implements Serializable{
    private String nameCourier;
    private int ratingCourier;
    private String aboutCourier;
    private String imagePathCourier;
    private boolean checkBoxCourier;
    private ArrayList<Ad> listAdCourier;

    public Courier() {
    }

    public Courier(String nameCourier, int ratingCourier, String aboutCourier, boolean checkBoxCourier, ArrayList<Ad> listAdCourier) {
        this.nameCourier = nameCourier;
        this.ratingCourier = ratingCourier;
        this.aboutCourier = aboutCourier;
        this.checkBoxCourier = checkBoxCourier;
        this.listAdCourier = listAdCourier;
    }

    public Courier(String nameCourier, int ratingCourier, String aboutCourier, String imagePathCourier, boolean checkBoxCourier, ArrayList<Ad> listAdCourier) {
        this.nameCourier = nameCourier;
        this.ratingCourier = ratingCourier;
        this.aboutCourier = aboutCourier;
        this.imagePathCourier = imagePathCourier;
        this.checkBoxCourier = checkBoxCourier;
        this.listAdCourier = listAdCourier;
    }

    public String getNameCourier() {
        return nameCourier;
    }

    public void setNameCourier(String nameCourier) {
        this.nameCourier = nameCourier;
    }

    public int getRatingCourier() {
        return ratingCourier;
    }

    public void setRatingCourier(int ratingCourier) {
        this.ratingCourier = ratingCourier;
    }

    public String getAboutCourier() {
        return aboutCourier;
    }

    public void setAboutCourier(String aboutCourier) {
        this.aboutCourier = aboutCourier;
    }

    public String getImagePathCourier() {
        return imagePathCourier;
    }

    public void setImagePathCourier(String imagePathCourier) {
        this.imagePathCourier = imagePathCourier;
    }

    public boolean isCheckBoxCourier() {
        return checkBoxCourier;
    }

    public void setCheckBoxCourier(boolean checkBoxCourier) {
        this.checkBoxCourier = checkBoxCourier;
    }

    public ArrayList<Ad> getListAdCourier() {
        return listAdCourier;
    }

    public void setListAdCourier(ArrayList<Ad> listAdCourier) {
        this.listAdCourier = listAdCourier;
    }
}
