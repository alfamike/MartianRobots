package dev.alfamike.martianrobots;

import java.io.Serializable;

public class ForbiddenCoordinatesId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int XAxis;
	private int YAxis;

	public int getXAxis() {
		return XAxis;
	}

	public void setXAxis(int xAxis) {
		XAxis = xAxis;
	}

	public int getYAxis() {
		return YAxis;
	}

	public void setYAxis(int yAxis) {
		YAxis = yAxis;
	}

}
