package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class TuVungHolder extends RecyclerView.ViewHolder {
    public TextView txtMaTV, txtMaBai, txtTuVung, txtPhienAm, txtNghia, txtLinkPhatAm, txtViDu;
    public LinearLayout linearLayout, linearDetail;
    public ImageView imgTuVung;
    public Button btnSpeaker, btnSnail;

    public TuVungHolder(@NonNull View itemView) {
        super(itemView);
        linearDetail=itemView.findViewById(R.id.linearVocabulary);;
        linearLayout=itemView.findViewById(R.id.btnViewDetail);
        btnSpeaker=itemView.findViewById(R.id.btnSpeaker);
        btnSnail=itemView.findViewById(R.id.btnSnail);
        txtMaTV = itemView.findViewById(R.id.txtMaTuVung);
        txtMaBai = itemView.findViewById(R.id.txtMaBaiTuVung);
        txtTuVung = itemView.findViewById(R.id.txtTuVung);
        txtPhienAm = itemView.findViewById(R.id.txtPhienAm);
        txtNghia = itemView.findViewById(R.id.txtNghia);
        txtLinkPhatAm=itemView.findViewById(R.id.txtLinkPhatAm);
        txtViDu=itemView.findViewById(R.id.txtViDu);
        imgTuVung = itemView.findViewById(R.id.imgTuVung);
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
    public void setTxtNghia(String string) {
        txtNghia.setText(string);
    }
    public void setTxtLinkPhatAm(String string) {
        txtLinkPhatAm.setText(string);
    }
    public void setTxtViDu(String string) {
        txtViDu.setText(string);
    }



}
