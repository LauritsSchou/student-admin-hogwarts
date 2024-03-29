package edu.hogwarts.house.repository;

import edu.hogwarts.house.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Integer> {
    Optional<Object> findByName(String gryffindor);
}
