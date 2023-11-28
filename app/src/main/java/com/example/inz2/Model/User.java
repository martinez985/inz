package com.example.inz2.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @SerializedName("user_id")
    private Integer user_id;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("deleted")
    private Boolean deleted;
    @SerializedName("password_recovery_link")
    private String password_recovery_link;
    @SerializedName("role")
    private Role role;
    @SerializedName("company")
    private Company company;
    @SerializedName("trucks")
    private List<Object> trucks;

    // konstruktory, gettery i settery

    public static class Role implements Serializable{
        private Integer role_id;
        private String role_name;

        // konstruktory, gettery i settery
    }

    public static class Company implements Serializable{
        private Integer company_id;
        private String company_name;
        private String company_nip;

        // konstruktory, gettery i settery
    }
}
