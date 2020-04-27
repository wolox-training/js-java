package wolox.training.practicae;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.models.Book;

@RestController
@RequestMapping (value="listar")
public class BookController {
	
//	@Autowired
//	private IBookService bookService;

	@Autowired
	private IBookDao bookDao;
	
	//     http://localhost:8086/listar/todos
	
	@GetMapping(value="todos")
	public List<Book> getBooks() {
//		return bookService.getBook();
		return (List<Book>) bookDao.findAll();
	}
	
	//    http://localhost:8086/listar/Xautor?author=pepi
	
	@GetMapping(value="Xautor")
	public List<Book> getBooks2(@RequestParam(name="author", required=false, defaultValue="xxxxx") String author, Model model) {
		return (List<Book>) bookDao.findByAuthor(author);
	}
	
}
