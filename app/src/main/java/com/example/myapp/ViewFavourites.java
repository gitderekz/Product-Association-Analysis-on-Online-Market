package com.example.myapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewFavourites extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    TextView totalPrice;
    ImageButton delete;
    View v;


    public ViewFavourites(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.cartImage);
        textView = itemView.findViewById(R.id.cartItemName);
        totalPrice = itemView.findViewById(R.id.totalPrice);
        delete = itemView.findViewById(R.id.imageButton2);
        v = itemView;
    }
}