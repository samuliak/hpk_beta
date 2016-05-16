package com.hpk.pr131.hpk_beta.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class NewsModel implements Serializable {
    private String title;
    private String short_Info;
    private String long_Info;
    private String URL;
    private String URL_PHOTO;
    private String date;
    private byte[] imageByteArray;

    public NewsModel() {
        title = "";
        short_Info = "";
        long_Info = "";
        URL = "";
        URL_PHOTO = "";
        date = "";
    }

    public void setDate(String date) {
        this.date = date;
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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        imageByteArray = stream.toByteArray();
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

    public String getDate() {
        return date;
    }

    public Bitmap getPhoto() {
        Bitmap image = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        return image;
    }

    public String getURL_PHOTO() {
        return URL_PHOTO;
    }

    public String toString(){
        return "LongINfo: "+long_Info;
    }
}
