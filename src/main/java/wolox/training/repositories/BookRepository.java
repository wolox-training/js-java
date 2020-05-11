package wolox.training.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findFirstByAuthorOrderByYearDesc(@NotBlank String author);

	Optional<Book> findFirstByIsbn(@NotBlank String isbn);

	List<Book> findByPublisherAndGenreAndYear(@NotBlank String publisher, @NotBlank String genre,
	        @NotBlank String year);
}
