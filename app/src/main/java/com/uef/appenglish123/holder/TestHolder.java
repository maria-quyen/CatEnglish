package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class TestHolder extends RecyclerView.ViewHolder {
    public CheckBox chbA, chbB, chbC, chbD;
    public TextView txtCauHoi, txtDapAn;
    public LinearLayout linearCheck, checkA, checkB, checkC, checkD;

    public TestHolder(@NonNull View itemView) {
        super(itemView);

        linearCheck=(LinearLayout)itemView.findViewById(R.id.linearTest);
        checkA=(LinearLayout)itemView.findViewById(R.id.checkA);
        checkB=(LinearLayout)itemView.findViewById(R.id.checkB);
        checkC=(LinearLayout)itemView.findViewById(R.id.checkC);
        checkD=(LinearLayout)itemView.findViewById(R.id.checkD);
        chbA=(CheckBox) itemView.findViewById(R.id.checkBoxA);
        chbB=(CheckBox) itemView.findViewById(R.id.checkBoxB);
        chbC=(CheckBox) itemView.findViewById(R.id.checkBoxC);
        chbD=(CheckBox) itemView.findViewById(R.id.checkBoxD);
        txtCauHoi=(TextView) itemView.findViewById(R.id.tvCauHoiTN);
        txtDapAn=(TextView) itemView.findViewById(R.id.tvCauTL);

    }

    public void setTxtCauHoi(String string) {
        txtCauHoi.setText(string);
    }
    public void setTxtTraLoi(String string) {
        txtDapAn.setText(string);
    }


    public void chbA(String cauA) {
        chbA.setText(cauA);
    }

    public void chbB(String cauB) {
        chbB.setText(cauB);
    }

    public void chbC(String cauC) {
        chbC.setText(cauC);
    }

    public void chbD(String cauD) {
        chbD.setText(cauD);
    }
}
