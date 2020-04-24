package wolox.training.practicae;

import org.springframework.data.repository.CrudRepository;

import wolox.training.models.Book;

public interface IBookDao extends CrudRepository<Book, Long> {

}
