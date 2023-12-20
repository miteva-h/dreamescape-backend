package com.project.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceDto {

    private String name;

    private String description;

    private Double coordinate_x;

    private Double coordinate_y;

    public PlaceDto(String name,
                    String description,
                    Double coordinate_x,
                    Double coordinate_y) {
        this.name = name;
        this.description = description;
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
    }
}
