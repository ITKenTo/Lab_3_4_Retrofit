package com.example.ph26746_lab_3_4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ph26746_lab_3_4.Iclicklisten;
import com.example.ph26746_lab_3_4.Model.LoverModel;
import com.example.ph26746_lab_3_4.R;

import java.util.List;

public class LoverAdapter extends RecyclerView.Adapter<LoverAdapter.ViewHolder> {

   private final Iclicklisten iclicklisten;
    private List<LoverModel> list;
    Context context;

    public LoverAdapter(  Context context,List<LoverModel> list,Iclicklisten iclicklisten) {
        this.context=context;
        this.list = list;
        this.iclicklisten=iclicklisten;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lover,parent,false);
        return new ViewHolder(view,iclicklisten);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LoverModel loverModel= list.get(position);


        holder.tv_name.setText(loverModel.getName());
        holder.tv_phone.setText(loverModel.getPhone());
        holder.tv_idtype.setText(loverModel.getId_type().getName());
        holder.tv_des.setText(loverModel.getDes());
        holder.tv_date.setText(loverModel.getDate());
        if (loverModel.getImage()==null) {
            holder.imageView.setVisibility(View.GONE);

        }else {
            String url="http://192.168.1.15:3000"+loverModel.getImage();
            Glide.with(context).load(url).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(v -> {
            if (iclicklisten!=null) {
                iclicklisten.onClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (iclicklisten!=null) {
                iclicklisten.onLongClick(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_phone, tv_des,tv_idtype,tv_date;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView, Iclicklisten iclicklisten) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_idtype=itemView.findViewById(R.id.tv_type);
            tv_des=itemView.findViewById(R.id.tv_des);
            tv_date=itemView.findViewById(R.id.tv_date);
            imageView=itemView.findViewById(R.id.img_lover);
            itemView.setOnClickListener(v -> {
                int pos= getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION) {
                    iclicklisten.onClick(pos);
                }
            });

            itemView.setOnLongClickListener(v -> {
                int pos= getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION) {
                    iclicklisten.onLongClick(pos);
                }
                return true;
            });
        }
    }
}
