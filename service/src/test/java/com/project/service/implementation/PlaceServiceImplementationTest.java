package com.project.service.implementation;

import com.project.domain.PhotoForPlace;
import com.project.domain.Place;
import com.project.domain.dto.PlaceDto;
import com.project.repository.AccommodationRepository;
import com.project.repository.PhotoForPlaceRepository;
import com.project.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceImplementationTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PhotoForPlaceRepository photoForPlaceRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private PlaceServiceImplementation placeService;

    @Test
    public void findAll_ShouldReturnListOfPlaces() {
        List<Place> expectedPlaces = Arrays.asList(
                new Place("Place 1", "Location 1", "Description 1"),
                new Place("Place 2", "Location 2", "Description 2")
        );
        when(placeRepository.findAll()).thenReturn(expectedPlaces);

        List<Place> actualPlaces = placeService.findAll();

        assertThat(actualPlaces).isEqualTo(expectedPlaces);
    }

    @Test
    public void findAllByNameContaining_ShouldReturnListOfPlaces() {
        List<Place> expectedPlaces = Arrays.asList(
                new Place("Place 1", "Location 1", "Description 1"),
                new Place("Place 2", "Location 2", "Description 2")
        );
        when(placeRepository.findAllByNameContainingIgnoreCase("Place")).thenReturn(expectedPlaces);

        List<Place> actualPlaces = placeService.findAllByNameContaining("Place");

        assertThat(actualPlaces).isEqualTo(expectedPlaces);
    }

    @Test
    public void findById_ShouldReturnOptionalOfPlace() {
        Place expectedPlace = new Place("Place 1", "Location 1", "Description 1");
        when(placeRepository.findById(1L)).thenReturn(Optional.of(expectedPlace));

        Optional<Place> actualPlace = placeService.findById(1L);

        assertThat(actualPlace).isEqualTo(Optional.of(expectedPlace));
    }

    @Test
    public void checkIfPresent_ShouldReturnSuccess() {
        PlaceDto placeDto = new PlaceDto("Place 1", "Location 1", "Description 1");
        Place place = new Place("Place 1", "Location 1", "Description 1");
        List<Place> places = Collections.singletonList(place);
        when(placeRepository.findAll()).thenReturn(places);

        Boolean actualResult = placeService.checkIfPresent(placeDto);

        assertThat(actualResult).isTrue();
    }

    @Test
    public void checkIfPresent_ShouldReturnFailure() {
        PlaceDto placeDto = new PlaceDto("Place 2", "Location 2", "Description 2");
        Place place = new Place("Place 1", "Location 1", "Description 1");
        List<Place> places = Collections.singletonList(place);
        when(placeRepository.findAll()).thenReturn(places);

        Boolean actualResult = placeService.checkIfPresent(placeDto);

        assertThat(actualResult).isFalse();
    }

    @Test
    public void add_ShouldReturnOptionalOfPlace() {
        PlaceDto placeDto = new PlaceDto("Place 1", "Location 1", "Description 1");
        Place expectedPlace = new Place("Place 1", "Location 1", "Description 1");
        when(placeRepository.save(expectedPlace)).thenReturn(expectedPlace);

        Optional<Place> actualPlace = placeService.add(placeDto);

        assertThat(actualPlace).isEqualTo(Optional.of(expectedPlace));
    }

    @Test
    public void add_TestIfAlreadyExists(){
        PlaceDto placeDto = new PlaceDto("Place 1", "Location 1", "Description 1");

        when(placeRepository.findAll()).thenReturn(Collections.singletonList(new Place("Place 1", "Location 1", "Description 1")));

        Optional<Place> result = placeService.add(placeDto);

        verify(placeRepository).findAll();
        verify(placeRepository, never()).save(any(Place.class));
        assertFalse(result.isPresent());
    }

    @Test
    public void edit_ShouldReturnOptionalOfPlace() {
        PlaceDto placeDto = new PlaceDto("Place New", "Location New", "Description New");
        Place expectedPlace = new Place("Place 1", "Location 1", "Description 1");
        when(placeRepository.findById(1L)).thenReturn(Optional.of(expectedPlace));
        when(placeRepository.save(expectedPlace)).thenReturn(expectedPlace);

        Optional<Place> actualPlace = placeService.edit(1L, placeDto);

        assertThat(actualPlace).isNotEmpty();
        assertThat(actualPlace.get().getName()).isEqualTo("Place New");
        assertThat(actualPlace.get().getLocation()).isEqualTo("Location New");
        assertThat(actualPlace.get().getDescription()).isEqualTo("Description New");
        verify(placeRepository, times(1)).findById(1L);
        verify(placeRepository, times(1)).save(expectedPlace);
    }

    @Test
    public void delete_ShouldReturnVoid(){
        Place place = new Place();
        PhotoForPlace photo = new PhotoForPlace();
        photo.setPlace(place);

        when(placeRepository.findById(1L)).thenReturn(Optional.of(place));
        when(photoForPlaceRepository.findAllByPlace(place)).thenReturn(Collections.singletonList(photo));
        when(accommodationRepository.findAllByPlace(place)).thenReturn(Collections.emptyList());

        placeService.deleteById(1L);

        verify(placeRepository, Mockito.times(1)).deleteById(1L);
        verify(photoForPlaceRepository, Mockito.times(1)).deleteAll(Collections.singletonList(photo));
        verify(accommodationRepository, Mockito.times(1)).deleteAll(Collections.emptyList());
    }
}