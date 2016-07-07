package net.stf.twittersearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by st-f on 07/07/2016
 */

public class VolleySingleton {

    private static VolleySingleton mInstance = null;
    private final ImageLoader mImageLoader;

    private VolleySingleton(Context context) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(50);

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }
}