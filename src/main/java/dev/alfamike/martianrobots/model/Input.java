package dev.alfamike.martianrobots.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.alfamike.martianrobots.Instruction;

@Entity
public class Input {
	
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
	
	private int gridX;
	
	private int gridY;
	
	private int initialCoordinateX;
	
	private int initialCoordinateY;
	
	private int initialCoordinateO;
	
	@Column(length=100)
	@Enumerated(EnumType.STRING)
	private Instruction [] movements;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public int getInitialCoordinateX() {
		return initialCoordinateX;
	}

	public void setInitialCoordinateX(int initialCoordinateX) {
		this.initialCoordinateX = initialCoordinateX;
	}

	public int getInitialCoordinateY() {
		return initialCoordinateY;
	}

	public void setInitialCoordinateY(int initialCoordinateY) {
		this.initialCoordinateY = initialCoordinateY;
	}

	public int getInitialCoordinateO() {
		return initialCoordinateO;
	}

	public void setInitialCoordinateO(int initialCoordinateO) {
		this.initialCoordinateO = initialCoordinateO;
	}

	public Instruction[] getMovements() {
		return movements;
	}

	public void setMovements(Instruction[] movements) {
		this.movements = movements;
	}
	
}
