package com.example.inz2.Model;

import com.google.gson.annotations.SerializedName;

public class RouteHistory {
    @SerializedName("id")
    private int id;

    @SerializedName("truck")
    private String truck;

    @SerializedName("data_start")
    private String dataStart;

    @SerializedName("data_end")
    private String dataEnd;

    public RouteHistory(int id, String truck, String dataStart, String dataEnd) {
        this.id = id;
        this.truck = truck;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTruck() {
        return truck;
    }

    public void setTruck(String truck) {
        this.truck = truck;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }
}
