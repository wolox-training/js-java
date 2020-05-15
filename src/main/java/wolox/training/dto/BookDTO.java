package wolox.training.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class BookDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String subtitle;
    private CoverDTO cover = new CoverDTO();
    private List<PublishersDTO> publishers = new ArrayList<PublishersDTO>();
    private List<SubjectsDTO> subjects = new ArrayList<SubjectsDTO>();
    private String publishDate;
    private int numberOfPages;
    private List<AuthorDTO> authors = new ArrayList<AuthorDTO>();
}
