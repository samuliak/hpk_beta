package com.hpk.pr131.hpk_beta.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class LeaderModel implements Serializable{
    private String name;
    private String position;
    private String work;
    private String detailInfo;
    private byte[] imageByteArray;

    public LeaderModel(String name, String position, String work, String detailInfo) {
        this.name = name;
        this.position = position;
        this.work = work;
        this.detailInfo = detailInfo;
    }

    public void setPhoto(Bitmap photo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        imageByteArray = stream.toByteArray();
    }

    public Bitmap getPhoto() {
        Bitmap image = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getWork() {
        return work;
    }

    public String getDetailInfo() { return detailInfo; }
}
