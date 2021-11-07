package dev.alfamike.martianrobots.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alfamike.martianrobots.model.Robot;

public interface RobotRepository extends JpaRepository<Robot, Long>{

}
