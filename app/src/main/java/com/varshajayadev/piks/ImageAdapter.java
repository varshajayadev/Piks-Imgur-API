package com.varshajayadev.piks;

import android.content.Context;
import android.graphics.*;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import java.util.*;
import okhttp3.ResponseBody;
import retrofit2.*;

/**
 * Created by varshajayadev on 2/25/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<ImageResponse.Image> imageList;
    private Context context;
    private Connection conn;
    private AlertBox alert = new AlertBox();

    //Get the context and the image list to be displayed
    public ImageAdapter(Context context, List<ImageResponse.Image> imageList){
        this.context = context;
        this.imageList = imageList;
        conn = new Connection(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recyclerview_holder, null);
        ImageViewHolder ivh = new ImageViewHolder(layoutView);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        //Get the image object w.r.t recycler view position
        ImageResponse.Image i = imageList.get(position);
        String imageID = "";
        String tUp = String.valueOf(i.getUps());
        String tDown = String.valueOf(i.getDowns());
        holder.description.setText(i.getImage_Title());
        holder.image.setImageBitmap(null);

        //Check if the thumbs up and thumbs down values are set in each Image object
        if(tUp.equals(context.getResources().getString(R.string.zero))){
            holder.thumbsupText.setText(context.getResources().getString(R.string.zero));
        }else {
            holder.thumbsupText.setText(tUp);
        }

        if(tDown.equals(context.getResources().getString(R.string.zero))){
            holder.thumbsdownText.setText(context.getResources().getString(R.string.zero));
        }else {
            holder.thumbsdownText.setText(tDown);
        }
        //Check if the image is part of an album
        if(i.is_Album == true){
            imageID = i.getCover();
        }else {
            imageID = i.getImage_ID();
        }
        //Download the image
        loadBitmap(imageID, holder);
    }

    @Override
    public int getItemCount()  {
        return imageList.size();
    }

    private void loadBitmap(final String imageID, ImageViewHolder holder) {
        final Bitmap bitmap = LRUCache.getBitmapFromMemCache(imageID);

        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        } else {
            if(conn.isNetworkAvailable()){
                getImage(imageID, holder);
            }else {
                alert.show(context,context.getResources().getString(R.string.no_connection));
            }

        }
    }

    private void getImage(final String imageID, final ImageViewHolder holder){
        //Download the image for the corresponding recycler view position
        ApiInterface apiService = ApiClient.getClient(context.getResources().getString(R.string.image_api)).create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getImage(imageID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                displayImage(bm, imageID, holder);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                alert.show(context,context.getResources().getString(R.string.api_error));

            }
        });

    }

    //Display the image and store in LRUCache
    private void displayImage(Bitmap bm, String imageID,ImageViewHolder holder ){
        LRUCache.addBitmapToMemoryCache(imageID, bm);
        holder.image.setImageBitmap(bm);
    }
}
