package com.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private Double coordinate_x;

    private Double coordinate_y;

    @JsonIgnore
    @OneToMany(mappedBy = "place")
    private List<PhotoForPlace> gallery;

    @JsonIgnore
    @OneToMany(mappedBy = "place")
    private List<Accommodation> accommodations;

    public Place(String name,
                 String description,
                 Double coordinate_x,
                 Double coordinate_y) {
        this.name = name;
        this.description = description;
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
    }
}
