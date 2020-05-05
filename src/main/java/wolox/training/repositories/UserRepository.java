package wolox.training.repositories;

import java.util.Optional;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByUserName(@NotBlank String userName);
}
