package ru.explorewithme.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.location.model.Location;

@RepositoryRestResource(path = "locations")
public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

}
