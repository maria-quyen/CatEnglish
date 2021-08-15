package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class HomeHolder extends RecyclerView.ViewHolder {
    public TextView txtHome, txtMa;
    public ImageView imgHome, imgHinhNen;
    CardView cardView;

    public HomeHolder(@NonNull View itemView) {
        super(itemView);
        cardView=(CardView) itemView.findViewById(R.id.card);
        imgHinhNen=(ImageView) itemView.findViewById(R.id.imgHinhNen);;
        txtMa=(TextView) itemView.findViewById(R.id.txtMaHome);
        txtHome = (TextView) itemView.findViewById(R.id.txtHome);
        imgHome = (ImageView) itemView.findViewById(R.id.imgHome);
    }


    public void setTxtHome(String string) {
        txtHome.setText(string);
    }
    public void setTxtMa(String string) {
        txtMa.setText(string);
    }


}
