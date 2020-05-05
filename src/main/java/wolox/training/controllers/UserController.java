package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.google.common.base.Preconditions;

import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@GetMapping
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping("/{id}")
	public User findOne(@PathVariable(required = true) Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No se encontro el Usuario "));
	}

	@GetMapping
	@RequestMapping(params = "userName")
	public User findByUserName(@RequestParam(required = true) String userName) {
		return userRepository.findFirstByUserName(userName)
		        .orElseThrow(() -> new UserNotFoundException("No se encontro el Usuario "));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable(required = true) Long id) {
		userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No se encontro el Usuario "));
		userRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@RequestBody User user, @PathVariable Long id) {
		if (!id.equals(user.getId())) {
			throw new UserIdMismatchException("Id invalido");
		}
		userRepository.findById(id)
		        .orElseThrow(() -> new UserNotFoundException("No existe el Usuario del id ingresado"));
		return userRepository.save(user);
	}

	@PatchMapping("/{userid}/book/{bookid}")
	@ResponseStatus(HttpStatus.OK)
	public User addBook(@PathVariable(required = true) Long userid, @PathVariable(required = true) Long bookid) {
		User user = userRepository.findById(userid)
		        .orElseThrow(() -> new UserNotFoundException("No existe el Usuario del id ingresado"));
		Book book = bookRepository.findById(bookid)
		        .orElseThrow(() -> new BookNotFoundException("No existe el Libro del id ingresado"));
		user.addBook(book);
		return userRepository.save(user);
	}

	@DeleteMapping("/{userid}/book/{bookid}")
	@ResponseStatus(HttpStatus.OK)
	public User deleteBook(@PathVariable(required = true) Long userid, @PathVariable(required = true) Long bookid) {
		User user = userRepository.findById(userid)
		        .orElseThrow(() -> new UserNotFoundException("No existe el Usuario del id ingresado"));
		Book book = bookRepository.findById(bookid)
		        .orElseThrow(() -> new BookNotFoundException("No existe el Libro del id ingresado"));
		user.deleteBook(book);
		return userRepository.save(user);
	}

}
