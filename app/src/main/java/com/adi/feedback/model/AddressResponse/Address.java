
package com.adi.feedback.model.AddressResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("extraf")
    @Expose
    private String extraf;
    @SerializedName("extraff")
    @Expose
    private String extraff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExtraf() {
        return extraf;
    }

    public void setExtraf(String extraf) {
        this.extraf = extraf;
    }

    public String getExtraff() {
        return extraff;
    }

    public void setExtraff(String extraff) {
        this.extraff = extraff;
    }

}
