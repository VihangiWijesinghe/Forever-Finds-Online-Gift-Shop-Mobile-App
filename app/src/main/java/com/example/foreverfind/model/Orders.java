package com.example.foreverfind.model;

public class Orders {


    String orderNo;
    String RecName;
    String Daddress;
    String ResPhone;
    Boolean status;

    public Orders() {
    }

    public Orders(String orderNo, String recName, String daddress, String resPhone, Boolean status) {
        this.orderNo = orderNo;
        RecName = recName;
        Daddress = daddress;
        ResPhone = resPhone;
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRecName() {
        return RecName;
    }

    public void setRecName(String recName) {
        RecName = recName;
    }

    public String getDaddress() {
        return Daddress;
    }

    public void setDaddress(String daddress) {
        Daddress = daddress;
    }

    public String getResPhone() {
        return ResPhone;
    }

    public void setResPhone(String resPhone) {
        ResPhone = resPhone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
