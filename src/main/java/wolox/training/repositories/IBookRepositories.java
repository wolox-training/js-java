package wolox.training.practicae;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import wolox.training.models.Book;

public interface IBookDao extends CrudRepository<Book, Long> {
	public List<Book> findByAuthor(String author);

}
