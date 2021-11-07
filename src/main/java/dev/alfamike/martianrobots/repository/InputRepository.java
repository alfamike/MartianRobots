package dev.alfamike.martianrobots.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alfamike.martianrobots.model.Input;

public interface InputRepository extends JpaRepository<Input, Long>{

}
