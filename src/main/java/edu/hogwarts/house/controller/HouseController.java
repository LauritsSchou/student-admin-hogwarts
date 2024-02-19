package edu.hogwarts.house.controller;

import edu.hogwarts.house.model.House;
import edu.hogwarts.house.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {
    private final HouseRepository houseRepository;
public HouseController (HouseRepository houseRepository){
    this.houseRepository = houseRepository;
}
@GetMapping()
    public List<House> getAllHouses(){
    return houseRepository.findAll();
}
}
