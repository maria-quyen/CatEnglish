package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.uef.appenglish123.R;

public class ImageHolder extends RecyclerView.ViewHolder {

    public ImageView img;


    public ImageHolder(@NonNull View itemView) {
        super(itemView);

        img=(ImageView) itemView.findViewById(R.id.imgSetting);
    }
}

