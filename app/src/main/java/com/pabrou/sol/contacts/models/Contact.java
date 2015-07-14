package com.pabrou.sol.contacts.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.pabrou.sol.contacts.network.deserializer.CustomDateDeserializer;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pablo on 11/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact implements Parcelable {

    @JsonProperty("employeeId")
    private int employeeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("company")
    private String company;

    @JsonProperty("phone")
    private PhoneNumber phoneNumber;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("smallImageURL")
    private String smallImageURL;

    @JsonProperty("largeImageURL")
    private String largeImageURL;

    @JsonProperty("detailsURL")
    private String detailsURL;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthdate")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date birthdate;

    @JsonProperty("website")
    private String website;

    @JsonProperty("favorite")
    private boolean favorite;

    public Contact(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getDetailsURL() {
        return detailsURL;
    }

    public void setDetailsURL(String detailsURL) {
        this.detailsURL = detailsURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(employeeId);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeParcelable(phoneNumber, flags);
        dest.writeParcelable(address, flags);
        dest.writeString(smallImageURL);
        dest.writeString(largeImageURL);
        dest.writeString(detailsURL);
        dest.writeString(email);
        dest.writeLong(birthdate.getTime());
        dest.writeString(website);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    private Contact(Parcel in) {
        employeeId = in.readInt();
        name = in.readString();
        company = in.readString();
        phoneNumber = in.readParcelable(PhoneNumber.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
        smallImageURL = in.readString();
        largeImageURL = in.readString();
        detailsURL = in.readString();
        email = in.readString();
        birthdate = new Date(in.readLong());
        website = in.readString();
        favorite = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
