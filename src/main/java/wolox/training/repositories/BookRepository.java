package wolox.training.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.repository.CrudRepository;

import wolox.training.models.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
    
	Optional<Book> findFirstByAuthorOrderByYear(@NotBlank String author);
	Optional<Book> findFirstByAuthorOrderByYearDesc(@NotBlank String author);
    List<Book> findByAuthorOrderByYear(@NotBlank String author);
}
