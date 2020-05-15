package wolox.training.dto;

import java.io.Serializable;
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
public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String url;
    private String name;
}
