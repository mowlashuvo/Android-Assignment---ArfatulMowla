package com.example.assignmentarfatulmowlashuvo.networking;

import com.example.assignmentarfatulmowlashuvo.model.BlogModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("db")
    Call<BlogModel> doGetListBlog();

}
