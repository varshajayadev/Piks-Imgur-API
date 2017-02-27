package com.varshajayadev.piks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.varshajayadev.piks.ApiClient.page_Count;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String client_id = "Client-ID 13af23d1cdffdbb";
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    LinearLayoutManager mLayoutManager;
    private List<ImageResponse.Image> imageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the gallery images
        GalleryImages();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        imageAdapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(imageAdapter);
        //Detect end of scroll and get the next page of images in gallery
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!imageList.isEmpty() && mLayoutManager.findLastVisibleItemPosition() == imageList.size() - 1) {
                    GalleryImages();
                }
            }
        });
    }

    public void GalleryImages(){
        ApiInterface apiService = ApiClient.getClient("https://api.imgur.com/3/gallery/hot/viral/").create(ApiInterface.class);
        Call<ImageResponse> call = apiService.getGallery(client_id, page_Count);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                page_Count++;
                for(ImageResponse.Image i: response.body().getImage_List()){
                    imageList.add(i);
                }
                imageAdapter.notifyItemInserted(imageList.size()-1);
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                //TODO: Handle errors
                Log.d(TAG, "Response: Fail ");
            }
        });
    }
}
