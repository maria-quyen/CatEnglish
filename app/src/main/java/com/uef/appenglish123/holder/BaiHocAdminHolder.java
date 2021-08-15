package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class BaiHocAdminHolder extends RecyclerView.ViewHolder {
    public TextView txtTenBaiHoc;
    public ImageView imgView, imgDel, imgEdit;


    public BaiHocAdminHolder(@NonNull View itemView) {
        super(itemView);
        txtTenBaiHoc = itemView.findViewById(R.id.txtTenBaiLesson);
        imgView=itemView.findViewById(R.id.imgViewLesson);
        imgDel=itemView.findViewById(R.id.imgDelLesson);
        imgEdit=itemView.findViewById(R.id.imgEditLesson);
    }

    public void setTxtTenBaiHoc(String string) {
        txtTenBaiHoc.setText(string);
    }
}
