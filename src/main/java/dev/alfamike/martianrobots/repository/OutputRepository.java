package dev.alfamike.martianrobots.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alfamike.martianrobots.model.Output;

public interface OutputRepository extends JpaRepository<Output, Long> {

}
