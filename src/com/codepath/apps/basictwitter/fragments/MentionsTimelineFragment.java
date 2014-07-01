package com.codepath.apps.basictwitter.fragments;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TimelineFragment {

	@Override
	protected void retrieveTimeline(JsonHttpResponseHandler responseHandler) {
		getClient().getMentionsTimeline(responseHandler);
	}

	@Override
	protected void retrieveTimelineBeforeTweet(
			JsonHttpResponseHandler responseHandler, Long oldestTweetId) {
		getClient().getMentionsTimeline(responseHandler, oldestTweetId);
	}
}
