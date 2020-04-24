package wolox.training.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Books")
public class Book implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	 
    private String genre;
 
    private String author;

    private String image;

    private String title;
    
    private String subtitle;

    private String publisher;

    private String year;

    private Integer pages;

    private String isbn;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSubtitle() {
		return subtitle;
	}


	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public Integer getPages() {
		return pages;
	}


	public void setPages(Integer pages) {
		this.pages = pages;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	private static final long serialVersionUID = 1L;
}
