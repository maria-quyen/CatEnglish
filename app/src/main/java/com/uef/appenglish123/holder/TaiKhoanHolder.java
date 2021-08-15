package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.uef.appenglish123.R;

public class TaiKhoanHolder extends RecyclerView.ViewHolder{
    public TextView txtTenTK, txtEmail, txtTinMoiNhat;
    public RoundedImageView rivTK;
    public LinearLayout showOn;
    public TaiKhoanHolder(@NonNull View itemView) {
        super(itemView);

        showOn=itemView.findViewById(R.id.showOn);
        txtTenTK=itemView.findViewById(R.id.tvTenTKChat);
        txtEmail=itemView.findViewById(R.id.tvTinMoiNhat);
        txtTinMoiNhat=itemView.findViewById(R.id.tvTinNhanMoi);
        rivTK=itemView.findViewById(R.id.riv_TaiKhoan);

    }

    public void setTxtTenTK(String string) {
        txtTenTK.setText(string);
    }
    public void setTxtEmail(String string) {
        txtEmail.setText(string);
    }
    public void setTxtTinMoiNhat(String string) {
        txtTinMoiNhat.setText(string);
    }
}
