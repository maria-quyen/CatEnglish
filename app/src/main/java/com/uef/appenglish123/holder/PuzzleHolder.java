package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class PuzzleHolder extends RecyclerView.ViewHolder {
    public TextView txtTuVung, txtNghia;
    public ImageView imgHinhAnh;
    public Button btnSkip, btnNext;
    public GridView gridTraLoi, gridDapAn;

    public PuzzleHolder(@NonNull View itemView) {
        super(itemView);

        btnSkip=(Button) itemView.findViewById(R.id.btnSkip);
        btnNext=(Button) itemView.findViewById(R.id.btnNextCautiep);
        txtTuVung = (TextView) itemView.findViewById(R.id.txtTuVungPuzzle);
        txtNghia = (TextView) itemView.findViewById(R.id.txtCauHoiPuzzle);
        imgHinhAnh = (ImageView) itemView.findViewById(R.id.imgHinhAnhPuzzle);
        gridDapAn=(GridView) itemView.findViewById(R.id.gridDapAn);
        gridTraLoi=(GridView) itemView.findViewById(R.id.gridTraLoi);
    }


    public void setTxtTuVung(String string) {
        txtTuVung.setText(string);
    }
    public void setTxtNghia(String string) {
        txtNghia.setText(string);
    }
}
