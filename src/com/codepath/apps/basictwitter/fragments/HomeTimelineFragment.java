package com.codepath.apps.basictwitter.fragments;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TimelineFragment {
	
	public void addComposedTweet(Tweet newTweet) {
		add(0, newTweet);
		scrollTop();
	}

	@Override
	protected void retrieveTimeline(JsonHttpResponseHandler responseHandler) {
		getClient().getHomeTimeline(responseHandler);
	}

	@Override
	protected void retrieveTimelineBeforeTweet(
			JsonHttpResponseHandler responseHandler, Long oldestTweetId) {
		getClient().getHomeTimeline(responseHandler, oldestTweetId);
	}
}
