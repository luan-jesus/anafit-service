package luanjesus.tech.anafitservice.infrastructure.hibernate.repository;

import luanjesus.tech.anafitservice.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
