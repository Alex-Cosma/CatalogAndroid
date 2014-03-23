/*
 * Copyright (C) 2013 Catalog Online Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.catalog.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.catalog.core.AppPreferences;
import com.catalog.core.AsyncTaskFactory;
import com.catalog.core.CatalogApplication;
import com.catalog.helper.Constants;
import com.catalog.model.LoginCredentials;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 * 
 * @author Alex
 */
public class LoginActivity extends Activity {

	/*
	 * Static members
	 */
	public static final String CLASS_NAME = Constants.LoginActivity;

	/*
	 * Public members
	 */
	public EditText mEmailView;
	public EditText mPasswordView;
	// Values for email and password at the time of the login attempt.
	public String mEmail;
	public String mPassword;

	/*
	 * Private members
	 */
	private AsyncTaskFactory asyncTaskFactory;
	private AsyncTask<Object, Void, Integer> loginTask;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private AppPreferences prefs;
	private LoginCredentials cred;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		setContentView(R.layout.activity_login);
		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);

		/**
		 * Important: this enables app preferences.
		 */
		prefs = AppPreferences.getInstance(this);
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(this).isIpExternal());

		// Set up the login form.
		cred = prefs.getLoginCredentials();
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(cred.getUsername());

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setText(cred.getPassword());
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						boolean bypass = false;
						if (bypass) {
							Intent intent = new Intent(getApplicationContext(),
									MenuActivity.class);
							startActivity(intent);
							finish();
						} else {
							InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

							inputManager.hideSoftInputFromWindow(
									getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
							attemptLogin();
						}

					}
				});

		// if (!cred.getPassword().equals("") && !cred.getUsername().equals(""))
		// {
		//
		// attemptLogin();
		// }
		prefs.setIpExternal(false);
		showIpDialog();

		EasyTracker.getInstance(this).activityStart(this);
	}

	private void showIpDialog() {

		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.choose_your_location)
				.setMessage(R.string.work_from_home)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								prefs.setIpExternal(true);
								if (!cred.getPassword().equals("")
										&& !cred.getUsername().equals("")) {
									CatalogApplication
											.getGaTracker()
											.send(MapBuilder
													.createEvent(
															Constants.UI_ACTION,
															Constants.UI_ACTION_WORK_FROM_HOME,
															null, null).build());

									attemptLogin();
								}
							}

						})
				.setNegativeButton(R.string.no,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								if (!cred.getPassword().equals("")
										&& !cred.getUsername().equals("")) {

									CatalogApplication
											.getGaTracker()
											.send(MapBuilder
													.createEvent(
															Constants.UI_ACTION,
															Constants.UI_ACTION_WORK_FROM_SCHOOL,
															null, null).build());

									attemptLogin();
								}
							}

						}).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		handleMenuItems(item);

		return super.onOptionsItemSelected(item);

	}

	private void handleMenuItems(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_about_app:

			Intent i = new Intent(this, AboutAppActivity.class);
			startActivity(i);

		}
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 0) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			loginTask = asyncTaskFactory.getTask(this, CLASS_NAME, "");
			loginTask.execute(mEmail, mPassword);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
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
