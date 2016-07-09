package net.stf.twittersearch;

import android.util.Log;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.GuestCallback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

/**
 * Created by st-f on 07/07/2016
 */

public class TwitterService {

    private static final String LOG_TAG = TwitterService.class.getSimpleName();
    private static TwitterService instance;
    private AppSession session;
    public String currentSearchTerm;

    private TwitterService() {
    }

    public static TwitterService getInstance() {
        if (instance == null) {
            instance = new TwitterService();
        }
        return instance;
    }

    public void login() {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                session = appSessionResult.data;
            }

            @Override
            public void failure(TwitterException e) {
                Log.e(LOG_TAG, "logInGuest error: " + e.getMessage());
            }
        });
    }

    public void search(String term, GuestCallback callback) {
        currentSearchTerm = term;
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
        twitterApiClient.getSearchService().tweets(currentSearchTerm, null, null, null, null, 50, null, null, null, true, callback);
    }
}
