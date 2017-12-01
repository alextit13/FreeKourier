package com.accherniakocich.android.freecourier.Сlasses;

import java.io.Serializable;

public class User implements Serializable{
    private String userFIO;
    private String userNumberPhone;
    private String userEmail;

    public User() {
    }

    public User(String userFIO, String userNumberPhone, String userEmail) {
        this.userFIO = userFIO;
        this.userNumberPhone = userNumberPhone;
        this.userEmail = userEmail;
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
}
