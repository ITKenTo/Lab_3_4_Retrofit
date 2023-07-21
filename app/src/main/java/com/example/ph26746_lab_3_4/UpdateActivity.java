package com.example.ph26746_lab_3_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ph26746_lab_3_4.Adapter.SpinerTypeAdpter;
import com.example.ph26746_lab_3_4.Model.LoverModel;
import com.example.ph26746_lab_3_4.Model.TypeModel;
import com.example.ph26746_lab_3_4.databinding.ActivityUpdateBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;
    ApiService apiService;
    List<TypeModel> list;
    SpinerTypeAdpter adpter;
    String regex= "^[0-9]{10}$";
    String idType;
    String ID;
    int temp=0;
    Uri uri;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update);
        binding= ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService= RetrofitHelper.getService();
        list= new ArrayList<>();

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.txDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
            }
        });
        binding.edIdtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idType= list.get(position).get_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ID= getIntent().getStringExtra("ID");
        String NAME= getIntent().getStringExtra("NAME");
        String  phone= getIntent().getStringExtra("PHONE");
        String date= getIntent().getStringExtra("DATE");
        String des= getIntent().getStringExtra("DES");
        String id_type = getIntent().getStringExtra("TYPE");
        image = getIntent().getStringExtra("IMAGE");
        Log.d("TYPE", "onCreate: "+image);

        binding.edName.setText(NAME);
        binding.edDate.setText(date);
        binding.edPhone.setText(phone);
        binding.edDes.setText(des);
        String url="http://192.168.1.15:3000"+image;
        Glide.with(this).load(url).into(binding.viewImage);

        getListType();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get_id().equals(id_type)) {

                   binding.edIdtype.setSelection(i);
            }
        }

        binding.btnUpdateLover.setOnClickListener(v -> {
           Validate();
           if (temp==0){
               UpdateImage();
           }else {
               temp=0;
           }
        });
        binding.addImage.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        binding.view.setOnTouchListener((v, event) -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data.getData();

            // Use Uri object instead of File to avoid storage permissions
            binding.viewImage.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void Validate() {

//        if (uri==null) {
//            Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
//            temp++;
//        } else {
//
//        }

        if (binding.edName.getText().toString().isEmpty()) {
            binding.txName.setError("Name not isEmpty");
            binding.txName.setErrorEnabled(true);
            temp++;
        } else {
            binding.txName.setError("");
            binding.txName.setErrorEnabled(false);
        }

        if (binding.edPhone.getText().toString().isEmpty()) {
            binding.txPhone.setError("Phone not isEmpty");
            binding.txPhone.setErrorEnabled(true);
            temp++;
        } else {
            binding.txPhone.setError("");
            binding.txPhone.setErrorEnabled(false);
        }

        if (!binding.edPhone.getText().toString().matches(regex)) {
            binding.txPhone.setError("Phone illegal");
            binding.txPhone.setErrorEnabled(true);
            temp++;
        } else {
            binding.txPhone.setError("");
            binding.txPhone.setErrorEnabled(false);
        }

        if (binding.edDate.getText().toString().isEmpty()) {
            binding.txDate.setError("Date not isEmpty");
            binding.txDate.setErrorEnabled(true);
            temp++;
        } else {
            binding.txDate.setError("");
            binding.txDate.setErrorEnabled(false);
        }


        if (binding.edDes.getText().toString().isEmpty()) {
            binding.txDes.setError("Des not isEmpty");
            binding.txDes.setErrorEnabled(true);
            temp++;
        } else {
            binding.txDes.setError("");
            binding.txDes.setErrorEnabled(false);
        }

    }
    public void Update(){
        String name= binding.edName.getText().toString();
        String phone= binding.edPhone.getText().toString();
        String date= binding.edDate.getText().toString();
        String des= binding.edDes.getText().toString();

        apiService.putLover(ID,name,phone,date,idType,des).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(UpdateActivity.this, "Update Thành Công", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void UpdateImage(){
        String name= binding.edName.getText().toString();
        String phone= binding.edPhone.getText().toString();
        String date= binding.edDate.getText().toString();
        String des= binding.edDes.getText().toString();

        if (uri !=null){
            File imageFile = new File(uri.getPath());
            RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
            RequestBody requestPhone = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
            RequestBody requestdate = RequestBody.create(MediaType.parse("multipart/form-data"), date);
            RequestBody requestdes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
            RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), idType);

            RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestImageFile);
            apiService.updateImage(ID,requestName,requestPhone,requestdate,requestId,requestdes,imagePart).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("ADD", "onResponse: "+response.body());
                    Toast.makeText(UpdateActivity.this, "Sủa Thành Công", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t );
                }
            });
        }else {
//            RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
//            RequestBody requestPhone = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
//            RequestBody requestdate = RequestBody.create(MediaType.parse("multipart/form-data"), date);
//            RequestBody requestdes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
//            RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), idType);

//            RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
//            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "", requestImageFile);
            apiService.putLover(ID,name,phone,date,idType,des).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("ADD", "onResponse: "+response.body());
                    Toast.makeText(UpdateActivity.this, "Sủa Thành Công", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t );
                }
            });
        }
    }
    public void getListType(){
        apiService.getListType().enqueue(new Callback<List<TypeModel>>() {
            @Override
            public void onResponse(Call<List<TypeModel>> call, Response<List<TypeModel>> response) {
                if (response.isSuccessful()){
                    list=response.body();
                    adpter= new SpinerTypeAdpter(getApplicationContext(),list);
                    binding.edIdtype.setAdapter(adpter);


                }

            }

            @Override
            public void onFailure(Call<List<TypeModel>> call, Throwable t) {

            }
        });
    }



    private void datePick() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                binding.edDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, 2023, 7, 19);
        dialog.show();
    }
}