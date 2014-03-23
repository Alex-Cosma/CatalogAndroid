package com.catalog.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class AboutAppActivity extends Activity {
	private static final String CLASS_NAME = Constants.AboutAppActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_app);
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_app, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		CatalogApplication.getGaTracker().send(
				MapBuilder.createAppView().build());
	}

	@Override
	public void onStop() {
		super.onStop();
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, null);
	}

}
