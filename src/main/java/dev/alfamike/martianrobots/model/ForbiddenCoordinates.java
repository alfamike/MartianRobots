package dev.alfamike.martianrobots.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import dev.alfamike.martianrobots.ForbiddenCoordinatesId;

@Entity
@IdClass(ForbiddenCoordinatesId.class)
public class ForbiddenCoordinates {

	public ForbiddenCoordinates(int xAxis, int yAxis) {
		super();
		this.XAxis = xAxis;
		this.YAxis = yAxis;
	}

	@Id
	private int XAxis;

	@Id
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
