package wolox.training.models;

import javax.validation.constraints.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
	private Date birthDate;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	private List<Book> books = new ArrayList<Book>();

	public User() {
	}

	public List<Book> agregarLibro(Book Book) throws BookAlreadyOwnedException {
		if (books.contains(Book)) {
			throw new BookAlreadyOwnedException("Ingreso un libro que el usuario ya tenia asociado");
		} else {
			this.books.add(Book);
		}
		return (List<Book>) Collections.unmodifiableList(books);
	}

	// borrar por libro entero desde la clase, pero el controller que busque por id

	public void borrarLibro(Book Book) throws BookNotFoundException {

		if (!books.contains(Book)) {
			throw new BookNotFoundException("El libro ingresado no existe enla lista del usuario");
		} else {
			this.books.remove(Book);
		}
	}

	public User(@NotNull String userName, @NotNull String name, @NotNull Date birthDate, List<Book> books) {
		this.userName = userName;
		this.name = name;
		this.birthDate = birthDate;
		this.books = books;
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

	public Date getBirthDay() {
		return birthDate;
	}

	public void setBirthDay(@NotNull Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Book> getBooks() {
		// return books;
		return (List<Book>) Collections.unmodifiableList(books);
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
