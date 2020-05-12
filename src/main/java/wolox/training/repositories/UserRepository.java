package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByUserName(@NotBlank String userName);

	@Query("select u from User u where ((cast(:dateFrom as date) is null or cast(:dateTo as date) = null or"
	        + " u.birthDate between :dateFrom and :dateTo) and (:name = null or lower(u.name) like lower(concat('%',:name,'%'))))")
	List<User> findByBirthDateBetweenAndCadena(@Param("dateFrom") LocalDate dateFrom,
	        @Param("dateTo") LocalDate dateTo, @Param("name") String name);
}
