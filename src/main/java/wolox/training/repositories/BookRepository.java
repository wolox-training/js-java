package wolox.training.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findFirstByAuthorOrderByYearDesc(@NotBlank String author);

	Optional<Book> findFirstByIsbn(@NotBlank String isbn);

//	List<Book> findByPublisherAndGenreAndYear(@NotBlank String publisher, @NotBlank String genre,
//    @NotBlank String year);
	@Query("select u from Book u where (u.publisher = :publisher or null = :publisher) and (u.genre = :genre or null = :genre) and (u.year = :year or null = :year)")
	List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre,
	        @Param("year") String year);
}
