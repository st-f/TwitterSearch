package net.stf.twittersearch;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by st-f on 07/07/2016
 */

public class Application extends android.app.Application {

    private static final String CONSUMER_KEY = "yxSo2JN8sGUkk7GFZQ9N9CJsl";
    private static final String CONSUMER_SECRET = "NAKFN5r1NJ1kfkxIbsj7NEv0njd9ziaqWmwhl2cvsc6u28ri3h";
    private static Application mAppInstance = null;

    public static Application getInstance() {
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        TwitterService.getInstance().login();
    }
}