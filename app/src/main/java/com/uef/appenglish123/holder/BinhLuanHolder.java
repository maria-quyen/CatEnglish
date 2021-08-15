package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.uef.appenglish123.R;

public class BinhLuanHolder extends RecyclerView.ViewHolder {

    public RoundedImageView riv;
    public TextView tvTenTK,tvNoiDungBL;

    public BinhLuanHolder(@NonNull View itemView) {
        super(itemView);

        riv=itemView.findViewById(R.id.rivHinhComment);
        tvTenTK=itemView.findViewById(R.id.tvTenTKComment);
        tvNoiDungBL=itemView.findViewById(R.id.tvComment);
    }
}
