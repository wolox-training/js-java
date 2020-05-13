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

    @Query("select u from Book u where ( null is :publisher or u.publisher = :publisher) "
            + "and (null is :genre or u.genre = :genre) and (null is :year or u.year = :year)")
    List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre,
            @Param("year") String year);

    @Query("select u from Book u where ((:id is null or u.id = cast(:id as long)) and (:genre is null or u.genre = :genre)"
            + "and (:author is null or u.author = :author)" + "and (:image is null or u.image = :image)"
            + "and (:title is null or u.title = :title)" + "and (:subtitle is null or u.subtitle = :subtitle)"
            + "and (:publisher is null or u.publisher = :publisher)"
            + "and (:yearFrom is null or  :yearTo is null or u.year between :yearFrom and :yearTo )"
            + "and (:pages is null or u.pages = cast(:pages as int))" + "and (:isbn is null or u.isbn = :isbn))")
    List<Book> findByAllParameters(@Param("id") String id, @Param("genre") String genre, @Param("author") String author,
            @Param("image") String image, @Param("title") String title, @Param("subtitle") String subtitle,
            @Param("publisher") String publisher, @Param("yearFrom") String yearFrom, @Param("yearTo") String yearTo,
            @Param("pages") String pages, @Param("isbn") String isbn);

}
