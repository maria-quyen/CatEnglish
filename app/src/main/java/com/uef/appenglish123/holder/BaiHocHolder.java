package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class BaiHocHolder extends RecyclerView.ViewHolder {
    public TextView txtTenBai, txtMaBai, txtMaNhom;
    public LinearLayout linearLayout;
    public ImageView imgFlashCard, imgPuzzle, imgQuiz;

    public BaiHocHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout=itemView.findViewById(R.id.linearLesson);
        txtTenBai = itemView.findViewById(R.id.txtNameLesson);
        txtMaBai = itemView.findViewById(R.id.txtIdLesson);
        txtMaNhom = itemView.findViewById(R.id.txtIdGroup);
        imgFlashCard=itemView.findViewById(R.id.imgFlashcard);
        imgPuzzle=itemView.findViewById(R.id.imgPuzzle);
        imgQuiz=itemView.findViewById(R.id.imgQuiz);
    }

    public void setTxtTenBai(String string) {
        txtTenBai.setText(string);
    }
    public void setTxtMaNhom(String string) {
        txtMaNhom.setText(string);
    }
    public void setTxtMaBai(String string) {
        txtMaBai.setText(string);
    }

}
