package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByUserName(@NotBlank String userName);

	@Query("select u from User u where u.birthDate between ?1 and ?2 and u.name like ?3")
	List<User> findByBirthDateBetweenAndCadena(LocalDate dateFrom, LocalDate dateTo, String cadena);

}
