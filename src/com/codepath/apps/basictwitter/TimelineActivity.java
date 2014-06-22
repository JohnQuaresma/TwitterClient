package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private Tweet oldestTweet;
	
	private JsonHttpResponseHandler getTimelineResponseHandler() {
		return new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonArray) {
				ArrayList<Tweet> responseTweets = Tweet.fromJSONArray(jsonArray);
				oldestTweet = responseTweets.get(responseTweets.size() - 1);
				aTweets.addAll(responseTweets);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s.toString());
			}
		};
	}
	
	private void initializeTimeline() {
		client.getHomeTimeline(getTimelineResponseHandler());
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (ListView)findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		setupScrolling();
		initializeTimeline();
	}
}
