package com.accherniakocich.android.freecourier.Сlasses;


import java.io.Serializable;
import java.util.ArrayList;

public class Courier implements Serializable{

    private long timeCourierCreate;
    private String emailCourier;
    private String nameCourier;
    private int ratingCourier;
    private String aboutCourier;
    private String imagePathCourier;
    private String numberOfDriverRoot;
    private boolean checkBoxCourier;
    private String numberOfPhone;
    private String dateOfBirdth;
    private String numberOfCard;


    public Courier() {
    }

    public Courier(long timeCourierCreate, String emailCourier, String nameCourier, int ratingCourier, String aboutCourier, String imagePathCourier, String numberOfDriverRoot, boolean checkBoxCourier, String numberOfPhone, String dateOfBirdth, String numberOfCard) {
        this.timeCourierCreate = timeCourierCreate;
        this.emailCourier = emailCourier;
        this.nameCourier = nameCourier;
        this.ratingCourier = ratingCourier;
        this.aboutCourier = aboutCourier;
        this.imagePathCourier = imagePathCourier;
        this.numberOfDriverRoot = numberOfDriverRoot;
        this.checkBoxCourier = checkBoxCourier;
        this.numberOfPhone = numberOfPhone;
        this.dateOfBirdth = dateOfBirdth;
        this.numberOfCard = numberOfCard;
    }

    public long getTimeCourierCreate() {
        return timeCourierCreate;
    }

    public void setTimeCourierCreate(long timeCourierCreate) {
        this.timeCourierCreate = timeCourierCreate;
    }

    public String getEmailCourier() {
        return emailCourier;
    }

    public void setEmailCourier(String emailCourier) {
        this.emailCourier = emailCourier;
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

    public String getNumberOfDriverRoot() {
        return numberOfDriverRoot;
    }

    public void setNumberOfDriverRoot(String numberOfDriverRoot) {
        this.numberOfDriverRoot = numberOfDriverRoot;
    }

    public boolean isCheckBoxCourier() {
        return checkBoxCourier;
    }

    public void setCheckBoxCourier(boolean checkBoxCourier) {
        this.checkBoxCourier = checkBoxCourier;
    }

    public String getNumberOfPhone() {
        return numberOfPhone;
    }

    public void setNumberOfPhone(String numberOfPhone) {
        this.numberOfPhone = numberOfPhone;
    }

    public String getDateOfBirdth() {
        return dateOfBirdth;
    }

    public void setDateOfBirdth(String dateOfBirdth) {
        this.dateOfBirdth = dateOfBirdth;
    }

    public String getNumberOfCard() {
        return numberOfCard;
    }

    public void setNumberOfCard(String numberOfCard) {
        this.numberOfCard = numberOfCard;
    }
}
