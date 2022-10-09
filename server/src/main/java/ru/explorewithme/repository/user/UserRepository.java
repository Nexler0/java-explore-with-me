package ru.explorewithme.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.model.user.User;

/**
 * Jpa Repository для пользователя
 */

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
