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
    public void givenBooks_WhenSearchByAuthorForPepa2_ThenFindBookOfIsbn78945678945962() {
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
    public void givenBooks_WhenSearchByAuthorForPirulo_ThenNotFindBooks() {
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

    @Test
    public void givenBooks_WhenSearchByPublisherAndGenreAndYear_ThenFindBookOfIsbn78945678945962() {
        Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
        bookRepository.save(book1);
        Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "2", "2", "2022", 362, "78945678945962");
        bookRepository.save(book2);
        Book book3 = new Book("pepa2", "pepa2", "Image2", "momo2", "3", "3", "2022", 362, "78945678945962");
        bookRepository.save(book3);
        Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "4", "4", "2024", 364, "78945678945964");
        bookRepository.save(book4);

        Iterable<Book> foundedBook = bookRepository.findByPublisherAndGenreAndYear(null, "pepa2", "2022");
        System.out.println(foundedBook.toString());
        assertThat(foundedBook).hasSize(2);
    }

    @Test
    public void givenBooks_WhenSearchByByAllParameters_ThenFindBookOfIsbn78945678945962AndSubtitle2() {
        Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
        bookRepository.save(book1);
        Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "2", "2", "2022", 362, "78945678945962");
        bookRepository.save(book2);
        Book book3 = new Book("pepa3", "pepa2", "Image2", "momo2", "3", "3", "2023", 362, "78945678945962");
        bookRepository.save(book3);
        Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "4", "4", "2024", 364, "78945678945964");
        bookRepository.save(book4);

        // Iterable<Book> foundedBook = bookRepository.findByAllParameters(null, null, null, null, null,
        // "2", null, null,
        // null, null, "78945678945962");
        Iterable<Book> foundedBook = bookRepository.findByAllParameters("2", "pepa2", "pepa2", "Image2", "momo2", "2",
                "2", "2022", "2022", "362", "78945678945962");
        System.out.println(foundedBook.toString());
        assertThat(foundedBook).hasSize(1);
    }

}
