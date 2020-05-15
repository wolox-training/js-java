package wolox.training.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<User>();

    private String genre;

    @NotNull
    private String author;

    @NotNull
    private String image;

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private String publisher;

    @NotNull
    private String year;

    @NotNull
    private int pages;

    @NotNull
    private String isbn;

    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year,
            int pages, String isbn) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }
}
