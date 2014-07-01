package com.codepath.apps.basictwitter;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

public abstract class TweetListActivity extends SherlockFragmentActivity {
	public static final String USER_PARAM = "current_user";
	public static final String REPLY_USER_PARAM = "reply_user";
	public static final String REPLY_TWEET_PARAM = "reply_tweet";
	
	public void openUserProfile(User user) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(USER_PARAM, user);
		startActivity(i);
	}
	
	public void replyToTweet(User replyUser, Tweet replyTweet) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra(REPLY_USER_PARAM, replyUser);
		i.putExtra(REPLY_TWEET_PARAM, replyTweet);
		startActivity(i);
	}
}
