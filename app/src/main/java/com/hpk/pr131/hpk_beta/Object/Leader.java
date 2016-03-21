package com.hpk.pr131.hpk_beta.Object;

import java.io.Serializable;

public class Leader implements Serializable{
    private String name;
    private String position;
    private String work;
    private String detailInfo;

    public Leader(String name, String position, String work, String detailInfo) {
        this.name = name;
        this.position = position;
        this.work = work;
        this.detailInfo = detailInfo;
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

    public String getdDetailInfo() { return detailInfo; }

}
