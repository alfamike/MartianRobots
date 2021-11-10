package dev.alfamike.martianrobots.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.alfamike.martianrobots.Orientation;

@Entity
public class LogMovements {

	public LogMovements(Long robot, int xAxis, int yAxis, Orientation orientation) {
		super();
		this.robot = robot;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.orientation = orientation;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private Long robot;

	private int xAxis;

	private int yAxis;

	@Enumerated(EnumType.STRING)
	private Orientation orientation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRobot() {
		return robot;
	}

	public void setRobot(Long robot) {
		this.robot = robot;
	}

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
