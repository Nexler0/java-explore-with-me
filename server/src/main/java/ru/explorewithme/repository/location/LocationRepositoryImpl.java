package ru.explorewithme.repository.location;

import org.springframework.context.annotation.Lazy;

public class LocationRepositoryImpl implements LocationRepositoryCustom {

    private final LocationRepository locationRepository;

    public LocationRepositoryImpl(@Lazy LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
