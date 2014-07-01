package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends TweetListActivity {
	
	private static final int COMPOSE_REQUEST = 777;
	public static final String TWEET_PARAM = "new_tweet";
	public static final String MENTIONS_TAG = "mentions";
	public static final String HOME_TAG = "home";
	private HomeTimelineFragment fragmentHomeTimeline;
	private User currentUser;
	private TwitterClient client;
	private Tab homeTab;
	private Tab mentionsTab;
	
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
	
	private void setupTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		homeTab = actionBar
			.newTab()
			.setText(R.string.timeline)
			.setIcon(R.drawable.ic_home)
			.setTabListener(
				new SherlockTabListener<HomeTimelineFragment>(R.id.flContainer, this, HOME_TAG,
						HomeTimelineFragment.class));

		actionBar.addTab(homeTab);
		actionBar.selectTab(homeTab);

		mentionsTab = actionBar
			.newTab()
			.setText(R.string.mentions)
			.setIcon(R.drawable.ic_mention)
			.setTabListener(
				new SherlockTabListener<MentionsTimelineFragment>(R.id.flContainer, this, MENTIONS_TAG,
						MentionsTimelineFragment.class));

		actionBar.addTab(mentionsTab);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		setupTabs();
		loadCurrentUser();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		if (requestCode == COMPOSE_REQUEST) {
			Tweet newTweet = (Tweet) data.getSerializableExtra(TWEET_PARAM);
			getSupportActionBar().selectTab(homeTab);
			((HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG)).addComposedTweet(newTweet);
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.tweets, menu);
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
	
	public void onProfile(MenuItem mi) {
		if (currentUser == null) {
			return;
		}
		openUserProfile(currentUser);
	}
}
