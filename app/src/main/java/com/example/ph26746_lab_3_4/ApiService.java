package com.example.ph26746_lab_3_4;

import com.example.ph26746_lab_3_4.Model.LoverModel;
import com.example.ph26746_lab_3_4.Model.TypeModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/listlover")
    Call<List<LoverModel>> getList();

    @GET("api/listtype")
    Call<List<TypeModel>> getListType();
    @DELETE("api/deletelover/{id}")
    Call<Void> deleteLover(@Path("id") String id);
    @FormUrlEncoded
    @PUT("api/updatelover/{idL}")
    Call<ResponseBody> putLover(
            @Path("idL") String id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("date") String date,
            @Field("typeId") String id_type,
            @Field("des") String des
    );

    @FormUrlEncoded
    @POST("lover/addlover")
    Call<ResponseBody> addObj(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("date") String date,
            @Field("typeId") String id_type,
            @Field("desc") String des
    );
    @Multipart
    @POST("api/addlover")
    Call<Void> addObjImage(
            @Part("name") RequestBody name,
            @Part("phone") RequestBody  phone,
            @Part("date") RequestBody  date,
            @Part("typeId") RequestBody  id_type,
            @Part("des") RequestBody  des,
            @Part MultipartBody.Part imageFile
    );

    @Multipart
    @PUT("api/updatelover/{idL}")
    Call<Void> updateImage(
            @Path("idL") String id,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody  phone,
            @Part("date") RequestBody  date,
            @Part("typeId") RequestBody  id_type,
            @Part("des") RequestBody  des,
            @Part MultipartBody.Part imageFile
    );
}
