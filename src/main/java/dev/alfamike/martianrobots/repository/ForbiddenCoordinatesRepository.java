package dev.alfamike.martianrobots.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alfamike.martianrobots.model.ForbiddenCoordinates;

public interface ForbiddenCoordinatesRepository extends JpaRepository<ForbiddenCoordinates, Long>{
	public ForbiddenCoordinates findByXAxisAndYAxis(int xAxis, int yAxis);
}
