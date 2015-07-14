package com.pabrou.sol.contacts.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by pablo on 11/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumber implements Parcelable {

    @JsonProperty("work")
    private String work;

    @JsonProperty("home")
    private String home;

    @JsonProperty("mobile")
    private String mobile;

    public PhoneNumber(){
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static final Parcelable.Creator<PhoneNumber> CREATOR
            = new Parcelable.Creator<PhoneNumber>() {
        public PhoneNumber createFromParcel(Parcel in) {
            return new PhoneNumber(in);
        }

        public PhoneNumber[] newArray(int size) {
            return new PhoneNumber[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(work);
        dest.writeString(home);
        dest.writeString(mobile);
    }

    private PhoneNumber(Parcel in) {
        work = in.readString();
        home = in.readString();
        mobile = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
