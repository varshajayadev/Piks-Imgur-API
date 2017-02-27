package com.varshajayadev.piks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by varshajayadev on 2/25/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<ImageResponse.Image> imageList;

    public ImageAdapter(List<ImageResponse.Image> imageList){
        this.imageList = imageList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recyclerview_holder, null);
        ImageViewHolder ivh = new ImageViewHolder(layoutView);
        return ivh;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        String imageID = "";
        ImageResponse.Image i = imageList.get(position);
        holder.description.setText(i.getImage_Title());
        holder.image.setImageBitmap(null);
        if(i.is_Album == true){
            imageID = i.getCover();
        }else {
            imageID = i.getImage_ID();
        }
        //Download the image for the corresponding recycler view position
        ApiInterface apiService = ApiClient.getClient("http://i.imgur.com/").create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getImage(imageID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                holder.image.setImageBitmap(bm);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Response: Fail ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
