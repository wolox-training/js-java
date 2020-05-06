package wolox.training.controllersTest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import wolox.training.controllers.BookController;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

//@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc Mvc;

	@MockBean
	private BookRepository bookRepository;

	@BeforeAll
	public void init() {
		Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
		bookRepository.save(book1);
		Book book2 = new Book("pepa2", "pepa2", "Image2", "momo2", "2", "2", "2022", 362, "78945678945962");
		bookRepository.save(book2);
		Book book3 = new Book("pepa3", "pepa3", "Image3", "momo3", "3", "3", "2023", 363, "78945678945963");
		bookRepository.save(book3);
		Book book4 = new Book("pepa4", "pepa4", "Image4", "momo4", "4", "4", "2024", 364, "78945678945964");
		bookRepository.save(book4);
	}

	@Test
	public void givenBooks_whenGetBooks_thenReturnJsonArray() throws Exception {
		List<Book> allBooks = bookRepository.findAll();
		given(bookRepository.findAll()).willReturn(allBooks);
		Mvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$[2].isbn").value("78945678945963")).andExpect(jsonPath("$", hasSize(4)));
	}

}
