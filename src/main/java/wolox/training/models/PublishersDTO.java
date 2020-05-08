package wolox.training.models;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PublishersDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;

	public PublishersDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PublishersDTO(String name) {
		super();
		this.name = name;
	}

}
