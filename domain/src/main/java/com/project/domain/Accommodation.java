package com.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.domain.enumerations.TypeOfAccommodation;
import com.project.domain.enumerations.TypeOfBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeOfAccommodation typeOfAccommodation;

    @Enumerated(EnumType.STRING)
    private TypeOfBoard typeOfBoard;

    private Integer sleeps;

    private String destination;

    @Column(length = 1000)
    private String description;

    private Double coordinate_x;

    private Double coordinate_y;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private Double pricePerNight;

    private String photo;

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation")
    private List<Arrangement> arrangements;

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation")
    private List<Review> reviews;

    public Accommodation(String name,
                         TypeOfAccommodation typeOfAccommodation,
                         TypeOfBoard typeOfBoard,
                         Integer sleeps,
                         String destination,
                         String description,
                         Double coordinate_x,
                         Double coordinate_y,
                         Place place,
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
