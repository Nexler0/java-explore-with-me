package ru.explorewithme.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.user.model.User;

import java.awt.print.Pageable;
import java.util.List;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
