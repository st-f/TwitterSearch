package net.stf.twittersearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.GuestCallback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by st-f on 07/07/2016
 */

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int REFRESH_PERIOD = 30000;
    private ListView list;
    private TextView textView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.home_text);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle(query);
            loadTweets(query);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    loadTweets(query);
                }

            }, 0, REFRESH_PERIOD);
        }
    }

    private void loadTweets(String query) {
        showHideList(true);
        TwitterService.getInstance().search(query, new GuestCallback<>(new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                final boolean hasTweets = result.data.tweets.size() > 0;
                showHideList(hasTweets);
                if(!hasTweets) {
                    textView.setText(getString(R.string.no_results));
                } else {
                    ArrayList<ListAdapter.ListViewItemModel> listModel = getListViewItemModel(result);
                    ListAdapter listAdapter = new ListAdapter(context, 0, listModel);
                    list.setAdapter(listAdapter);
                }
            }

            @Override
            public void failure(TwitterException e) {
                Log.e(LOG_TAG, "getSearchService error: " + e.getMessage());
            }
        }));
    }

    @NonNull
    private ArrayList<ListAdapter.ListViewItemModel> getListViewItemModel(Result<Search> result) {
        final List<Tweet> tweets = result.data.tweets;
        ArrayList<ListAdapter.ListViewItemModel> listModel = new ArrayList<>();
        for (Tweet tweet : tweets) {
            ListAdapter.ListViewItemModel itemModel = new ListAdapter.ListViewItemModel();
            final List<MediaEntity> media = tweet.entities.media;
            itemModel.text = tweet.text;
            if (media != null) {
                final MediaEntity mediaEntity = media.get(0);
                if (mediaEntity != null && mediaEntity.type.equals("photo")) {
                    itemModel.imageURL = mediaEntity.mediaUrl;
                }
            }
            itemModel.profileImageURL = tweet.user.profileImageUrl;
            itemModel.profileName = "@"+tweet.user.screenName;
            listModel.add(itemModel);
        }
        return listModel;
    }

    private void showHideList(boolean visible) {
        list.setVisibility(visible ? View.VISIBLE : View.GONE);
        textView.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG, "onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
}
