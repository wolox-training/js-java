package wolox.training.repositories;

import java.util.Optional;
import javax.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findFirstByUserName(@NotBlank String userName);
}
