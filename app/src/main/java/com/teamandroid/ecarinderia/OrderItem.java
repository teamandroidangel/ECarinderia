package com.teamandroid.ecarinderia;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderItem extends  Object implements Serializable {

    private String name = "menu";
    private Double price = 0.00;
    private Integer quantity = 0;
    private Long id;
    private String orderStatus = "PROCESSING";
    //private String imageUrl = "http://www.birchstreet.net/wp-content/uploads/2014/08/restaurant-icon-4.png";
    private String imageUrl;

    public OrderItem() {
        // A default constructor is required.
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String serialize() {
        return this.getName()+"|"+this.getPrice()+"|"+this.getQuantity()+"|"+this.getImageUrl()+"|"+this.getId()+"|"+this.getOrderStatus();
    }



    public void deserialize(String data){
        String[] stringArray = data.split("\\|");
        this.setName(stringArray[0]);
        try{
            this.setPrice(Double.parseDouble(stringArray[1]));
            this.setQuantity(Integer.parseInt(stringArray[2]));
            this.setImageUrl(stringArray[3]);
            this.setId(Long.parseLong(stringArray[4]));
            this.setOrderStatus(stringArray[5]);
        }catch(Exception error){

        }

    }

}
