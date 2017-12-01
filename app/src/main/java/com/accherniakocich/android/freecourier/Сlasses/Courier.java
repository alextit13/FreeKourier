package com.accherniakocich.android.freecourier.Сlasses;


import java.io.Serializable;
import java.util.ArrayList;

public class Courier implements Serializable{

    private String emailCourier;
    private String nameCourier;
    private int ratingCourier;
    private String aboutCourier;
    private String imagePathCourier;
    private String numberOfDriverRoot;
    private boolean checkBoxCourier;
    private ArrayList<Ad> listAdCourier;
    private String numberOfPhone;
    private String dateOfBirdth;
    private String numberOfCard;


    public Courier() {
    }

    public Courier(String emailCourier, String nameCourier, int ratingCourier, String imagePathCourier, String numberOfDriverRoot, boolean checkBoxCourier, ArrayList<Ad> listAdCourier, String numberOfPhone, String dateOfBirdth) {
        this.emailCourier = emailCourier;
        this.nameCourier = nameCourier;
        this.ratingCourier = ratingCourier;
        this.imagePathCourier = imagePathCourier;
        this.numberOfDriverRoot = numberOfDriverRoot;
        this.checkBoxCourier = checkBoxCourier;
        this.listAdCourier = listAdCourier;
        this.numberOfPhone = numberOfPhone;
        this.dateOfBirdth = dateOfBirdth;
    }

    public Courier(String emailCourier, String nameCourier, int ratingCourier, String aboutCourier, String numberOfDriverRoot, boolean checkBoxCourier, ArrayList<Ad> listAdCourier, String numberOfPhone, String dateOfBirdth, String numberOfCard) {
        this.emailCourier = emailCourier;
        this.nameCourier = nameCourier;
        this.ratingCourier = ratingCourier;
        this.aboutCourier = aboutCourier;
        this.numberOfDriverRoot = numberOfDriverRoot;
        this.checkBoxCourier = checkBoxCourier;
        this.listAdCourier = listAdCourier;
        this.numberOfPhone = numberOfPhone;
        this.dateOfBirdth = dateOfBirdth;
        this.numberOfCard = numberOfCard;
    }

    public Courier(String emailCourier, String nameCourier, int ratingCourier, String aboutCourier, String imagePathCourier, String numberOfDriverRoot, boolean checkBoxCourier, ArrayList<Ad> listAdCourier, String numberOfPhone, String dateOfBirdth, String numberOfCard) {
        this.emailCourier = emailCourier;
        this.nameCourier = nameCourier;
        this.ratingCourier = ratingCourier;
        this.aboutCourier = aboutCourier;
        this.imagePathCourier = imagePathCourier;
        this.numberOfDriverRoot = numberOfDriverRoot;
        this.checkBoxCourier = checkBoxCourier;
        this.listAdCourier = listAdCourier;
        this.numberOfPhone = numberOfPhone;
        this.dateOfBirdth = dateOfBirdth;
        this.numberOfCard = numberOfCard;
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

    public ArrayList<Ad> getListAdCourier() {
        return listAdCourier;
    }

    public void setListAdCourier(ArrayList<Ad> listAdCourier) {
        this.listAdCourier = listAdCourier;
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
