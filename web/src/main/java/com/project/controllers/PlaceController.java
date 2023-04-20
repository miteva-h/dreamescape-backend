package com.project.controllers;

import com.project.domain.Accommodation;
import com.project.domain.PhotoForPlace;
import com.project.domain.Place;
import com.project.domain.dto.PhotoForPlaceDto;
import com.project.domain.dto.PlaceDto;
import com.project.service.AccommodationService;
import com.project.service.PhotoForPlaceService;
import com.project.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    private final PhotoForPlaceService photoForPlaceService;

    private final AccommodationService accommodationService;

    @GetMapping
    public List<Place> findAll() {
        return this.placeService.findAll();
    }

    @GetMapping("/{id}/accommodations")
    public List<Accommodation> findAllAccommodationsByPlace(@PathVariable Long id) {
        return this.accommodationService.findAllByPlace(id);
    }

    @GetMapping("/{id}/photos")
    public List<PhotoForPlace> findAllPhotosByPlace(@PathVariable Long id) {
        return this.photoForPlaceService.findAll(id);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<PhotoForPlace> addPhotoToPLace(@PathVariable Long id, @RequestBody PhotoForPlaceDto photoForPlaceDto) {
        return this.photoForPlaceService.add(id, photoForPlaceDto)
                .map(photoForPlace -> ResponseEntity.ok().body(photoForPlace))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/filter/{word}")
    public List<Place> findAllByNameContaining(@PathVariable String word) {
        return this.placeService.findAllByNameContaining(word);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> findById(@PathVariable Long id) {
        return this.placeService.findById(id)
                .map(place -> ResponseEntity.ok().body(place))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Place> add(@RequestBody PlaceDto placeDto) {
        return this.placeService.add(placeDto)
                .map(place -> ResponseEntity.ok().body(place))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Place> edit(@PathVariable Long id, @RequestBody PlaceDto placeDto) {
        return this.placeService.edit(id, placeDto)
                .map(place -> ResponseEntity.ok().body(place))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.placeService.deleteById(id);
        if (this.placeService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
