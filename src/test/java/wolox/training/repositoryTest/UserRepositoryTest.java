package wolox.training.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void givenUsers_WhenSearchAll_ThenFind2Users() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user1 = new User("0001", "pepe", LocalDate.parse("1920-10-10", formatter));
		userRepository.save(user1);
		User user2 = new User("0002", "popo", LocalDate.parse("1918-01-04", formatter));
		userRepository.save(user2);

		Iterable<User> users = userRepository.findAll();

		assertThat(users).hasSize(2);
	}

	@Test
	public void givenUsers_WhenSearchByExistentUserName_ThenFind1User() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user1 = new User("0001", "pepe", LocalDate.parse("1920-10-10", formatter));
		userRepository.save(user1);
		User user2 = new User("0002", "popo", LocalDate.parse("1918-01-04", formatter));
		userRepository.save(user2);

		assertThat(userRepository.findFirstByUserName("0001").isPresent());
	}

	@Test
	public void givenUsers_WhenSearchByNonExistentUserName_ThenNotFindAnyUser() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user1 = new User("0001", "pepe", LocalDate.parse("1920-10-10", formatter));
		userRepository.save(user1);
		User user2 = new User("0002", "popo", LocalDate.parse("1918-01-04", formatter));
		userRepository.save(user2);

		assertThat(!userRepository.findFirstByUserName("A000005").isPresent());
	}

	@Test
	public void givenUsers_WhenSearchByBirthDateBetweenAndCadena_ThenFind1Users() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user1 = new User("0001", "pepe", LocalDate.parse("1918-01-10", formatter));
		userRepository.save(user1);
		User user2 = new User("0002", "popo", LocalDate.parse("1918-01-15", formatter));
		userRepository.save(user2);
		User user3 = new User("0003", "popi", LocalDate.parse("1918-01-20", formatter));
		userRepository.save(user3);

		Iterable<User> users = userRepository.findByBirthDateBetweenAndName(LocalDate.parse("1918-01-11", formatter),
		        LocalDate.parse("1918-01-17", formatter), "%" + "po" + "%");
		System.out.println(users.toString());

		assertThat(users).hasSize(1);

	}

	@Test
	public void givenUsers_WhenSearchByBirthDateBetweenAndCadenaWith1DateInNull_ThenFind2Users() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user1 = new User("0001", "pepe", LocalDate.parse("1918-01-10", formatter));
		userRepository.save(user1);
		User user2 = new User("0002", "popo", LocalDate.parse("1918-01-15", formatter));
		userRepository.save(user2);
		User user3 = new User("0003", "popi", LocalDate.parse("1918-01-20", formatter));
		userRepository.save(user3);

		Iterable<User> users = userRepository.findByBirthDateBetweenAndName(null,
		        LocalDate.parse("1918-01-17", formatter), "po");
		System.out.println(users.toString());

		assertThat(users).hasSize(2);

	}
}
