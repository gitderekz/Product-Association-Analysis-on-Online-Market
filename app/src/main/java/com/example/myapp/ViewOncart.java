package com.example.myapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewOncart extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    TextView totalPrice;
    ImageButton delete;
    Button buyNow;
    Spinner quantity;
    View v;


    public ViewOncart(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.cartImage);
        textView = itemView.findViewById(R.id.cartItemName);
        totalPrice = itemView.findViewById(R.id.totalPrice);
        delete = itemView.findViewById(R.id.imageButton2);
        buyNow = itemView.findViewById(R.id.buyNow);
        quantity = itemView.findViewById(R.id.spinnerQuantity);
        v = itemView;
    }
}
