package wolox.training.services;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import wolox.training.repositories.BookRepository;

@Service
public class OpenLibraryService {

	@Autowired
	private BookRepository bookRepository;

	public Book bookInfo(String isbn) throws JsonMappingException, JsonProcessingException {
		System.out.println("++bookInfo+++++++++++++++++++++++++++++++");
		RestTemplate restTemplate = new RestTemplate();

		String openlibraryResourceUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";
		// toma el primer nivel, y el resto mete en el objeto

		Map<String, BookDTO> response = restTemplate.exchange(openlibraryResourceUrl, HttpMethod.GET, null,
		        new ParameterizedTypeReference<Map<String, BookDTO>>() {
		        }, isbn).getBody();

		BookDTO bookdto = response.get("ISBN:" + isbn);

		Book book = new Book();
		ArrayList<AuthorDTO> authorLista = (ArrayList<AuthorDTO>) bookdto.getAuthors();

		if (authorLista.isEmpty()) {
			book.setAuthor("sin informar");
		} else {
			book.setAuthor(authorLista.get(0).getName());
		}

		book.setGenre("sin informar");
		book.setImage("sin informar");
		book.setIsbn(isbn);
		book.setPages(bookdto.getNumberOfPages());

		ArrayList<PublishersDTO> publishersLista = (ArrayList<PublishersDTO>) bookdto.getPublishers();

		if (publishersLista.isEmpty()) {
			book.setPublisher("sin informar");
		} else {
			book.setPublisher(publishersLista.get(0).getName());
		}

		book.setSubtitle(bookdto.getSubtitle());
		book.setTitle(bookdto.getTitle());
		book.setYear(bookdto.getPublishDate());

		System.out.println("salida: " + book.toString());
		return bookRepository.save(book);

//		String url = "https://openlibrary.org/api/books?bibkeys=ISBN:0385472579&format=json&jscmd=data";

		// ResponseEntity<String> response = restTemplate.getForEntity(url + "/1",
		// String.class);
//		BookDTO bookDTO = restTemplate.getForObject(url, BookDTO.class);

//		System.out.println("+++++++++++++++++++++++++++++++++");
//		System.out.println(bookDTO.getIsbn());
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode root = mapper.readTree(response.getBody());

//		System.out.println("+++++++++++++++++++++++++++++++++");
//		System.out.println(root.toString());

//		System.out.println("+++++++++++++++++++++++++++++++++");
//		BookDTO bookDTO = restTemplate.getForObject(url + "/1", BookDTO.class);

//		System.out.println(bookDTO.toString());
//		System.out.println("+++++++++++++++++++++++++++++++++");
//		System.out.println(response.toString());
//

//
//		JsonNode isbnOut = root.path("ISBN");
//		System.out.println("+++++++++++++++++++++++++++++++++");
//		System.out.println(isbnOut.toString());
//		System.out.println("+++++++++++++++++++++++++++++++++");

	}

}
