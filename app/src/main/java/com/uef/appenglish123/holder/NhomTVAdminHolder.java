package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class NhomTVAdminHolder extends RecyclerView.ViewHolder {
    public TextView txtMaNhom;
    public TextView txtTenNhom;
    public ImageView imgView, imgDel, imgEdit;


    public NhomTVAdminHolder(@NonNull View itemView) {
        super(itemView);
        txtMaNhom = itemView.findViewById(R.id.txtMaNhom2);
        txtTenNhom = itemView.findViewById(R.id.txtTenNhom2);
        imgView=itemView.findViewById(R.id.imgView);
        imgDel=itemView.findViewById(R.id.imgDel);
        imgEdit=itemView.findViewById(R.id.imgEdit);
    }

    public void setTxtMaNhom(String string) {
        txtMaNhom.setText(string);
    }
    public void setTxtTenNhom(String string) {
        txtTenNhom.setText(string);
    }
}
