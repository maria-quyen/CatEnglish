package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class QuizHolder extends RecyclerView.ViewHolder {
    public TextView txtCauHoi, txtTraLoi;
    public Button btnA, btnB, btnC, btnD, btnNext;
    public LinearLayout linearLayout;

    public QuizHolder(@NonNull View itemView) {
        super(itemView);

        linearLayout=itemView.findViewById(R.id.linearQuestion_);
        txtCauHoi=(TextView) itemView.findViewById(R.id.txtQuestion);
        txtTraLoi=(TextView) itemView.findViewById(R.id.txtAnswer);
        btnA=(Button) itemView.findViewById(R.id.btnOptionA);
        btnB=(Button) itemView.findViewById(R.id.btnOptionB);
        btnC=(Button) itemView.findViewById(R.id.btnOptionC);
        btnD=(Button) itemView.findViewById(R.id.btnOptionD);
        btnNext=(Button) itemView.findViewById(R.id.btnNextQuestion);
    }

    public void setTxtCauHoi(String string) {
        txtCauHoi.setText(string);
    }
    public void setTxtTraLoi(String string) {
        txtTraLoi.setText(string);
    }

    public void btnA(String cauA) {
        btnA.setText(cauA);
    }

    public void btnB(String cauB) {
        btnB.setText(cauB);
    }

    public void btnC(String cauC) {
        btnC.setText(cauC);
    }

    public void btnD(String cauD) {
        btnD.setText(cauD);
    }
}
