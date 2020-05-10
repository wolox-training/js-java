package wolox.training.models;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CoverDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String small;
	private String large;
	private String medium;

	public CoverDTO() {
		super();
	}

	public CoverDTO(String small, String large, String medium) {
		super();
		this.small = small;
		this.large = large;
		this.medium = medium;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

}
