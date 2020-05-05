package wolox.training.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void whenFindFirstByUserName_thenReturnUser() {
		User user = new User();
		user.setUserName("jona2");
		user.setName("jona");
		user.setBirthDay("1987-02-07");

		entityManager.persist(user);
		entityManager.flush();
		// userRepository.save(book);

		User found = userRepository.findFirstByUserName(user.getUserName()).orElse(new User());

		assertThat("jona2".equals(found.getUserName())).isTrue();
	}
}
