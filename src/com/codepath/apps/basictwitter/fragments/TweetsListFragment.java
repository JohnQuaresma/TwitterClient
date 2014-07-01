package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends SherlockFragment {
	
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	private TwitterClient client;
	
	protected TwitterClient getClient() {
		return client;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//non-view initialization
		client = TwitterApplication.getRestClient();
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//inflate layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		//assign view references
		lvTweets = (eu.erikw.PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		//return view
		return v;
	}
	
	public void clear() {
		aTweets.clear();
	}
	
	public void add(int position, Tweet tweet) {
		tweets.add(position, tweet);
		aTweets.notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public void scrollTop() {
		lvTweets.setSelectionAfterHeaderView();
	}
	
	public void setOnScrollListener(OnScrollListener listener) {
		lvTweets.setOnScrollListener(listener);
	}
	
	public void setOnRefreshListener(OnRefreshListener listener) {
		lvTweets.setOnRefreshListener(listener);
	}
	
	public void onRefreshComplete() {
		lvTweets.onRefreshComplete();
	}
}
