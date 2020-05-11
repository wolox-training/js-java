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
import com.google.common.base.Preconditions;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnore
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

	public Book() {
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", users=" + users + ", genre=" + genre + ", author=" + author + ", image=" + image
		        + ", title=" + title + ", subtitle=" + subtitle + ", publisher=" + publisher + ", year=" + year
		        + ", pages=" + pages + ", isbn=" + isbn + "]";
	}

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

	public long getId() {
		return id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		Preconditions.checkArgument(genre != null && !genre.isEmpty(), "genre no puede estar vacio");
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {

		Preconditions.checkArgument(author != null && !author.isEmpty(), "author no puede estar vacio");

		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		Preconditions.checkArgument(image != null && !image.isEmpty(), "image no puede estar vacio");
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		Preconditions.checkArgument(title != null && !title.isEmpty(), "title no puede estar vacio");
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		Preconditions.checkArgument(subtitle != null && !subtitle.isEmpty(), "subtitle no puede estar vacio");
		this.subtitle = subtitle;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		Preconditions.checkArgument(publisher != null && !publisher.isEmpty(), "publisher no puede estar vacio");
		this.publisher = publisher;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}
