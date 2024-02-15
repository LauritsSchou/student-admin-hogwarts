package edu.hogwarts.house.repository;

import edu.hogwarts.house.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Integer> {
}
