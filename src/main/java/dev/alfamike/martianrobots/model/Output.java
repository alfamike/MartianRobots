package dev.alfamike.martianrobots.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.alfamike.martianrobots.Orientation;

@Entity
public class Output {
	
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
	@Column
	private int xAxis;
	
	@Column
	private int yAxis;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Orientation orientation;
	
	@Column
	private String observation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	
}
