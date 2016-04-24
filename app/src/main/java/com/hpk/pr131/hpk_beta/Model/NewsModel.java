package com.hpk.pr131.hpk_beta.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NewsModel implements Serializable {
    private String title;
    private String short_Info;
    private String long_Info;
    private String URL;
    private String URL_PHOTO;
    private Bitmap photo;

    public NewsModel() {
        title = "";
        short_Info = "";
        long_Info = "";
        URL = "";
        URL_PHOTO = "";
        photo = null;
    }

    public void setLong_Info(String long_Info) {
        this.long_Info = long_Info;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShort_Info(String short_Info) {
        this.short_Info = short_Info;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    public void setURL_PHOTO(String URL) {
        this.URL_PHOTO = URL;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public String getShort_Info() {
        return short_Info;
    }

    public String getLong_Info() {
        return long_Info;
    }

    public String getURL() {
        return URL;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getURL_PHOTO() {
        return URL_PHOTO;
    }

    public String toString(){
        return "LongINfo: "+long_Info;
    }
}
