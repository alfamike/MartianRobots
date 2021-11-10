package dev.alfamike.martianrobots.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alfamike.martianrobots.ForbiddenCoordinatesId;
import dev.alfamike.martianrobots.model.ForbiddenCoordinates;

public interface ForbiddenCoordinatesRepository extends JpaRepository<ForbiddenCoordinates, ForbiddenCoordinatesId> {
	public ForbiddenCoordinates findByXAxisAndYAxis(int xAxis, int yAxis);
}
