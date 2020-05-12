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
	@Query("select u from Book u where (u.publisher = :publisher or null = :publisher) "
	        + "and (u.genre = :genre or null = :genre) and (u.year = :year or null = :year)")
	List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre,
	        @Param("year") String year);

	@Query("select u from Book u where (u.id = :id or null = :id) "
	                            + "and (u.genre = :genre or null = :genre)"
	                            + "and (u.author = :author or null = :author)"
	                            + "and (u.image = :image or null = :image)"
	                            + "and (u.title = :title or null = :title)"
	                            + "and (u.subtitle = :subtitle or null = :subtitle)"
	                            + "and (u.publisher = :publisher or null = :publisher)"
	                            + "and (null = :yearFrom or null = :yearTo or u.year between :yearFrom and :yearTo )"
	                            + "and (u.pages = :pages or null = :pages)"
	                            + "and (u.isbn = :isbn or null = :isbn)")
	List<Book> findForAllParameters(@Param("id") Long id, @Param("genre") String genre, @Param("author") String author,
	        @Param("image") String image, @Param("title") String title, @Param("subtitle") String subtitle,
	        @Param("publisher") String publisher, @Param("yearFrom") String yearFrom, @Param("yearTo") String yearTo,
	        @Param("pages") int pages, @Param("isbn") String isbn);

}
