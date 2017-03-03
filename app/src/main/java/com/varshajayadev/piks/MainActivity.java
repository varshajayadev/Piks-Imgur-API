package com.varshajayadev.piks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;

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
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private LinearLayoutManager mLayoutManager;
    private AlertBox alert = new AlertBox();
    private List<ImageResponse.Image> imageList = new ArrayList<>();
    private Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conn = new Connection(this);
        //Get the gallery images and check for Internet connection
        LRUCache.createCache();

        //Initializing recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        imageAdapter = new ImageAdapter(this, imageList);
        recyclerView.setAdapter(imageAdapter);
        if(conn.isNetworkAvailable()){
            fetchGalleryImages();
        }else {
            alert.show(this,this.getResources().getString(R.string.no_connection));
        }
        //Detect end of scroll and get the next page of images in gallery
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!imageList.isEmpty() && mLayoutManager.findLastVisibleItemPosition() == imageList.size() - 1) {
                    if(conn.isNetworkAvailable()){
                        fetchGalleryImages();
                    }else {
                        alert.show(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_connection));
                    }
                }
            }
        });
    }

    public void fetchGalleryImages(){
        ApiInterface apiService = ApiClient.getClient(getResources().getString(R.string.gallery_api)).create(ApiInterface.class);
        Call<ImageResponse> call = apiService.getGallery(getResources().getString(R.string.client_id), page_Count);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                //Increment the page number when requesting new images
                page_Count++;
                for(ImageResponse.Image i: response.body().getImage_List()){
                    imageList.add(i);
                }
                imageAdapter.notifyItemInserted(imageList.size()-1);
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                //Display dialog box for downloading image error
                alert.show(getApplicationContext(),getResources().getString(R.string.api_error));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
