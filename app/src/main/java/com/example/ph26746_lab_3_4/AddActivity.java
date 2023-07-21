package com.example.ph26746_lab_3_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.ph26746_lab_3_4.Adapter.SpinerTypeAdpter;
import com.example.ph26746_lab_3_4.Model.TypeModel;
import com.example.ph26746_lab_3_4.databinding.ActivityAddBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    ApiService apiService;
    String regex= "^[0-9]{10}$";
    List<TypeModel> listType;
    SpinerTypeAdpter adpter;
    String id_type;
    Uri uri;
    int temp=0;
    File file;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService= RetrofitHelper.getService();
        listType=new ArrayList<>();

        binding.edIdtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  id_type=listType.get(position).get_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.btnAddLover.setOnClickListener(v -> {
                    Validate();
                    if (temp==0) {
                      //  Add();
                        AddImage();
                    }else {
                        temp=0;
                    }

                }
        );

        binding.txDate.setStartIconOnClickListener(v -> {
            datePick();
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
        getType();

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

    public void reset(){
        binding.edName.setText("");
        binding.edPhone.setText("");
        binding.edDate.setText("");
        binding.edDes.setText("");
    }
    public void Add(){
        String name= binding.edName.getText().toString();
        String phone= binding.edPhone.getText().toString();
        String date= binding.edDate.getText().toString();
        String des= binding.edDes.getText().toString();
        apiService.addObj(name,phone,date,id_type,des).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("ADD", "onResponse: "+response.body());
                reset();
                Toast.makeText(AddActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void AddImage(){
        String name= binding.edName.getText().toString();
        String phone= binding.edPhone.getText().toString();
        String date= binding.edDate.getText().toString();
        String des= binding.edDes.getText().toString();
        File imageFile = new File(uri.getPath());
        RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestPhone = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody requestdate = RequestBody.create(MediaType.parse("multipart/form-data"), date);
        RequestBody requestdes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
        RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), id_type);

        RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestImageFile);
        apiService.addObjImage(requestName,requestPhone,requestdate,requestId,requestdes,imagePart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("ADD", "onResponse: "+response.body());
                reset();
                Toast.makeText(AddActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t );
            }
        });
    }

    private void Validate() {

        if (uri==null) {
            Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
            temp++;
        } else {

        }

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

    public void getType(){
        apiService.getListType().enqueue(new Callback<List<TypeModel>>() {
            @Override
            public void onResponse(Call<List<TypeModel>> call, Response<List<TypeModel>> response) {
                listType=response.body();
                //ArrayAdapter<TypeModel> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listType);
                adpter= new SpinerTypeAdpter(getApplication(),listType);
                binding.edIdtype.setAdapter(adpter);
                Log.d("TAG111", "onResponse: "+response.body());
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