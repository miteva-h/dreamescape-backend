package com.project.controllers;

import com.project.domain.Accommodation;
import com.project.domain.Review;
import com.project.domain.dto.AccommodationDto;
import com.project.domain.enumerations.TypeOfAccommodation;
import com.project.domain.enumerations.TypeOfBoard;
import com.project.service.AccommodationService;
import com.project.service.ArrangementService;
import com.project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    private final ReviewService reviewService;

    private final ArrangementService arrangementService;

    @GetMapping
    public List<Accommodation> findAll() {
        return this.accommodationService.findAll();
    }

    @GetMapping("/{id}/arrangements")
    public List<LocalDate> findAllDatesForAccommodation(@PathVariable Long id) {
        return this.arrangementService.getAllDatesForAccommodation(id);
    }

    @GetMapping("/{id}/reviews")
    public List<Review> findAllReviewsByAccommodation(@PathVariable Long id) {
        return this.reviewService.findByAccommodation(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> findById(@PathVariable Long id) {
        return this.accommodationService.findById(id)
                .map(accommodation -> ResponseEntity.ok().body(accommodation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/type")
    public List<TypeOfAccommodation> listAllTypesOfAccommodation() {
        return Arrays.stream(TypeOfAccommodation.values()).collect(Collectors.toList());
    }

    @GetMapping("/board")
    public List<TypeOfBoard> listAllTypesOfBoard() {
        return Arrays.stream(TypeOfBoard.values()).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<Accommodation> add(@RequestBody AccommodationDto accommodationDto) {
        return this.accommodationService.add(accommodationDto)
                .map(accommodation -> ResponseEntity.ok().body(accommodation))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Accommodation> edit(@PathVariable Long id, @RequestBody AccommodationDto accommodationDto) {
        return this.accommodationService.edit(id, accommodationDto)
                .map(accommodation -> ResponseEntity.ok().body(accommodation))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.accommodationService.deleteById(id);
        if (this.accommodationService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
