package com.pabrou.sol.contacts.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by pablo on 11/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Parcelable {

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    public Address(){

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCityState(){
        String format;
        if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(state)){
            format = city + ", " + state;
        }else if (!TextUtils.isEmpty(city)){
            format = city;
        }else{
            format = state;
        }
        return format;
    }

    public static final Parcelable.Creator<Address> CREATOR
            = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(zip);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    private Address(Parcel in) {
        street = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        zip = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

}
