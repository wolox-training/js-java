package wolox.training.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import wolox.training.models.Book;
import wolox.training.services.OpenLibraryService;

@SpringBootTest
@ContextConfiguration(classes = {OpenLibraryService.class})
public class BookApiExternaTest {

    @Autowired
    private OpenLibraryService openLibraryService;

    @Test
    public void givenBooks_WhenGetBookByISBN_thenFromMockAPIBase() throws Exception {

        Optional<Book> book1 = openLibraryService.bookInfo("0385472579");

        assertThat("Anchor Books".equals(book1.get().getPublisher())).isTrue();

    }
}
