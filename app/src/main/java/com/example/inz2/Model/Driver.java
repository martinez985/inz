package com.example.inz2.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Driver implements Serializable{

    @SerializedName("trucks")
    private Truck truck;

    public Driver(Truck truck) {
        this.truck = truck;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public static class Truck implements Serializable{
        @SerializedName("model")
        private String model;
        @SerializedName("brand")
        private String brand;
        @SerializedName("registration_number")
        private String registration_number;

        public Truck(String model, String brand, String registration_number) {
            this.model = model;
            this.brand = brand;
            this.registration_number = registration_number;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getRegistration_number() {
            return registration_number;
        }

        public void setRegistration_number(String registration_number) {
            this.registration_number = registration_number;
        }
    }

}
