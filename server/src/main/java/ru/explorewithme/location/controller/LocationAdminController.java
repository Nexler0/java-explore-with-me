package ru.explorewithme.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.explorewithme.location.repository.LocationRepository;

@RestController
@RequestMapping("/admin/locations")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationRepository locationRepository;


}
