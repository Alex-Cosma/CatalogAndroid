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

import java.util.List;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

import com.catalog.model.ChartData;

/**
 * An abstract class for the demo charts to extend. It contains some methods for
 * building datasets and renderers.
 */
public abstract class AbstractChart implements Chart_I {

	public static int[] colors = new int[] { Color.RED, Color.GREEN,
			Color.rgb(170, 0, 255), Color.BLUE, Color.rgb(184, 134, 11),
			Color.rgb(255, 51, 204), Color.rgb(49, 140, 231),
			Color.rgb(251, 236, 93), Color.CYAN, Color.LTGRAY, Color.YELLOW };

	protected XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	protected XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	protected Context context;

	public AbstractChart(Context context, List<ChartData> data) {
		this.context = context;
		if (data != null) {
			if (!data.isEmpty()) {
				for (int i = 0; i < data.size(); i++) {

					ChartData d = data.get(i);
					CategorySeries series = new CategorySeries(
							d.getWhatItRepresents());

					for (int j = 0; j < d.getxValues().length; j++) {
						series.add(d.getxValues()[j], d.getyValues().get(j));
					}

					dataset.addSeries(series.toXYSeries());

					XYSeriesRenderer aRenderer = new XYSeriesRenderer();
					setupSmallRenderer(aRenderer, d, i);
					renderer.addSeriesRenderer(aRenderer);
				}

				for (int i = 0; i < data.get(0).getxValues().length; i++) {
					renderer.addXTextLabel(i + 1, data.get(0).getxValues()[i]);
				}

				setupBigRenderer(renderer);
			} else
				return;
		} else
			return;

	}

	abstract void setupSmallRenderer(XYSeriesRenderer aRenderer, ChartData d,
			int i);

	private void setupBigRenderer(XYMultipleSeriesRenderer renderer) {
		renderer.setFitLegend(true);
		renderer.setGridColor(Color.BLACK);
		renderer.setAntialiasing(true);
		renderer.setShowGridX(true);
		renderer.setShowGridY(false);
		renderer.setZoomButtonsVisible(true);
		int[] margins = { 150, 150, 150, 150 };
		renderer.setMargins(margins);
		renderer.setAxisTitleTextSize(40);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(35);
		renderer.setLegendTextSize(35);

		renderer.setShowCustomTextGrid(true);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setXLabels(0);

	}

}
