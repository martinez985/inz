package com.example.inz2;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    String token;
    @SerializedName("role")
    String role;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
