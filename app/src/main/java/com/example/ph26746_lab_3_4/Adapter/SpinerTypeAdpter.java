package com.example.ph26746_lab_3_4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ph26746_lab_3_4.Model.TypeModel;
import com.example.ph26746_lab_3_4.R;

import java.util.ArrayList;
import java.util.List;

public class SpinerTypeAdpter extends ArrayAdapter<TypeModel> {
    List<TypeModel> list;
    TextView tv_name;

    public SpinerTypeAdpter(@NonNull Context context, List<TypeModel> list) {
        super(context, 0,list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder=convertView;
        if (holder==null){
            LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder=inflater.inflate(R.layout.item_spiner,null,false);
        }
        final TypeModel ls=list.get(position);
        if (ls !=null){
            tv_name=holder.findViewById(R.id.tv_name_type);
            tv_name.setText(ls.getName());
        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder=convertView;
        if (holder==null){
            LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder=inflater.inflate(R.layout.item_spiner,null,false);
        }
        final TypeModel ls=list.get(position);
        if (ls !=null){
            tv_name=holder.findViewById(R.id.tv_name_type);
            tv_name.setText(ls.getName());
        }
        return holder;
    }
}
