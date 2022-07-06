package com.example.myapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageButton imageButton;
    ImageButton likeButton;
    ImageView imageView;
    TextView textView;
    TextView price;
    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageButton = itemView.findViewById(R.id.cartButton);
        likeButton = itemView.findViewById(R.id.likeButton);
        imageView = itemView.findViewById(R.id.image_single_view);
        textView = itemView.findViewById(R.id.textView_single_view);
        price = itemView.findViewById(R.id.singleView_Price);
        v = itemView;
    }
}
