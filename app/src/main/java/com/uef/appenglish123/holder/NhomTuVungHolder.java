package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class NhomTuVungHolder extends RecyclerView.ViewHolder {
    public TextView txtMaNhom;
    public TextView txtTenNhom;
    public LinearLayout linearLayout;


    public NhomTuVungHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout= itemView.findViewById(R.id.listNhomTuVung);
        txtMaNhom = itemView.findViewById(R.id.txtMaNhomTuVung);
        txtTenNhom = itemView.findViewById(R.id.txtTenNhomTuVung);

    }

    public void setTxtMaNhom(String string) {
        txtMaNhom.setText(string);
    }


    public void setTxtTenNhom(String string) {
        txtTenNhom.setText(string);
    }
}
