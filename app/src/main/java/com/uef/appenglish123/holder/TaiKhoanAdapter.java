package com.uef.appenglish123.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.model.TaiKhoan;

import java.util.List;
import com.uef.appenglish123.R;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {

    private Context context;
    private List<TaiKhoan> list;

    public TaiKhoanAdapter(Context context, List<TaiKhoan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new TaiKhoanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaiKhoan tk=list.get(position);
        holder.txtTenTK.setText(tk.getTen());
        holder.txtEmail.setText(tk.getEmail());
        Picasso.get().load(tk.getHinh()).into(holder.rivHinhTK);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenTK, txtEmail;
        RoundedImageView rivHinhTK;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenTK=itemView.findViewById(R.id.tvTenTKChat);
            txtEmail=itemView.findViewById(R.id.tvTinMoiNhat);
            rivHinhTK=itemView.findViewById(R.id.riv_TaiKhoan);
        }
    }
}
