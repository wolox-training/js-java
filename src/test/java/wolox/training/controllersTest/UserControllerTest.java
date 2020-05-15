package wolox.training.controllersTest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import wolox.training.controllers.UserController;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@WebMvcTest(UserController.class)
@TestMethodOrder(OrderAnnotation.class)
// @ContextConfiguration(classes = {OpenLibraryService.class, BookController.class})
class UserControllerTest {

    @Autowired
    private MockMvc Mvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    public void givenUsers_WhenPostUser_ThenReturnHttpStatusCREATED() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));

        given(userRepository.save(any())).willReturn(user1);

        Mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(user1).toString())).andDo(print())
                .andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    @Order(3)
    public void givenUsers_WhenGetUserById_thenReturnAJsonArray() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));

        userRepository.save(user1);

        given(userRepository.findById(Long.valueOf("1"))).willReturn(Optional.of(user1));

        Mvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("pepe1"));
    }

    @Test
    @Order(4)
    public void givenUsers_WhenGetUserByUserName_thenReturnAJsonArray() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));

        userRepository.save(user1);

        given(userRepository.findFirstByUserName("0001")).willReturn(Optional.of(user1));

        Mvc.perform(
                MockMvcRequestBuilders.get("/api/users").param("userName", "0001").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("pepe1"));
    }

    @Test
    @Order(5)
    public void givenUsers_WhenDeleteUser_thenReturnHttpStatusOk() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));

        userRepository.save(user1);

        given(userRepository.findById(any())).willReturn(Optional.of(user1));

        Mvc.perform(delete("/api/users/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Order(6)
    public void givenUsers_WhenDeleteUser_thenReturnHttpStatusNotFound() throws Exception {
        given(userRepository.findById(any())).willReturn(Optional.empty());

        Mvc.perform(delete("/api/users/1")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void givenUsers_WhenPutUser_thenReturnHttpStatusOk() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));
        User user1m = new User("0001", "pepe112", LocalDate.parse("1920-10-10", formatter));

        given(userRepository.findById(any())).willReturn(Optional.of(user1));
        given(userRepository.save(any())).willReturn(user1m);

        Mvc.perform(put("/api/users/0").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(user1m).toString())).andDo(print())
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("pepe112"));
    }

    @Test
    @Order(8)
    public void givenUsers_WhenPutUser_ThenReturnHttpStatusBAD_REQUEST() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));

        Mvc.perform(put("/api/users/33").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(user1))).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    public void givenUsers_WhenPutUser_ThenReturnHttpStatusBAD_REQUEST2() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));

        given(userRepository.findById(any())).willReturn(Optional.empty());
        Mvc.perform(put("/api/users/0").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(user1).toString())).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(10)
    public void givenUsers_WhenPatchUser_ThenReturnHttpStatusOk() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));
        Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");

        given(userRepository.findById(any())).willReturn(Optional.of(user1));
        given(bookRepository.findById(any())).willReturn(Optional.of(book1));

        given(userRepository.save(any())).willReturn(user1);

        Mvc.perform(patch("/api/users/{userid}/book/{bookid}", 1, 1)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.books[0].id").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }

    @Test
    @Order(11)
    public void givenUsers_WhenDeleteUser_ThenReturnHttpStatusOk() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user1 = new User("0001", "pepe1", LocalDate.parse("1920-10-10", formatter));
        Book book1 = new Book("pepa1", "pepa1", "Image1", "momo1", "1", "1", "2021", 361, "78945678945961");
        user1.addBook(book1);
        userRepository.save(user1);
        given(userRepository.findById(any())).willReturn(Optional.of(user1));
        given(bookRepository.findById(any())).willReturn(Optional.of(book1));

        given(userRepository.save(any())).willReturn(user1);

        Mvc.perform(delete("/api/users/{userid}/book/{bookid}", 1, 1)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.books").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }
}
