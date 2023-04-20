package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.config.JwtAuthConstants;
import com.project.config.filters.JWTAuthorizationFilter;
import com.project.domain.Arrangement;
import com.project.domain.dto.ArrangementDto;
import com.project.domain.relations.ArrangementInShoppingCart;
import com.project.service.ArrangementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arrangements")
public class ArrangementController {

    private final ArrangementService arrangementService;

    private final JWTAuthorizationFilter filter;

    @PostMapping("/add")
    public ResponseEntity<Arrangement> add(@RequestBody ArrangementDto arrangementDto) {
        return this.arrangementService.add(arrangementDto)
                .map(arrangement -> ResponseEntity.ok().body(arrangement))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/user")
    public List<ArrangementInShoppingCart> findAllArrangementsForUser(HttpServletRequest request) throws JsonProcessingException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);
        String username = filter.getUsername(header);
        return this.arrangementService.getAllArrangementsForUser(username);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.arrangementService.deleteById(id);
        if (this.arrangementService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}
