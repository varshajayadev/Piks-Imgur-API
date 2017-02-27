package com.varshajayadev.piks;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by varshajayadev on 2/24/17.
 */

public interface ApiInterface {

    //Download all the gallery images
    @GET("{pageCount}.json")
    Call<ImageResponse> getGallery(@Header("Authorization") String client_id, @Path("pageCount") int pCount);
    //Download an image
    @GET("{id}h.jpg")
    Call<ResponseBody> getImage(@Path("id") String imageid);
}
