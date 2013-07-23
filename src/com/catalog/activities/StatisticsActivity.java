package com.catalog.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catalog.activities.extras.AsyncTaskFactory;
import com.catalog.helper.AppPreferences;
import com.catalog.helper.Constants;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class StatisticsActivity extends Activity {

	/*
	 * Static members
	 */
	private static String CLASSNAME = Constants.StatisticsActivity;
	private static String CHART_NAME = "name";

	/*
	 * Public members
	 */

	/*
	 * Private members
	 */
	private Context ctx;
	private ListView listview;
	private AsyncTaskFactory asyncTaskFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_statistics);

		initUi();
	}

	private void initUi() {
		ctx = this;
		asyncTaskFactory = AsyncTaskFactory.getInstance(AppPreferences
				.getInstance(this).isIpExternal());

		listview = (ListView) findViewById(R.id.lvChartsNames);

		listview.setAdapter(new SimpleAdapter(this, getListValues(),
				android.R.layout.simple_list_item_2,
				new String[] { CHART_NAME }, new int[] { android.R.id.text1 }));

		listview.setOnItemClickListener(new ChartListviewListner());

	}

	private List<Map<String, String>> getListValues() {
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();

		int length = Constants.CHARTS_NAMES.length;
		for (int i = 0; i < length; i++) {
			Map<String, String> v = new HashMap<String, String>();
			v.put(CHART_NAME, Constants.CHARTS_NAMES[i]);
			values.add(v);
		}
		return values;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

	class ChartListviewListner implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {

			asyncTaskFactory.getTask(ctx, CLASSNAME, "").execute(
					Constants.CHARTS_NAMES[position]);

		}
	}

	public void showLoading() {
		((ProgressBar) findViewById(R.id.progressBar))
				.setVisibility(View.VISIBLE);
		listview.setEnabled(false);
	}

	public void hideLoading() {
		((ProgressBar) findViewById(R.id.progressBar))
				.setVisibility(View.INVISIBLE);
		listview.setEnabled(true);
	}

}
