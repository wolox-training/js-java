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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;

@Entity
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull
    private String userName;

    @NotNull
    private String name;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @Getter(AccessLevel.NONE)
    private List<Book> books = new ArrayList<Book>();

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

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

}
