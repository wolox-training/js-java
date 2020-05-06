package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String userName;

	@NotNull
	private String name;

	@NotNull
	private LocalDate birthDate;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	private List<Book> books = new ArrayList<Book>();

	public User() {
	}

	public List<Book> addBook(Book Book) throws BookAlreadyOwnedException {
		if (books.contains(Book)) {
			throw new BookAlreadyOwnedException("Ingresó un libro que el usuario ya tenia asociado");
		} else {
			this.books.add(Book);
		}
		return (List<Book>) Collections.unmodifiableList(books);
	}

	public void deleteBook(Book Book) throws BookNotFoundException {

		if (!books.contains(Book)) {
			throw new BookNotFoundException("El libro ingresado no existe en la lista del usuario");
		} else {
			this.books.remove(Book);
		}
	}

	public User(String userName, String name, LocalDate birthDate) {
		this.userName = userName;
		this.name = name;
		this.birthDate = birthDate;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableList(books);
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
