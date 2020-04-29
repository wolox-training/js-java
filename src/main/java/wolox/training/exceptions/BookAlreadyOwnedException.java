package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookAlreadyOwnedException(final String message) {
        super(message);
    }
	
    public BookAlreadyOwnedException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
