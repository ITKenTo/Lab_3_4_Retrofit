package com.example.ph26746_lab_3_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ph26746_lab_3_4.Adapter.LoverAdapter;
import com.example.ph26746_lab_3_4.Model.LoverModel;
import com.example.ph26746_lab_3_4.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Iclicklisten {

     List<LoverModel> list;
     LoverAdapter adapter;
    ApiService apiService;

     ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.addLove.setOnClickListener(v -> {
            startActivity(new Intent(this, AddActivity.class));

        });
         apiService= RetrofitHelper.getService();
        list= new ArrayList<>();
        GridLayoutManager layoutManager= new GridLayoutManager(this,2);
        binding.recy.setLayoutManager(layoutManager);
        getAll();

    }

    public void getAll(){
        apiService.getList().enqueue(new Callback<List<LoverModel>>() {
            @Override
            public void onResponse(Call<List<LoverModel>> call, Response<List<LoverModel>> response) {
                if (response.isSuccessful()){
                    list=response.body();
                    Log.d("TAG", "onResponse: "+list.toString());
                    adapter= new LoverAdapter(getApplication(),list,MainActivity.this);
                    binding.recy.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<LoverModel>> call, Throwable t) {

                Log.e("TAG", "onFailure: "+t );
            }
        });
    }
    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("ID",list.get(pos).get_id());
        intent.putExtra("NAME",list.get(pos).getName());
        intent.putExtra("PHONE",list.get(pos).getPhone());
        intent.putExtra("DATE",list.get(pos).getDate());
        intent.putExtra("DES",list.get(pos).getDes());
        intent.putExtra("TYPE", list.get(pos).getId_type().get_id());
        intent.putExtra("IMAGE", list.get(pos).getImage());
        startActivity(intent);
    }

    @Override
    public void onLongClick(int pos) {

      //  Toast.makeText(this, ""+list.get(pos).get_id(), Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có muốn xóa 1 bản ghi không ?");

        builder.setNegativeButton("Ờ",(dialog, which) -> {
            apiService.deleteLover(list.get(pos).get_id()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("Delete", "onResponse: "+response.body());
                    Toast.makeText(MainActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    getAll();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        builder.setPositiveButton("Khong",(dialog, which) -> {
           dialog.dismiss();
        });
        AlertDialog dialog= builder.create();
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAll();
    }
}