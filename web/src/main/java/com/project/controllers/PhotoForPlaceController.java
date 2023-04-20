package com.project.controllers;

import com.project.domain.PhotoForPlace;
import com.project.domain.dto.PhotoForPlaceDto;
import com.project.service.PhotoForPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoForPlaceController {

    private final PhotoForPlaceService photoForPlaceService;

    @GetMapping("/{id}")
    public ResponseEntity<PhotoForPlace> findById(@PathVariable Long id) {
        return this.photoForPlaceService.findById(id)
                .map(photoForPlace -> ResponseEntity.ok().body(photoForPlace))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<PhotoForPlace> edit(@PathVariable Long id, @RequestBody PhotoForPlaceDto photoForPlaceDto) {
        return this.photoForPlaceService.edit(id, photoForPlaceDto)
                .map(photoForPlace -> ResponseEntity.ok().body(photoForPlace))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.photoForPlaceService.deleteById(id);
        if (this.photoForPlaceService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
