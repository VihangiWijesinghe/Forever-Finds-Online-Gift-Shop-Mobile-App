package com.example.foreverfindvihangi.model;

public class ShoppingCart {

    private String cartId;
    private String pid;
    private String pname;
    private String price;
    private String quantity;

    public ShoppingCart() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCartId() {
        return cartId;
    }
}
