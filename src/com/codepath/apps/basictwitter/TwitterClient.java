package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1/";
    public static final String REST_CONSUMER_KEY = "xDy17U1h0dsdGNysCMwafHELB";
    public static final String REST_CONSUMER_SECRET = "VcNHmtyhA2JntdvPnC63hi9JgR5grgD2SXSnVzUpCUUxHPlXLk";
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; 
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
    	getHomeTimeline(handler, null);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler, Long maxId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = null;
        
        if (maxId != null) {
        	params = new RequestParams();
        	params.put("max_id", maxId.toString());
        }
        
        client.get(apiUrl, params, handler);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
    	getMentionsTimeline(handler, null);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler, Long maxId) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = null;
        
        if (maxId != null) {
        	params = new RequestParams();
        	params.put("max_id", maxId.toString());
        }
        
        client.get(apiUrl, params, handler);
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler, Long userId) {
    	getUserTimeline(handler, userId, null);
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler, Long userId, Long maxId) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = null;
        params = new RequestParams();
    	params.put("user_id", userId.toString());
        
        if (maxId != null) {
        	params.put("max_id", maxId.toString());
        }
        
        client.get(apiUrl, params, handler);
    }
    
    public void getVerifyCredentials(AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("account/verify_credentials.json");
    	client.get(apiUrl,  null, handler);
    }
    
    public void postStatusUpdate(AsyncHttpResponseHandler handler, String status) {
    	String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
    	client.post(apiUrl, params, handler);
    }
}