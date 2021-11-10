package dev.alfamike.martianrobots;

public class Coordinate {
	
    
    public Coordinate(int xAxis, int yAxis, Orientation orientation) {
		super();
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.orientation = orientation;
	}

	private int xAxis;
    
    private int yAxis;
    
    private Orientation orientation;

	public int getxAxis() {
		return xAxis;
	}

	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
    
    
}
