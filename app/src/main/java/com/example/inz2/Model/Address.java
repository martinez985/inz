package com.example.inz2.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {
    @SerializedName("address_id")
    private Integer address_id;
    @SerializedName("zip_code")
    private String zip_code;
    @SerializedName("house_number")
    private String house_number;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("routes")
    private List<Object> routes;
    @SerializedName("gps_Y")
    private String gps_Y;
    @SerializedName("gps_X")
    private String gps_X;

    // konstruktory, gettery i settery

    public Address(Integer address_id, String zip_code, String house_number, String city, String address, List<Object> routes, String gps_Y, String gps_X) {
        this.address_id = address_id;
        this.zip_code = zip_code;
        this.house_number = house_number;
        this.city = city;
        this.address = address;
        this.routes = routes;
        this.gps_Y = gps_Y;
        this.gps_X = gps_X;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Object> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Object> routes) {
        this.routes = routes;
    }

    public String getGps_Y() {
        return gps_Y;
    }

    public void setGps_Y(String gps_Y) {
        this.gps_Y = gps_Y;
    }

    public String getGps_X() {
        return gps_X;
    }

    public void setGps_X(String gps_X) {
        this.gps_X = gps_X;
    }
}
