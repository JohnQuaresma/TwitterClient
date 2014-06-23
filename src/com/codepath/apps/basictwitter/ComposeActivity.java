package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	
	private static final int MAX_CHARS = 140;
	private int charsLeft;
	private TwitterClient client;
	private User currentUser;
	private TextView tvTweetBtn;
	private TextView tvCharCount;
	private TextView tvUserName;
	private TextView tvUserScreenName;
	private ImageView ivUserProfileImage;
	private EditText etStatus;
	private boolean canTweet;

	private void disableTweetBtn() {
		if (!canTweet) {
			return;
		}
		canTweet = false;
		tvTweetBtn.setTextColor(Color.parseColor("#aaaaaa"));
		tvTweetBtn.setTypeface(null, Typeface.NORMAL);
	}
	
	private void enableTweetBtn() {
		if (canTweet) {
			return;
		}
		canTweet = true;
		tvTweetBtn.setTextColor(Color.parseColor("#33b5e5"));
		tvTweetBtn.setTypeface(null, Typeface.BOLD);
	}
	
	private void setupViews() {
		tvUserName.setText(currentUser.getName());
		tvUserScreenName.setText("@" + currentUser.getScreenName());
		ivUserProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(currentUser.getProfileImageUrl(), ivUserProfileImage);
	}
	
	private void setCharCountListener() {
		etStatus.addTextChangedListener(new TextWatcher() {          
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { 
            	   charsLeft = MAX_CHARS - s.length();
                   tvCharCount.setText(String.valueOf(charsLeft));
                   if (charsLeft < 10) {
                	   tvCharCount.setTextColor(Color.parseColor("#e60000"));
                   } else if (charsLeft < 20) {
                	   tvCharCount.setTextColor(Color.parseColor("#b30000"));
                   } else {
                	   tvCharCount.setTextColor(Color.parseColor("#aaaaaa"));
                   }
                   
                   if (charsLeft >= 0 && charsLeft < MAX_CHARS) {
                	   enableTweetBtn();
                   } else {
                	   disableTweetBtn();
                   }
            }                       
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub                          
            }                       
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub                          

            }
        });
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		canTweet = false;
		client = TwitterApplication.getRestClient();
		currentUser = (User) getIntent().getSerializableExtra(TimelineActivity.USER_PARAM);
		tvTweetBtn = (TextView) findViewById(R.id.tvTweetBtn);
		tvCharCount = (TextView) findViewById(R.id.tvCharCount);
		tvUserName = (TextView) findViewById(R.id.tvComposeUserName);
		tvUserScreenName = (TextView) findViewById(R.id.tvComposeUserScreenName);
		ivUserProfileImage = (ImageView) findViewById(R.id.ivComposeUserProfileImage);
		etStatus = (EditText) findViewById(R.id.etStatus);
		setupViews();
		setCharCountListener();
	}
	
	public void onCancel(View v) {
		finish();
	}
	
	public void onTweet(View v) {
		if (!canTweet) {
			return;
		}
		String status = etStatus.getText().toString();
		client.postStatusUpdate(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject jsonObject) {
				Tweet newTweet = Tweet.fromJSON(jsonObject);
				Intent i = new Intent();
				i.putExtra(TimelineActivity.TWEET_PARAM, newTweet);
				setResult(RESULT_OK, i);
				finish();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s.toString());
			}
		}, status);
	}
}
