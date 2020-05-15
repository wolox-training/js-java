package wolox.training.services;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import wolox.training.dto.AuthorDTO;
import wolox.training.dto.BookDTO;
import wolox.training.dto.PublishersDTO;
import wolox.training.dto.SubjectsDTO;
import wolox.training.models.Book;

@Service
public class OpenLibraryService {
    // local:http://localhost:8080
    // real :https://openlibrary.org
    @Value("${open-library.base.url}")
    private String baseUrl;

    public Optional<Book> bookInfo(String isbn) throws JsonMappingException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String openlibraryResourceUrl = baseUrl + "/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";
        System.out.println("openlibraryResourceUrl: " + openlibraryResourceUrl);
        Map<String, BookDTO> response = restTemplate.exchange(openlibraryResourceUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Map<String, BookDTO>>() {}, isbn).getBody();

        BookDTO bookdto = response.get("ISBN:" + isbn);

        return Optional.ofNullable(buildBookFromBookDTO(bookdto, isbn));

    }

    public Book buildBookFromBookDTO(BookDTO bookdto, String isbn) {

        Book book = null;

        if (bookdto != null) {

            String sinInformar = "sinInformar";

            book = new Book();

            ArrayList<AuthorDTO> authorLista = (ArrayList<AuthorDTO>) bookdto.getAuthors();
            ArrayList<PublishersDTO> publishersLista = (ArrayList<PublishersDTO>) bookdto.getPublishers();
            ArrayList<SubjectsDTO> subjectsLista = (ArrayList<SubjectsDTO>) bookdto.getSubjects();

            if (!authorLista.isEmpty()) {
                book.setAuthor(Optional.ofNullable(authorLista.get(0).getName()).orElse(sinInformar));
            }

            if (!publishersLista.isEmpty()) {
                book.setPublisher(Optional.ofNullable(publishersLista.get(0).getName()).orElse(sinInformar));
            }

            if (!subjectsLista.isEmpty()) {
                book.setGenre(Optional.ofNullable(subjectsLista.get(0).getName()).orElse(sinInformar));
            }

            book.setIsbn(isbn);
            book.setImage(Optional.ofNullable(bookdto.getCover().getLarge()).orElse(sinInformar));
            book.setPages(Optional.ofNullable(bookdto.getNumberOfPages()).orElse(0));
            book.setSubtitle(Optional.ofNullable(bookdto.getSubtitle()).orElse(sinInformar));
            book.setTitle(Optional.ofNullable(bookdto.getTitle()).orElse(sinInformar));
            book.setYear(Optional.ofNullable(bookdto.getPublishDate()).orElse(sinInformar));
        }

        return book;
    }

}
