package com.hpk.pr131.hpk_beta.Model;

import java.io.Serializable;

public class ReplaceModel implements Serializable{
    private String group;
    private String pair;
    private String replaced;
    private String subject;
    private String teacher;
    private String audience;
    private String date;

    public ReplaceModel(String group, String pair, String replaced, String subject, String teacher, String audience) {
        this.group = group;
        this.pair = pair;
        this.replaced = replaced;
        this.subject = subject;
        this.teacher = teacher;
        this.audience = audience;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroup() {
        return group;
    }

    public String getPair() {
        return pair;
    }

    public String getReplaced() {
        return replaced;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getAudience() {
        return audience;
    }

    public String toString(){
        return "Група: "+group+", Пара: "+pair+", Замінено: "+replaced+
                ", Предмет: " +subject+", Викладач: "+teacher+", Ауд. "+audience;
    }
}
