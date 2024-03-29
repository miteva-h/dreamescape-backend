package com.project.service.implementation;

import com.project.domain.Accommodation;
import com.project.domain.PhotoForPlace;
import com.project.domain.Place;
import com.project.domain.dto.PlaceDto;
import com.project.domain.exceptions.PlaceNotFoundException;
import com.project.repository.AccommodationRepository;
import com.project.repository.PhotoForPlaceRepository;
import com.project.repository.PlaceRepository;
import com.project.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImplementation implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PhotoForPlaceRepository photoForPlaceRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public List<Place> findAllByNameContaining(String word) {
        return placeRepository.findAllByNameContainingIgnoreCase(word);
    }

    @Override
    public Optional<Place> findById(Long id) {
        return this.placeRepository.findById(id);
    }

    public Boolean checkIfPresent(PlaceDto placeDto) {
        boolean check = this.placeRepository.findAll()
                .stream().anyMatch(x -> x.getName().equals(placeDto.getName()));
        return check;
    }

    @Override
    public Optional<Place> add(PlaceDto placeDto) {
        if (!checkIfPresent(placeDto)) {
            Place place = new Place(placeDto.getName(), placeDto.getDescription(), placeDto.getCoordinate_x(), placeDto.getCoordinate_y());
            this.placeRepository.save(place);
            return Optional.of(place);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Place> edit(Long id, PlaceDto placeDto) {
        Place place = this.placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        place.setName(placeDto.getName());
        place.setDescription(placeDto.getDescription());
        place.setCoordinate_x(placeDto.getCoordinate_x());
        place.setCoordinate_y(placeDto.getCoordinate_y());
        this.placeRepository.save(place);
        return Optional.of(place);
    }

    @Override
    public void deleteById(Long id) {
        Place place = this.placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        List<PhotoForPlace> photos = this.photoForPlaceRepository.findAllByPlace(place);
        this.photoForPlaceRepository.deleteAll(photos);
        List<Accommodation> accommodations = this.accommodationRepository.findAllByPlace(place);
        this.accommodationRepository.deleteAll(accommodations);
        this.placeRepository.deleteById(id);
    }
}
