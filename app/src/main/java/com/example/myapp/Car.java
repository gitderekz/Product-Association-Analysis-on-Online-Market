package com.example.myapp;

public class Car {
    private String CarName;
    private String ImageUrl;
    private String ImageUrl1;
    private String ImageUrl2;
    private String ImageUrl3;
    private String ItemPrice;
    private String time_stamp;
    private String transation_time;
    private String transation_items;
    private String transation_amount;
    private int itemCount;
    private int itemId;
    private String ItemDescription;
//    private String ItemKey;

    public Car() {
    }

    public Car(String carName, String imageUrl, String imageUrl1, String imageUrl2, String imageUrl3, String itemPrice, String time_stamp, String transation_time, String transation_items, String transation_amount, int itemCount, int itemId, String itemDescription) {
        CarName = carName;
        ImageUrl = imageUrl;
        ImageUrl1 = imageUrl1;
        ImageUrl2 = imageUrl2;
        ImageUrl3 = imageUrl3;
        ItemPrice = itemPrice;
        this.time_stamp = time_stamp;
        this.transation_time = transation_time;
        this.transation_items = transation_items;
        this.transation_amount = transation_amount;
        this.itemCount = itemCount;
        this.itemId = itemId;
        ItemDescription = itemDescription;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getImageUrl1() {
        return ImageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        ImageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return ImageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        ImageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return ImageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        ImageUrl3 = imageUrl3;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getTransation_time() {
        return transation_time;
    }

    public void setTransation_time(String transation_time) {
        this.transation_time = transation_time;
    }

    public String getTransation_items() {
        return transation_items;
    }

    public void setTransation_items(String transation_items) {
        this.transation_items = transation_items;
    }

    public String getTransation_amount() {
        return transation_amount;
    }

    public void setTransation_amount(String transation_amount) {
        this.transation_amount = transation_amount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }
}
