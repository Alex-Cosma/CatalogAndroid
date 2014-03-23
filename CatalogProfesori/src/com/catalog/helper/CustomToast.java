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
package com.catalog.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.catalog.activities.R;

/**
 * Custom Toast view which loads an XML layout and returns a toast according to
 * that layout.
 * 
 * @author Alex
 * 
 */
@SuppressLint("ViewConstructor")
public class CustomToast extends Toast {

	private Context c;
	private CharSequence s;

	public CustomToast(Context c, CharSequence s) {
		super(c);
		this.s = s;
		this.c = c;
		this.setView(getView());
		this.setDuration(Toast.LENGTH_SHORT);
	}

	public View getView() {
		LayoutInflater vi = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.toast_custom, null);
		TextView text = (TextView) v.findViewById(R.id.text);
		text.setText(s);
		return v;
	}

}
