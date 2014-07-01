package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private static final long serialVersionUID = 782520282314155005L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String profileBannerUrl;
	private String followersCount;
	private String friendsCount;
	private String statusesCount;
	private boolean following;
	private boolean verified;
	
	public static User fromJSON(JSONObject jsonObject) {
		User u = new User();
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");
			try {
				u.profileBannerUrl = jsonObject.getString("profile_banner_url");
			} catch (JSONException e) {
				u.profileBannerUrl = null;
			}
			
			u.followersCount = jsonObject.getString("followers_count");
			u.friendsCount = jsonObject.getString("friends_count");
			u.statusesCount = jsonObject.getString("statuses_count");
			u.following = jsonObject.getBoolean("following");
			u.verified = jsonObject.getBoolean("verified");
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileBannerUrl() {
		return profileBannerUrl;
	}

	public String getFollowersCount() {
		return followersCount;
	}

	public String getFriendsCount() {
		return friendsCount;
	}

	public String getStatusesCount() {
		return statusesCount;
	}

	public boolean isFollowing() {
		return following;
	}

	public boolean isVerified() {
		return verified;
	}
}
