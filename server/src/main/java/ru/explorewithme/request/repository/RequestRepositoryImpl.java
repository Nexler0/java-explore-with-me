package ru.explorewithme.request.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "requests")
public class RequestRepositoryImpl implements RequestRepositoryCustom {

    private final RequestRepository requestRepository;

    public RequestRepositoryImpl(@Lazy RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
}
