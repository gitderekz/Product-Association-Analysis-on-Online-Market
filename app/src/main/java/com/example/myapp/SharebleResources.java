package com.example.myapp;

public class SharebleResources {
    public static String contactName;
    public static String imageUrl;
    public static String phoneNo;
    public static String email;

    SharebleResources(String contactName,String imageUrl,String phoneNo,String email)
    {
        this.email = email;
        this.contactName = contactName;
        this.imageUrl = imageUrl;
        this.phoneNo = phoneNo;
    }
}
