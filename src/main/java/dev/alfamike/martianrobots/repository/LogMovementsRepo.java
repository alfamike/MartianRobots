package dev.alfamike.martianrobots.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alfamike.martianrobots.model.LogMovements;

public interface LogMovementsRepo extends JpaRepository<LogMovements, Long>{

}
