package wolox.training.controllersTest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import wolox.training.controllers.UserController;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc Mvc;

	@MockBean
	private UserRepository userRepository;

//	@BeforeAll
//	public static void init() {
//
//	}

	@Test
	public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));
		User user2 = new User("0002", "popo2", LocalDate.parse("1918-01-04", formatter));
		User user3 = new User("0003", "popo3", LocalDate.parse("2020-01-05", formatter));

		List<User> allUsers = Arrays.asList(user1, user2, user3);

		given(userRepository.findAll()).willReturn(allUsers);

		Mvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$[1].name").value("popo2")).andExpect(jsonPath("$", hasSize(3)));
	}
}
