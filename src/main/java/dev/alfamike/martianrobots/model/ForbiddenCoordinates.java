package dev.alfamike.martianrobots.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
