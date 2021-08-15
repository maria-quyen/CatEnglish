package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class LuyenThiHolder extends RecyclerView.ViewHolder {
    public TextView txtMaBai, txtTenBai, txtCoin;
    public LinearLayout linearLayout;

    public LuyenThiHolder(@NonNull View itemView) {
        super(itemView);

        linearLayout=itemView.findViewById(R.id.linearTests);
        txtMaBai=(TextView) itemView.findViewById(R.id.txtMaBaiTest);
        txtTenBai=(TextView) itemView.findViewById(R.id.txtTenBaiTest);
        txtCoin=(TextView) itemView.findViewById(R.id.txtCoin);
    }

    public void setTxtMaBai(String string) {
        txtMaBai.setText(string);
    }
    public void setTxtTenBai(String string) {
        txtTenBai.setText(string);
    }
    public void setTxtCoin(String string) {
        txtCoin.setText(string);
    }
}
