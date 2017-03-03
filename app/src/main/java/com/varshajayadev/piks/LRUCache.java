package com.varshajayadev.piks;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import static android.R.attr.bitmap;

/**
 * Created by varshajayadev on 3/1/17.
 */

public class LRUCache {

    private static LruCache<String, Bitmap> mMemoryCache;
    private static LRUCache lruCache = null;
    private LRUCache(){}

    public static LRUCache createCache(){

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory / 8;
        if (lruCache == null){
            lruCache = new LRUCache();
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }

                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                    super.entryRemoved(evicted, key, oldValue, newValue);
                    oldValue.recycle();
                }
            };
        }
        return lruCache;
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }




}
