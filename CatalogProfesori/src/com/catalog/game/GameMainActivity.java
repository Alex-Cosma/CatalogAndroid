/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.catalog.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.catalog.activities.R;
import com.catalog.core.CatalogApplication;
import com.catalog.game.GameView.State;
import com.catalog.helper.Constants;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class GameMainActivity extends Activity {
	private static final String CLASS_NAME = Constants.GameMainActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		EasyTracker.getInstance(this).activityStart(this);

		findViewById(R.id.start_player).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						startGame(true);
					}
				});

		findViewById(R.id.start_comp).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startGame(false);
			}
		});

		CatalogApplication.getGaTracker().set(Fields.SCREEN_NAME, CLASS_NAME);
	}

	private void startGame(boolean startWithHuman) {
		Intent i = new Intent(this, GameActivity.class);
		i.putExtra(
				GameActivity.EXTRA_START_PLAYER,
				startWithHuman ? State.PLAYER1.getValue() : State.PLAYER2
						.getValue());
		startActivity(i);
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