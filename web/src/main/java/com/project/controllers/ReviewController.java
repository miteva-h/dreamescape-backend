package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.config.JwtAuthConstants;
import com.project.config.filters.JWTAuthorizationFilter;
import com.project.domain.Review;
import com.project.domain.dto.ReviewDto;
import com.project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final JWTAuthorizationFilter filter;

    @PostMapping("/add")
    public ResponseEntity<Review> add(@RequestBody ReviewDto reviewDto) {
        return this.reviewService.add(reviewDto)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.reviewService.deleteById(id);
        if (this.reviewService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/permissionToAdd")
    public Boolean hasPermissionToAdd(@PathVariable Long id,HttpServletRequest request) throws JsonProcessingException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);
        String username = filter.getUsername(header);
        return this.reviewService.getPermissionToAdd(id, username);
    }
}
