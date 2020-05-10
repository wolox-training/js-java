package wolox.training.services;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import wolox.training.models.AuthorDTO;
import wolox.training.models.Book;
import wolox.training.models.BookDTO;
import wolox.training.models.PublishersDTO;
import wolox.training.models.SubjectsDTO;

@Service
public class OpenLibraryService {

	public Optional<Book> bookInfo(String isbn) throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();

		String openlibraryResourceUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";

		Map<String, BookDTO> response = restTemplate.exchange(openlibraryResourceUrl, HttpMethod.GET, null,
		        new ParameterizedTypeReference<Map<String, BookDTO>>() {
		        }, isbn).getBody();

		BookDTO bookdto = response.get("ISBN:" + isbn);

		Book book = null;

		return Optional.ofNullable(informaBookDesdeDTO(bookdto, isbn, book));

	}

	public Book informaBookDesdeDTO(BookDTO bookdto, String isbn, Book book) {

		if (bookdto != null) {

			String sinInformar = "sinInformar";

			book = new Book();
			ArrayList<AuthorDTO> authorLista = (ArrayList<AuthorDTO>) bookdto.getAuthors();
			ArrayList<PublishersDTO> publishersLista = (ArrayList<PublishersDTO>) bookdto.getPublishers();
			ArrayList<SubjectsDTO> subjectsLista = (ArrayList<SubjectsDTO>) bookdto.getSubjects();

			book.setIsbn(isbn);

			if (!authorLista.isEmpty()) {
				book.setAuthor(Optional.ofNullable(authorLista.get(0).getName()).orElse(sinInformar));
			}

			if (!publishersLista.isEmpty()) {
				book.setPublisher(Optional.ofNullable(publishersLista.get(0).getName()).orElse(sinInformar));
			}

			if (!subjectsLista.isEmpty()) {
				book.setGenre(Optional.ofNullable(subjectsLista.get(0).getName()).orElse(sinInformar));
			}

			book.setImage(Optional.ofNullable(bookdto.getCover().getLarge()).orElse(sinInformar));
			book.setPages(Optional.ofNullable(bookdto.getNumberOfPages()).orElse(0));

			book.setSubtitle(Optional.ofNullable(bookdto.getSubtitle()).orElse(sinInformar));
			book.setTitle(Optional.ofNullable(bookdto.getTitle()).orElse(sinInformar));
			book.setYear(Optional.ofNullable(bookdto.getPublishDate()).orElse(sinInformar));
		}

		return book;
	}

}
