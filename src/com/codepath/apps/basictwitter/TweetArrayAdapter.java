package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	private String formatNumberWithSuffix(String numberStr) {
		Long number = Long.parseLong(numberStr);
		if (number == 0) {
			return "";
		}
	    if (number < 1000) return "" + number;
	    int exp = (int) (Math.log(number) / Math.log(1000));
	    return String.format("%.1f%c", number / Math.pow(1000, exp), "KMB".charAt(exp-1));
	}
	
	
	private String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*Compensating for some weird difference between the android clock and GMT - it's literally seconds*/
		return relativeDate.startsWith("in ") ? "now" : relativeDate.replaceAll("([0-9]* [a-z])[a-z ]+", "$1");
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Tweet tweet = getItem(position);
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		RoundedImageView ivProfileImage = (RoundedImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvComposeUserName);
		TextView tvUserScreenName = (TextView) v.findViewById(R.id.tvUserScreenName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvCreatedAt = (TextView) v.findViewById(R.id.tvCreatedAt);
		TextView tvRetweets = (TextView) v.findViewById(R.id.tvRetweets);
		TextView tvFavorites = (TextView) v.findViewById(R.id.tvFavorites);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvUserScreenName.setText("@" + tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvCreatedAt.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		tvRetweets.setText(formatNumberWithSuffix(tweet.getRetweetCount()));
		tvFavorites.setText(formatNumberWithSuffix(tweet.getFavoriteCount()));
		
		ivProfileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((TweetListActivity) getContext()).openUserProfile(tweet.getUser());
			}
			
		});
		return v;
	}
}
