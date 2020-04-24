package wolox.training.practicae;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.models.Book;

@RestController
@RequestMapping (value="todolist")
public class ToDoList {
	
	@Autowired
	private IBookService bookService;

	@GetMapping(value="books")
	public List<Book> getBooks() {
		return bookService.getBook();
	}
	
	
	
}
