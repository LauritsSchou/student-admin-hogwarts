package edu.hogwarts.house.controller;

import edu.hogwarts.house.model.House;
import edu.hogwarts.house.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HouseController {
    @Autowired
    private HouseRepository houseRepository;
    @GetMapping("/houses")
    public List<House> createAllHouses(){
        var gryffindor = new House();
        gryffindor.setId(1);
        gryffindor.setName("Gryffindor");
        gryffindor.setFounder("Godric Gryffindor");
        gryffindor.setColors("Red", "Gold");

        var slytherin = new House();
        slytherin.setId(2);
        slytherin.setName("Slytherin");
        slytherin.setFounder("Salazar Slytherin");
        slytherin.setColors("Green", "Silver");

        var hufflepuff = new House();
        hufflepuff.setId(3);
        hufflepuff.setName("Hufflepuff");
        hufflepuff.setFounder("Helga Hufflepuff");
        hufflepuff.setColors("Yellow", "Black");

        var ravenclaw = new House();
        ravenclaw.setId(4);
        ravenclaw.setName("Ravenclaw");
        ravenclaw.setFounder("Rowena Ravenclaw");
        ravenclaw.setColors("Blue", "Bronze");

        List<House> houses = new ArrayList<>();
        houses.add(gryffindor);
        houses.add(slytherin);
        houses.add(hufflepuff);
        houses.add(ravenclaw);
        houseRepository.saveAll(houses);
        return houses;
    }
}
