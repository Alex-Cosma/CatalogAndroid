package com.catalog.model;

import java.util.List;

public class ChartData {
	private String whatItRepresents;
	private String[] xValues;
	private List<Float> yValues;

	/**
	 * @return the whatItRepresents
	 */
	public String getWhatItRepresents() {
		return whatItRepresents;
	}

	/**
	 * @param whatItRepresents
	 *            the whatItRepresents to set
	 */
	public void setWhatItRepresents(String whatItRepresents) {
		this.whatItRepresents = whatItRepresents;
	}

	/**
	 * @return the xValues
	 */
	public String[] getxValues() {
		return xValues;
	}

	/**
	 * @param xValues
	 *            the xValues to set
	 */
	public void setxValues(String[] xValues) {
		this.xValues = xValues;
	}

	/**
	 * @return the yValues
	 */
	public List<Float> getyValues() {
		return yValues;
	}

	/**
	 * @param yValues
	 *            the yValues to set
	 */
	public void setyValues(List<Float> yValues) {
		this.yValues = yValues;
	}
}
