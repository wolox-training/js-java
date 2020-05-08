package wolox.training.services;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import wolox.training.models.BookDTO;

@Service
public class OpenLibraryService {

	public BookDTO bookInfo(String isbn) throws JsonMappingException, JsonProcessingException {
		System.out.println("entro a bookInfo");
		RestTemplate restTemplate = new RestTemplate();
		isbn = "0385472579";
		String openlibraryResourceUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";
		Map<String, BookDTO> response = restTemplate.exchange(openlibraryResourceUrl, HttpMethod.GET, null,
		        new ParameterizedTypeReference<Map<String, BookDTO>>() {
		        }, isbn).getBody();
		System.out.println("arma response :" + response.toString());
		BookDTO bookdto = response.get("ISBN:" + isbn);
		System.out.println("arma bookdto :" + bookdto.toString());

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

		return null;

	}

}
