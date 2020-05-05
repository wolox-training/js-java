package wolox.training.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void whenFindFirstByAuthorOrderByYear_thenReturnBook() {
		Book book = new Book();
		book.setAuthor("pepe");
		book.setGenre("accion");
		book.setImage("pepe");
		book.setIsbn("123456");
		book.setPages(123);
		book.setPublisher("JOJO");
		book.setSubtitle("ASDAS");
		book.setTitle("coco");
		book.setYear("2020");

		entityManager.persist(book);
		entityManager.flush();
//		bookRepository.save(book);

		Book found = bookRepository.findFirstByAuthorOrderByYear(book.getAuthor()).orElse(new Book());

		assertThat("pepe".equals(found.getAuthor())).isTrue();
	}
}
