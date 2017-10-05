package com.example.dell.growup.network;

import com.example.dell.growup.network.result.AvatarResult;
import com.example.dell.growup.data.UserPreference;
import com.example.dell.growup.network.result.ApiResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by dell on 2017/10/5.
 */

public class RetrofitApi {

    private static final String BASE_URL = "https://pets.hustonline.net";
    public static Retrofit mRetrofit;
    private static RetrofitService service;

    private interface RetrofitService{

        /**Multipart单文件上传*/
        @Multipart
        @POST("/v1/user/change_head")
        ApiResult<AvatarResult> headerchange(@Part("token") String TOKEN,
                                             @Part("image") RequestBody imgs);
    }

    static {

        if(mRetrofit == null){

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ApiResult.class, new ApiResult.JsonAdapter())
                    .create();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor);

            OkHttpClient okHttpClient = builder.build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        service = mRetrofit.create(RetrofitService.class) ;
    }

    public static ApiResult<AvatarResult> headerchange(String uri) {

        File file = new File(uri);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return service.headerchange(UserPreference.getToken(), photoRequestBody);
    }
}
