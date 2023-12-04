package com.example.inz2.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataPackage implements Serializable {
    @SerializedName("commission_id")
    private Integer commission_id;
    @SerializedName("date_of_placement")
    private String date_of_placement;
    @SerializedName("date_of_receipt")
    private String date_of_receipt;
    @SerializedName("description")
    private String description;
    @SerializedName("mass")
    private Double mass;
    @SerializedName("stackable")
    private Boolean stackable;
    @SerializedName("count")
    private Integer count;
    @SerializedName("is_loaded")
    private Boolean is_loaded;
    @SerializedName("is_unloaded")
    private Boolean is_unloaded;
    @SerializedName("canceled")
    private Integer canceled;
    @SerializedName("user")
    private User user;
    @SerializedName("delivery_pickup")
    private Address delivery_pickup;
    @SerializedName("delivery_endpoint")
    private Address delivery_endpoint;
    @SerializedName("point_number_start")
    private Integer point_number_start;
    @SerializedName("point_number_end")
    private Integer point_number_end;
    @SerializedName("y")
    private Double y;
    @SerializedName("x")
    private Double x;
    @SerializedName("z")
    private Double z;

    // konstruktory, gettery i settery


    public DataPackage(Integer commission_id, String date_of_placement, String date_of_receipt, String description, Double mass, Boolean stackable, Integer count, Boolean is_loaded, Boolean is_unloaded, Integer canceled, User user, Address delivery_pickup, Address delivery_endpoint, Integer point_number_start, Integer point_number_end, Double y, Double x, Double z) {
        this.commission_id = commission_id;
        this.date_of_placement = date_of_placement;
        this.date_of_receipt = date_of_receipt;
        this.description = description;
        this.mass = mass;
        this.stackable = stackable;
        this.count = count;
        this.is_loaded = is_loaded;
        this.is_unloaded = is_unloaded;
        this.canceled = canceled;
        this.user = user;
        this.delivery_pickup = delivery_pickup;
        this.delivery_endpoint = delivery_endpoint;
        this.point_number_start = point_number_start;
        this.point_number_end = point_number_end;
        this.y = y;
        this.x = x;
        this.z = z;
    }

    public Integer getCommission_id() {
        return commission_id;
    }

    public void setCommission_id(Integer commission_id) {
        this.commission_id = commission_id;
    }

    public String getDate_of_placement() {
        return date_of_placement;
    }

    public void setDate_of_placement(String date_of_placement) {
        this.date_of_placement = date_of_placement;
    }

    public String getDate_of_receipt() {
        return date_of_receipt;
    }

    public void setDate_of_receipt(String date_of_receipt) {
        this.date_of_receipt = date_of_receipt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Boolean getStackable() {
        return stackable;
    }

    public void setStackable(Boolean stackable) {
        this.stackable = stackable;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getIs_loaded() {
        return is_loaded;
    }

    public void setIs_loaded(Boolean is_loaded) {
        this.is_loaded = is_loaded;
    }

    public Boolean getIs_unloaded() {
        return is_unloaded;
    }

    public void setIs_unloaded(Boolean is_unloaded) {
        this.is_unloaded = is_unloaded;
    }

    public Integer getCanceled() {
        return canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getDelivery_pickup() {
        return delivery_pickup;
    }

    public void setDelivery_pickup(Address delivery_pickup) {
        this.delivery_pickup = delivery_pickup;
    }

    public Address getDelivery_endpoint() {
        return delivery_endpoint;
    }

    public void setDelivery_endpoint(Address delivery_endpoint) {
        this.delivery_endpoint = delivery_endpoint;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Integer getPoint_number_start() {
        return point_number_start;
    }

    public void setPoint_number_start(Integer point_number_start) {
        this.point_number_start = point_number_start;
    }

    public Integer getPoint_number_end() {
        return point_number_end;
    }

    public void setPoint_number_end(Integer point_number_end) {
        this.point_number_end = point_number_end;
    }
}