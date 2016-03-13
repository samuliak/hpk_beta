package com.hpk.pr131.hpk_beta.Object;

/**
 * Created by samuliak on 13.03.2016.
 */
public class Leader {
    private String name;
    private String position;
    private String work;

    public Leader(String name, String position, String work) {
        this.name = name;
        this.position = position;
        this.work = work;
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
}
