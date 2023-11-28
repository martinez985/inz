package com.example.inz2;

import com.google.gson.annotations.SerializedName;

public class LoginBody {

    @SerializedName("loginUsername")
    private String login;
    @SerializedName("loginPassword")
    private String password;
    public LoginBody(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
