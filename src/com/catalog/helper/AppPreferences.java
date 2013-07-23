package com.catalog.helper;

import com.catalog.model.LoginCredentials;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
	private static final String APP_SHARED_PREFS = "com.catalog.prefs";

	private static AppPreferences appPrefs;

	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	public static AppPreferences getInstance(Context context) {
		if (appPrefs == null)
			if (context != null)
				appPrefs = new AppPreferences(context);
		return appPrefs;
	}

	/**
	 * 
	 * @param context
	 */
	private AppPreferences(Context context) {
		this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();

	}

	public LoginCredentials getLoginCredentials() {
		String username = appSharedPrefs.getString(Constants.Username, "");
		String password = appSharedPrefs.getString(Constants.Password, "");

		return new LoginCredentials(username, password);

	}

	public void saveLoginCredentials(String username, String password) {
		prefsEditor.putString(Constants.Username, username);
		prefsEditor.putString(Constants.Password, password);
		prefsEditor.commit();
	}

	public void resetLoginCredentials() {
		prefsEditor.putString(Constants.Username, "");
		prefsEditor.putString(Constants.Password, "");
		prefsEditor.commit();

	}

	public void setFirstEntry(boolean firstEntry) {
		prefsEditor.putBoolean(Constants.FirstEntry, firstEntry);
		prefsEditor.commit();

	}

	public boolean isFirstEntry() {
		return appSharedPrefs.getBoolean(Constants.FirstEntry, true);
	}

	public void setIpExternal(boolean ipIsExternal) {
		prefsEditor.putBoolean(Constants.IpExternal, ipIsExternal);
		prefsEditor.commit();
	}

	public boolean isIpExternal() {
		return appSharedPrefs.getBoolean(Constants.IpExternal, true);
	}

}
