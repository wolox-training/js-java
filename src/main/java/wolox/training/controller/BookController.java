package wolox.training.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.models.Book;
import wolox.training.repositories.IBookRepositories;

@RestController
@RequestMapping (value="listar")
public class BookController {

	@Autowired
	private IBookRepositories bookDao;
	
	//     http://localhost:8086/listar/todos
	
	@GetMapping(value="todos")
	public List<Book> getBooks() {
//		return bookService.getBook();
		return (List<Book>) bookDao.findAll();
	}
	
	//    http://localhost:8086/listar/Xautor?author=pepi
	
	@GetMapping(value="Xautor")
	public Optional<Book> getBooks2(@RequestParam(name="author", required=false, defaultValue="xxxxx") String author, Model model) {
		return bookDao.findFirstByAuthor(author);
	}
	
}
