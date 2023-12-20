package com.project.domain.dto;

import com.project.domain.enumerations.TypeOfAccommodation;
import com.project.domain.enumerations.TypeOfBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccommodationDto {

    private String name;

    private TypeOfAccommodation typeOfAccommodation;

    private TypeOfBoard typeOfBoard;

    private Integer sleeps;

    private String destination;

    private String description;

    private Double coordinate_x;

    private Double coordinate_y;

    private Long place;

    private Double pricePerNight;

    private String photo;

    public AccommodationDto(String name,
                            TypeOfAccommodation typeOfAccommodation,
                            TypeOfBoard typeOfBoard,
                            Integer sleeps,
                            String destination,
                            String description,
                            Double coordinate_x,
                            Double coordinate_y,
                            Long place,
                            Double pricePerNight,
                            String photo) {
        this.name = name;
        this.typeOfAccommodation = typeOfAccommodation;
        this.typeOfBoard = typeOfBoard;
        this.sleeps = sleeps;
        this.destination = destination;
        this.description = description;
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
        this.place = place;
        this.pricePerNight = pricePerNight;
        this.photo = photo;
    }
}
