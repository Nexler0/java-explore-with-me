package ru.explorewithme.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.user.model.User;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
