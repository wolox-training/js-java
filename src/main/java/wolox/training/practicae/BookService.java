package wolox.training.practicae;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.models.Book;
@Service
public class BookService implements IBookService {
	
	@Autowired
	private IBookDao bookDao;
	
	/**
	 * Method to get all the task
	 * @return
	 */
	public List<Book> getBook() {
		return (List<Book>) bookDao.findAll();
	}
}
