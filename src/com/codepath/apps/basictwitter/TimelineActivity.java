package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	
	private static final int COMPOSE_REQUEST = 777;
	public static final String USER_PARAM = "current_user";
	public static final String TWEET_PARAM = "new_tweet";
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	private Tweet oldestTweet;
	private Tweet newTweet;
	private User currentUser;
	
	private JsonHttpResponseHandler getTimelineResponseHandler(final boolean isRefresh) {
		return new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonArray) {
				ArrayList<Tweet> responseTweets = Tweet.fromJSONArray(jsonArray);
				oldestTweet = responseTweets.get(responseTweets.size() - 1);
				if (isRefresh) {
					aTweets.clear();
				}
				aTweets.addAll(responseTweets);
				if (isRefresh) {
					lvTweets.onRefreshComplete();
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s.toString());
			}
		};
	}
	
	private JsonHttpResponseHandler getTimelineResponseHandler() {
		return getTimelineResponseHandler(false);
	}
	
	private void initializeTimeline(boolean isRefresh) {
		client.getHomeTimeline(getTimelineResponseHandler(isRefresh));
	}
	
	private void initializeTimeline() {
		initializeTimeline(false);
	}
	
	private void loadMoreTweets() {
		client.getHomeTimeline(getTimelineResponseHandler(), oldestTweet.getUid());
	}
	
	private void setupScrolling() {
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		    	 Log.d("DEBUG", String.format("page: %s - total count: %s.", page, totalItemsCount));
	             loadMoreTweets();
		    }
	    });
	}
	
	private void setupRefresh() {
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeTimeline(true);
            }
        });
	}
	
	private void loadCurrentUser() {
		client.getVerifyCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject jsonObject) {
				currentUser = User.fromJSON(jsonObject);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s.toString());
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (eu.erikw.PullToRefreshListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		setupRefresh();
		setupScrolling();
		loadCurrentUser();
		initializeTimeline();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		if (requestCode == COMPOSE_REQUEST) {
			newTweet = (Tweet) data.getSerializableExtra(TWEET_PARAM);
			tweets.add(0, newTweet);
			aTweets.notifyDataSetChanged();
			lvTweets.setSelectionAfterHeaderView();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }
	
	public void onCompose(MenuItem mi) {
		if (currentUser == null) {
			return;
		}
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra(USER_PARAM, currentUser);
		startActivityForResult(i, COMPOSE_REQUEST);
	}
}
