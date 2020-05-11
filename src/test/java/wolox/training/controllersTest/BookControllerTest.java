package wolox.training.controllersTest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import wolox.training.controllers.BookController;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@WebMvcTest(BookController.class)
@TestMethodOrder(OrderAnnotation.class)
class BookControllerTest {

	@Autowired
	private MockMvc Mvc;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private OpenLibraryService openLibraryService;

	@Test
	@Order(1)
	public void givenBooks_whenGetAllBooks_thenReturnJsonArray() throws Exception {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		bookRepository.save(book1);
		Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "2", "2", "2022", 362, "78945678945962");
		bookRepository.save(book2);
		Book book3 = new Book("pepa3", "pepa3", "Image3", "momo3", "3", "3", "2023", 363, "78945678945963");
		bookRepository.save(book3);
		Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "4", "4", "2024", 364, "78945678945964");
		bookRepository.save(book4);

		List<Book> allBooks = Arrays.asList(book1, book2, book3, book4);

		given(bookRepository.findAll()).willReturn(allBooks);

		Mvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$[2].isbn").value("78945678945963")).andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	@Order(2)
	public void givenBooks_WhenPostBook_ThenReturnHttpStatusCREATED() throws Exception {

		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		// bookRepository.save(book1);

		given(bookRepository.save(any())).willReturn(book1);

		Mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
		        .content(new ObjectMapper().writeValueAsString(book1).toString())).andDo(print())
		        .andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.genre").exists());
	}

	@Test
	@Order(3)
	public void givenBooks_WhenGetBookById_thenReturnAJsonArray() throws Exception {

		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		bookRepository.save(book1);

		given(bookRepository.findById(Long.valueOf("1"))).willReturn(Optional.of(book1));

		Mvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
		        .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("78945678945961"));
	}

	@Test
	@Order(4)
	public void givenBooks_WhenGetBookByAuthor_thenReturnAJsonArray() throws Exception {

		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		bookRepository.save(book1);

		given(bookRepository.findFirstByAuthorOrderByYearDesc("pepa1")).willReturn(Optional.of(book1));

		Mvc.perform(
		        MockMvcRequestBuilders.get("/api/books").param("author", "pepa1").accept(MediaType.APPLICATION_JSON))
		        .andDo(print()).andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("78945678945961"));
	}

	@Test
	@Order(5)
	public void givenBooks_WhenDeleteBook_thenReturnHttpStatusOk() throws Exception {

		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		bookRepository.save(book1);

		given(bookRepository.findById(any())).willReturn(Optional.of(book1));

		Mvc.perform(delete("/api/books/1")).andDo(print()).andExpect(status().isOk())
		        .andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	@Order(6)
	public void givenBooks_WhenDeleteBook_thenReturnHttpStatusNotFound() throws Exception {
		given(bookRepository.findById(any())).willReturn(Optional.empty());

		Mvc.perform(delete("/api/books/1")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@Order(7)
	public void givenBooks_WhenPutBook_thenReturnHttpStatusOk() throws Exception {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		Book book1m = new Book("pepa1", "pepa1", "Image1", "xxxx", "1", "1", "2021", 361, "78945678945961");
		given(bookRepository.findById(any())).willReturn(Optional.of(book1));
		given(bookRepository.save(any())).willReturn(book1m);
		Mvc.perform(put("/api/books/0").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
		        .content(new ObjectMapper().writeValueAsString(book1m))).andDo(print()).andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("xxxx"));
	}

	@Test
	@Order(8)
	public void givenBooks_WhenPutBook_ThenReturnHttpStatusBAD_REQUEST() throws Exception {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		Mvc.perform(put("/api/books/33").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
		        .content(new ObjectMapper().writeValueAsString(book1))).andDo(print())
		        .andExpect(status().isBadRequest());
	}

	@Test
	@Order(9)
	public void givenBooks_WhenPutBook_ThenReturnHttpStatusBAD_REQUEST2() throws Exception {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		given(bookRepository.findById(any())).willReturn(Optional.empty());
		Mvc.perform(put("/api/books/0").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
		        .content(new ObjectMapper().writeValueAsString(book1))).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@Order(10)
	public void givenBooks_WhenGetBookByISBN_thenReturnAJsonArrayFromInternalBase() throws Exception {

		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "0385472579");

		given(bookRepository.findFirstByIsbn("0385472579")).willReturn(Optional.of(book1));

		Mvc.perform(
		        MockMvcRequestBuilders.get("/api/books").param("isbn", "0385472579").accept(MediaType.APPLICATION_JSON))
		        .andDo(print()).andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("0385472579"));
	}

	@Test
	@Order(11)
	public void givenBooks_WhenGetBookByISBN_thenReturnAJsonArrayFromAPIBase() throws Exception {

		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "0385472579");

		given(bookRepository.findFirstByIsbn("0385472579")).willReturn(Optional.empty());
		given(openLibraryService.bookInfo("0385472579")).willReturn(Optional.of(book1));
		given(bookRepository.save(any())).willReturn(book1);

		Mvc.perform(
		        MockMvcRequestBuilders.get("/api/books").param("isbn", "0385472579").accept(MediaType.APPLICATION_JSON))
		        .andDo(print()).andExpect(status().isCreated())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("0385472579"));
	}

}
