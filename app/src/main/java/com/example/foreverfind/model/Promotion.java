package com.example.foreverfind.model;

public class Promotion {

    private String desc;
    private String expdate;
    private String sdate;
    private Long maxtot;
    private Long value;

    public Promotion() {
    }

    public String getDesc() {
        return desc;
    }

    public Long getMaxtot() {
        return maxtot;
    }

    public void setMaxtot(Long maxtot) {
        this.maxtot = maxtot;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
