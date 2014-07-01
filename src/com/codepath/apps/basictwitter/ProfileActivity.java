package com.codepath.apps.basictwitter;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends SherlockFragmentActivity {
	
	private TwitterClient client;
	private User currentUser;
	private ImageView ivProfileBackground;
	private RoundedImageView ivPageProfileImage;
	private TextView tvPageProfileName;
	private TextView tvPageProfileScreenName;
	private TextView tvTweets;
	private TextView tvFollowing;
	private TextView tvFollowers;
	private UserTimelineFragment fragmentUserTimeline;
	
	private String formatNumberWithSuffix(String numberStr) {
		Long number = Long.parseLong(numberStr);
	    if (number < 1000) return "" + number;
	    int exp = (int) (Math.log(number) / Math.log(1000));
	    return String.format("%.1f%c", number / Math.pow(1000, exp), "KMB".charAt(exp-1));
	}
	
	private void setupViews() {
		tvPageProfileName.setText(currentUser.getName());
		tvPageProfileScreenName.setText("@" + currentUser.getScreenName());
		tvTweets.setText(formatNumberWithSuffix(currentUser.getStatusesCount()));
		tvFollowers.setText(formatNumberWithSuffix(currentUser.getFollowersCount()));
		tvFollowing.setText(formatNumberWithSuffix(currentUser.getFriendsCount()));
		ivProfileBackground.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(currentUser.getProfileBannerUrl(), ivProfileBackground);
		ivPageProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader profileImageLoader = ImageLoader.getInstance();
		profileImageLoader.displayImage(currentUser.getProfileImageUrl(), ivPageProfileImage);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		client = TwitterApplication.getRestClient();
		currentUser = (User) getIntent().getSerializableExtra(TimelineActivity.USER_PARAM);
		fragmentUserTimeline = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
		fragmentUserTimeline.setUserId(currentUser.getUid());
		tvTweets = (TextView) findViewById(R.id.tvTweets);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		ivProfileBackground = (ImageView) findViewById(R.id.ivProfileBackground);
		ivPageProfileImage = (RoundedImageView) findViewById(R.id.ivPageProfileImage);
		tvPageProfileName = (TextView) findViewById(R.id.tvPageProfileName);
		tvPageProfileScreenName = (TextView) findViewById(R.id.tvPageProfileScreenName);
		setupViews();
	}
	
	
}
