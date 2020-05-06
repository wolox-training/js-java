package wolox.training.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void givenBooks_WhenGetAllBooks_ThenFind4Books() {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "", "", "2021", 361, "78945678945961");
		bookRepository.save(book1);
		Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "", "", "2022", 362, "78945678945962");
		bookRepository.save(book2);
		Book book3 = new Book("pepa3", "pepa3", "Image3", "momo3", "", "", "2023", 363, "78945678945963");
		bookRepository.save(book3);
		Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "", "", "2024", 364, "78945678945964");
		bookRepository.save(book4);

		Iterable<Book> books = bookRepository.findAll();

		assertThat(books).hasSize(4);
	}

	@Test
	public void givenBooks_WhenSearchByAuthorForPepa2Then_FindBookOfIsbn78945678945962() {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "", "", "2021", 361, "78945678945961");
		bookRepository.save(book1);
		Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "", "", "2022", 362, "78945678945962");
		bookRepository.save(book2);
		Book book3 = new Book("pepa3", "pepa3", "Image3", "momo3", "", "", "2023", 363, "78945678945963");
		bookRepository.save(book3);
		Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "", "", "2024", 364, "78945678945964");
		bookRepository.save(book4);

		Book foundedBook = bookRepository.findFirstByAuthorOrderByYearDesc("pepa2").orElse(new Book());
		assertThat("78945678945962".equals(foundedBook.getIsbn()));
	}

	@Test
	public void givenBooks_WhenSearchByAuthorForPiruloThenNotFindBooks() {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "", "", "2021", 361, "78945678945961");
		bookRepository.save(book1);
		Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "", "", "2022", 362, "78945678945962");
		bookRepository.save(book2);
		Book book3 = new Book("pepa3", "pepa3", "Image3", "momo3", "", "", "2023", 363, "78945678945963");
		bookRepository.save(book3);
		Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "", "", "2024", 364, "78945678945964");
		bookRepository.save(book4);

		assertThat(!bookRepository.findFirstByAuthorOrderByYearDesc("pirulo").isPresent());
	}

}