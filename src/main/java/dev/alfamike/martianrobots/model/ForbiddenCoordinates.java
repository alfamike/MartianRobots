package dev.alfamike.martianrobots.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ForbiddenCoordinates {
	
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @Column
    private int xAxis;
    
    @Column
    private int yAxis;

}
