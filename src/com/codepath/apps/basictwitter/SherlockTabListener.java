package com.codepath.apps.basictwitter;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
 
public class SherlockTabListener<T extends SherlockFragment> implements TabListener {
	private SherlockFragment mFragment;
	private final SherlockFragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final int mfragmentContainerId;
        
    public SherlockTabListener(SherlockFragmentActivity activity, String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = android.R.id.content;
	}
	
    public SherlockTabListener(int fragmentContainerId, SherlockFragmentActivity activity,
			String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = fragmentContainerId;
	}
 
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (mFragment == null) {
			mFragment = (SherlockFragment) SherlockFragment
					.instantiate(mActivity, mClass.getName());
			ft.add(mfragmentContainerId, mFragment, mTag);
		} else {
			ft.attach(mFragment);
		}
	}
 
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (mFragment != null) {
			ft.detach(mFragment);
		}
	}
 
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// do nada
	}
}