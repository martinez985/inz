package com.example.inz2.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {
    @SerializedName("route_id")
    private Integer route_id;
    @SerializedName("truckModel")
    private String truckModel;
    @SerializedName("truckReg")
    private String truckReg;
    @SerializedName("trailerDesc")
    private String trailerDesc;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;
    @SerializedName("dateEnd")
    private String dateEnd;
    @SerializedName("zipCode")
    private String zipCode;
    @SerializedName("zipCodeEnd")
    private String zipCodeEnd;
    @SerializedName("houseNumber")
    private String houseNumber;
    @SerializedName("houseNumberEnd")
    private String houseNumberEnd;
    @SerializedName("gpsX")
    private Double gpsX;
    @SerializedName("gpsY")
    private Double gpsY;
    @SerializedName("gpsXEnd")
    private Double gpsXEnd;
    @SerializedName("gpsYEnd")
    private Double gpsYEnd;
    @SerializedName("address")
    private String address;
    @SerializedName("addressEnd")
    private String addressEnd;
    @SerializedName("city")
    private String city;
    @SerializedName("cityEnd")
    private String cityEnd;
    @SerializedName("driver")
    private String driver;
    @SerializedName("description")
    private String description;
    @SerializedName("packages")
    private List<DataPackage> dataPackages;


    public Route(Integer route_id, String truckModel, String truckReg, String trailerDesc, String name, String date, String dateEnd, String zipCode, String zipCodeEnd, String houseNumber, String houseNumberEnd, Double gpsX, Double gpsY, Double gpsXEnd, Double gpsYEnd, String address, String addressEnd, String city, String cityEnd, String driver, String description, List<DataPackage> dataPackages) {
        this.route_id = route_id;
        this.truckModel = truckModel;
        this.truckReg = truckReg;
        this.trailerDesc = trailerDesc;
        this.name = name;
        this.date = date;
        this.dateEnd = dateEnd;
        this.zipCode = zipCode;
        this.zipCodeEnd = zipCodeEnd;
        this.houseNumber = houseNumber;
        this.houseNumberEnd = houseNumberEnd;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.gpsXEnd = gpsXEnd;
        this.gpsYEnd = gpsYEnd;
        this.address = address;
        this.addressEnd = addressEnd;
        this.city = city;
        this.cityEnd = cityEnd;
        this.driver = driver;
        this.description = description;
        this.dataPackages = dataPackages;
    }

    public Integer getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Integer route_id) {
        this.route_id = route_id;
    }

    public String getTruckModel() {
        return truckModel;
    }

    public void setTruckModel(String truckModel) {
        this.truckModel = truckModel;
    }

    public String getTruckReg() {
        return truckReg;
    }

    public void setTruckReg(String truckReg) {
        this.truckReg = truckReg;
    }

    public String getTrailerDesc() {
        return trailerDesc;
    }

    public void setTrailerDesc(String trailerDesc) {
        this.trailerDesc = trailerDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Double getGpsX() {
        return gpsX;
    }

    public void setGpsX(Double gpsX) {
        this.gpsX = gpsX;
    }

    public Double getGpsY() {
        return gpsY;
    }

    public void setGpsY(Double gpsY) {
        this.gpsY = gpsY;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataPackage> getPackages() {
        return dataPackages;
    }

    public void setPackages(List<DataPackage> dataPackages) {
        this.dataPackages = dataPackages;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public String getCityEnd() {
        return cityEnd;
    }

    public void setCityEnd(String cityEnd) {
        this.cityEnd = cityEnd;
    }

    public List<DataPackage> getDataPackages() {
        return dataPackages;
    }

    public void setDataPackages(List<DataPackage> dataPackages) {
        this.dataPackages = dataPackages;
    }

    public String getZipCodeEnd() {
        return zipCodeEnd;
    }

    public void setZipCodeEnd(String zipCodeEnd) {
        this.zipCodeEnd = zipCodeEnd;
    }

    public String getHouseNumberEnd() {
        return houseNumberEnd;
    }

    public void setHouseNumberEnd(String houseNumberEnd) {
        this.houseNumberEnd = houseNumberEnd;
    }

    public Double getGpsXEnd() {
        return gpsXEnd;
    }

    public void setGpsXEnd(Double gpsXEnd) {
        this.gpsXEnd = gpsXEnd;
    }

    public Double getGpsYEnd() {
        return gpsYEnd;
    }

    public void setGpsYEnd(Double gpsYEnd) {
        this.gpsYEnd = gpsYEnd;
    }
}
