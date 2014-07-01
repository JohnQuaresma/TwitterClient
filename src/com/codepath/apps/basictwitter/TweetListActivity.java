package com.codepath.apps.basictwitter;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.basictwitter.models.User;

public abstract class TweetListActivity extends SherlockFragmentActivity {
	public static final String USER_PARAM = "current_user";
	
	public void openUserProfile(User user) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(USER_PARAM, user);
		startActivity(i);
	}
}
