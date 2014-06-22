package com.codepath.apps.basictwitter.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "tweets")
public class SampleModel extends Model {
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "userHandle")
	private String userHandle;
	
	@Column(name = "timestamp")
	private String timestamp;
	
	@Column(name = "body")
	private String body;
	
	public SampleModel() {
		super();
	}
	
	// Parse model from JSON
	public SampleModel(JSONObject object){
		super();

		try {
			this.userId = object.getString("user_id");
			this.userHandle = object.getString("user_username");
			this.timestamp = object.getString("timestamp");
			this.body = object.getString("body");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String getUserId() {
		return userId;
	}

	public String getUserHandle() {
		return userHandle;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getBody() {
		return body;
	}

	// Record Finders
	public static SampleModel byId(long id) {
	   return new Select().from(SampleModel.class).where("id = ?", id).executeSingle();
	}
	
	public static List<SampleModel> recentItems() {
      return new Select().from(SampleModel.class).orderBy("id DESC").limit("300").execute();
	}
}
