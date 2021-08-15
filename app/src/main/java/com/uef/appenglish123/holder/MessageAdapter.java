package com.uef.appenglish123.holder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;
import com.uef.appenglish123.model.Chat;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int LEFT=0;
    public static final int RIGHT=1;

    private Context context;
    private List<Chat> list;
    private String img;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> list, String img) {
        this.context = context;
        this.list = list;
        this.img = img;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==RIGHT){
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat=list.get(position);
        holder.showTinNhan.setText(chat.getMessage());
        Picasso.get().load(img).into(holder.riv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView showTinNhan;
        public RoundedImageView riv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showTinNhan=itemView.findViewById(R.id.ChatShowTin);
            riv=itemView.findViewById(R.id.ChatShowHinh);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(list.get(position).getSender().equals(firebaseUser.getUid())){
            return RIGHT;
        }else {
            return LEFT;
        }
    }
}
