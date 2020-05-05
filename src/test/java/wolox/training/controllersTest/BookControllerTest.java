package wolox.training.controllersTest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import wolox.training.controllers.BookController;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc Mvc;

	@MockBean
	private BookRepository repoBookMock;

	@Before
	public void init() {
	}

	@Test
	public void givenBooks_whenGetBooks_thenReturnJsonArray() throws Exception {

		Book testBook = new Book();
		testBook.setAuthor("asdas");
		testBook.setGenre("asdas");
		testBook.setImage("asdasd");
		testBook.setIsbn("123123");
		testBook.setPages(2);
		testBook.setPublisher("dasdas");
		testBook.setSubtitle("asdasd");
		testBook.setTitle("asdasd");
		testBook.setYear("2020");

		List<Book> allBooks = Arrays.asList(testBook);

		given(repoBookMock.findAll()).willReturn(allBooks);

		Mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].year", is(testBook.getYear())));
	}

}
