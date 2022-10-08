package ru.explorewithme.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.model.location.Location;

/**
 * Jpa Repository для локаций
 */

@RepositoryRestResource(path = "locations")
public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

}
