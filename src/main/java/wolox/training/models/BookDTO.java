package wolox.training.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String subtitle;
	private List<PublishersDTO> publishers = new ArrayList<PublishersDTO>();
	private String publishDate;
	private int numberOfPages;
	private List<AuthorDTO> authors = new ArrayList<AuthorDTO>();

	public BookDTO() {
		super();
	}

	public BookDTO(String title, String subtitle, List<PublishersDTO> publishers, String publishDate, int numberOfPages,
	        List<AuthorDTO> authors) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.publishers = publishers;
		this.publishDate = publishDate;
		this.numberOfPages = numberOfPages;
		this.authors = authors;
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

	public List<PublishersDTO> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<PublishersDTO> publishers) {
		this.publishers = publishers;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public List<AuthorDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorDTO> authors) {
		this.authors = authors;
	}

}
