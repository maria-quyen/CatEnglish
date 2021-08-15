package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.uef.appenglish123.R;

public class TuVungAdminHolder extends RecyclerView.ViewHolder {
    public TextView txtMaTV, txtMaBai, txtTuVung, txtPhienAm, txtLoai, txtNghia;
    public Button btnView, btnDel, btnEdit;
    public RoundedImageView riv;

    public TuVungAdminHolder(@NonNull View itemView) {
        super(itemView);
        txtMaTV = itemView.findViewById(R.id.txtIDMaTV);
        txtMaBai = itemView.findViewById(R.id.txtIDMaBai);
        txtTuVung = itemView.findViewById(R.id.txtTenTuVung);
        txtPhienAm = itemView.findViewById(R.id.txtPhienAmTuVung);
        txtLoai = itemView.findViewById(R.id.txtLoaiTuVung);
        txtNghia = itemView.findViewById(R.id.txtNghiaTuVung);
        riv = itemView.findViewById(R.id.rivHinhAnh);
        btnView=itemView.findViewById(R.id.btnXemTuVung);
        btnDel=itemView.findViewById(R.id.btnXoaTuVung);
        btnEdit=itemView.findViewById(R.id.btnSuaTuVung);
    }


    public void setTxtMaTV(String string) {
        txtMaTV.setText(string);
    }
    public void setTxtMaBai(String string) {
        txtMaBai.setText(string);
    }
    public void setTxtTuVung(String string) {
        txtTuVung.setText(string);
    }
    public void setTxtPhienAm(String string) {
        txtPhienAm.setText(string);
    }
    public void setTxtLoai(String string) {
        txtLoai.setText(string);
    }
    public void setTxtNghia(String string) {
        txtNghia.setText(string);
    }
}
