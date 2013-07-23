/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
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
package com.catalog.activities.charts;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint.Align;

import com.catalog.model.ChartData;

/**
 * Sales demo bar chart.
 */
public class BarChart extends AbstractChart {

	public BarChart(Context context, List<ChartData> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	void setupSmallRenderer(XYSeriesRenderer aRenderer, ChartData d, int i) {
		aRenderer.setLineWidth(7);

		if (!(i > colors.length)) {
			aRenderer.setColor(colors[i]);
		} else {
			Random r = new Random();
			aRenderer.setColor(colors[r.nextInt(colors.length)]);
		}
		aRenderer.setFillPoints(true);
		aRenderer.setChartValuesSpacing(75);
		aRenderer.setDisplayChartValues(true);
		aRenderer.setChartValuesTextAlign(Align.CENTER);
		aRenderer.setChartValuesTextSize(40);
		DecimalFormat df = new DecimalFormat("#.##");
		aRenderer.setChartValuesFormat(df);

	}

	@Override
	public Intent execute() {
		return ChartFactory.getBarChartIntent(context, dataset, renderer,
				Type.DEFAULT);
	}

}
