package ro.upt.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.upt.project.models.Role;
import ro.upt.project.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAllByRole(Role role);
}
