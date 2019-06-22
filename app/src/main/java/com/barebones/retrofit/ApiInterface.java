package com.barebones.retrofit;

import com.barebones.models.Comments;
import com.barebones.models.Video;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("api/workshop/video")
    Call<Video> getVideoDetails();

    @GET("api/workshop/comments")
    Call<List<Comments>> getVideoComments();

    @POST("api/workshop/comments")
    Call<Comments> postVideoComments(@Field("user") String user,
                                         @Field("comment") String comment);

}

