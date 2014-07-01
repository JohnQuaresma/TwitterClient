package com.codepath.apps.basictwitter.fragments;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TimelineFragment {

	private Long userId;
	
	@Override
	protected void retrieveTimeline(JsonHttpResponseHandler responseHandler) {
		getClient().getUserTimeline(responseHandler, userId);
	}

	@Override
	protected void retrieveTimelineBeforeTweet(
			JsonHttpResponseHandler responseHandler, Long oldestTweetId) {
		getClient().getUserTimeline(responseHandler, userId, oldestTweetId);
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
