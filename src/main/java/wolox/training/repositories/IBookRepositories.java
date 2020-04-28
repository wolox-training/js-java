package wolox.training.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


import wolox.training.models.Book;

public interface IBookRepositories extends CrudRepository<Book, Long> {
	public Optional<Book> findFirstByAuthor(String author);

}
