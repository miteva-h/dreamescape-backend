package com.project.service.implementation;

import com.project.domain.Accommodation;
import com.project.domain.Review;
import com.project.domain.dto.ReviewDto;
import com.project.domain.exceptions.AccommodationNotFoundException;
import com.project.domain.exceptions.UserNotFoundException;
import com.project.domain.identity.User;
import com.project.domain.relations.ArrangementInOrder;
import com.project.repository.AccommodationRepository;
import com.project.repository.ArrangementInOrderRepository;
import com.project.repository.ReviewRepository;
import com.project.repository.UserRepository;
import com.project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final ArrangementInOrderRepository arrangementInOrderRepository;

    @Override
    public List<Review> findByAccommodation(Long accommodationId) {
        return this.reviewRepository.findAllByAccommodation_Id(accommodationId);
    }

    @Override
    public Optional<Review> add(ReviewDto reviewDto) {
        User user = this.userRepository.findByUsername(reviewDto.getUsername()).orElseThrow(UserNotFoundException::new);
        Accommodation accommodation = this.accommodationRepository.findById(reviewDto.getAccommodation()).orElseThrow(AccommodationNotFoundException::new);
        Review review = new Review(reviewDto.getReviewText(), reviewDto.getStars(), user, accommodation);
        this.reviewRepository.save(review);
        return Optional.of(review);
    }

    @Override
    public void deleteById(Long id) {
        this.reviewRepository.deleteById(id);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return this.reviewRepository.findById(id);
    }

    @Override
    public Boolean getPermissionToAdd(Long id,String username) {
        List<ArrangementInOrder> arrangements = arrangementInOrderRepository.findAll();
        List<ArrangementInOrder> arrangementsForUser = arrangements.stream()
                .filter(a-> Objects.equals(a.getArrangement().getAccommodation().getId(), id))
                .filter(a -> Objects.equals(a.getOrder().getUser().getUsername(), username))
                .filter(a->a.getArrangement().getTo_date().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return !arrangementsForUser.isEmpty();
    }
}
