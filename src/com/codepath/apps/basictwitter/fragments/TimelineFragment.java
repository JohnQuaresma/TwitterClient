package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public abstract class TimelineFragment extends TweetsListFragment {
private Tweet oldestTweet;
	
	private JsonHttpResponseHandler getTimelineResponseHandler(final boolean isRefresh) {
		return new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonArray) {
				ArrayList<Tweet> responseTweets = Tweet.fromJSONArray(jsonArray);
				oldestTweet = responseTweets.get(responseTweets.size() - 1);
				if (isRefresh) {
					clear();
				}
				addAll(responseTweets);
				if (isRefresh) {
					onRefreshComplete();
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
		retrieveTimeline(getTimelineResponseHandler(isRefresh));
	}
	
	private void initializeTimeline() {
		initializeTimeline(false);
	}
	
	private void loadMoreTweets() {
		retrieveTimelineBeforeTweet(getTimelineResponseHandler(), oldestTweet.getUid());
	}
	
	private void setupScrolling() {
		setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		    	 Log.d("DEBUG", String.format("page: %s - total count: %s.", page, totalItemsCount));
	             loadMoreTweets();
		    }
	    });
	}
	
	private void setupRefresh() {
		setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeTimeline(true);
            }
        });
	}
	
	protected abstract void retrieveTimeline(JsonHttpResponseHandler responseHandler);
	
	protected abstract void retrieveTimelineBeforeTweet(JsonHttpResponseHandler responseHandler, Long oldestTweetId);
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeTimeline();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		setupRefresh();
		setupScrolling();
		return v;
	}
}
